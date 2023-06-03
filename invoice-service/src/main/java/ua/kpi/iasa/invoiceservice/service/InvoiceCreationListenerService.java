package ua.kpi.iasa.invoiceservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.commons.dto.ApplicationMessageDto;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;
import ua.kpi.iasa.invoiceservice.entity.Inbox;
import ua.kpi.iasa.invoiceservice.entity.Outbox;
import ua.kpi.iasa.invoiceservice.repository.InboxRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceCreationListenerService {

  private final InboxRepository inboxRepository;
  private final ObjectMapper objectMapper;

  @SqsListener(value = "${sqs.invoiceCreation.queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void listenInvoiceCreation(ApplicationMessageDto applicationMessageDto) {
    try {
      log.info("Receive message to create invoice for repairment with id : {}", applicationMessageDto.getId());
      inboxRepository.save(
          Inbox.builder()
              .id(applicationMessageDto.getMessageId())
              .message(objectMapper.writeValueAsString(applicationMessageDto))
              .time(LocalDateTime.now())
              .build()
      );
      log.info("Successfully created invoice for repairment with id : {}", applicationMessageDto.getId());
    } catch (Exception e) {
      log.error("Error: ", e);
    }
  }

}
