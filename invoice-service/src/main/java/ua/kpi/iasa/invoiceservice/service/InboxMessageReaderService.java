package ua.kpi.iasa.invoiceservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.iasa.commons.dto.ApplicationMessageDto;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;
import ua.kpi.iasa.invoiceservice.entity.Inbox;
import ua.kpi.iasa.invoiceservice.repository.InboxRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class InboxMessageReaderService {

  private final InboxRepository inboxRepository;
  private final InvoiceService invoiceService;
  private final ObjectMapper objectMapper;

  @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
  @Transactional
  public void readInboxMessages() throws JsonProcessingException {
    log.info("Start to read inbox messages");
    Page<Inbox> inboxMessages = inboxRepository.findAll(PageRequest.of(0, 10, Sort.by(Direction.ASC, "time")));
    List<Inbox> content = inboxMessages.getContent();
    if(content.isEmpty()) {
      log.info("No new inbox messages");
      return;
    }
    inboxRepository.deleteAll(content);
    for (Inbox inbox : content) {
      String message = inbox.getMessage();
      invoiceService.createInvoiceForApplication(objectMapper.readValue(message, ApplicationMessageDto.class));
    }
  }

}
