package ua.kpi.iasa.applicationservice.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceCreationSenderService {

  private final AmazonSQSAsync amazonSQSAsync;
  private final ObjectMapper objectMapper;

  @Value("${sqs.invoiceCreation.queue}")
  private String queueName;

  @Value("${sqs.baseUrl}")
  private String queueBaseUrl;

  public void publish(String repairmentApplicationDto) {
    try {
      SendMessageRequest request = new SendMessageRequest()
          .withMessageBody(repairmentApplicationDto)
          .withQueueUrl(queueBaseUrl + queueName);
      amazonSQSAsync.sendMessage(request);
      log.info("Sent message to queue to create invoice");
    } catch (Exception e) {
      log.error("Exception when send message no create invoice");
      throw new RuntimeException(e);
    }

  }

}
