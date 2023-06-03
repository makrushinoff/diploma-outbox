package ua.kpi.iasa.invoiceservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.iasa.commons.dto.AuthenticationRequest;
import ua.kpi.iasa.commons.dto.AuthenticationResponse;
import ua.kpi.iasa.commons.dto.NotificationDto;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;
import ua.kpi.iasa.commons.dto.UserDto;
import ua.kpi.iasa.commons.dto.UserPrincipalDto;
import ua.kpi.iasa.commons.exception.ResourceNotFoundException;
import ua.kpi.iasa.commons.exception.UnprocessableStateException;
import ua.kpi.iasa.commons.service.JwtService;
import ua.kpi.iasa.invoiceservice.client.NotificationClient;
import ua.kpi.iasa.invoiceservice.client.UsersClient;
import ua.kpi.iasa.invoiceservice.entity.Invoice;
import ua.kpi.iasa.invoiceservice.entity.Outbox;
import ua.kpi.iasa.invoiceservice.repository.InvoiceRepository;
import ua.kpi.iasa.invoiceservice.repository.OutboxRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {

  private final InvoiceRepository invoiceRepository;
  private final CalculationService calculationService;
  private final UsersClient usersClient;
  private final NotificationClient notificationClient;
  private final InvoiceNotificationSenderService invoiceNotificationSenderService;
  private final JwtService jwtService;
  private final OutboxRepository outboxRepository;
  private final ObjectMapper objectMapper;

  @Transactional
  public Long createInvoiceForApplication(RepairmentApplicationDto request) throws JsonProcessingException {
    Invoice invoice = new Invoice();
    if(request.getId() == null) {
      throw new ResourceNotFoundException("Can not create invoice for non-existent application");
    }
    if(request.getUserId() == null) {
      throw new UnprocessableStateException("Can not process invoice without user data");
    }
    UserDto user = this.findUser(request.getUserId());
    invoice.setApplicationId(request.getId());
    invoice.setUserId(user.getId());
    BigDecimal reimbursement = calculationService.calculateReimbursement(request);
    invoice.setReimbursementAmount(reimbursement);
    Invoice saved = invoiceRepository.save(invoice);
    log.info("Saved new invoice with id: {}", saved.getId());
    request.setInvoiceId(saved.getId());
    NotificationDto notification = NotificationDto.builder()
        .invoiceId(invoice.getId())
        .userDto(user)
        .repairmentApplicationDto(request)
        .reimbursementAmount(invoice.getReimbursementAmount())
        .build();
    outboxRepository.save(
        Outbox.builder()
            .time(LocalDateTime.now())
            .message(objectMapper.writeValueAsString(notification))
            .build()
    );
    log.info("Saved outbox message for invoice with id: {}", saved.getId());
    return saved.getId();
  }

  private UserDto findUser(Long userId) {
    Authentication existingAuthentication = SecurityContextHolder.getContext().getAuthentication();
    if(existingAuthentication == null) {
      AuthenticationResponse token = usersClient.authenticate(new AuthenticationRequest("admin@admin.com", "x"));
      UserPrincipalDto user = jwtService.extractUser(token.getToken());
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          user,
          "Bearer " + token.getToken(),
          List.of(new SimpleGrantedAuthority(user.getRole().name()))
      );
      authentication.setDetails(new WebAuthenticationDetailsSource());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    return usersClient.getUser(userId);
  }

  public void deleteInvoiceById(Long invoiceId) {
    boolean exists = invoiceRepository.existsById(invoiceId);
    if(!exists) {
      return;
    }
    invoiceRepository.deleteById(invoiceId);
  }

}
