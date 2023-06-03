package ua.kpi.iasa.invoiceservice.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.commons.dto.NotificationDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceNotificationSenderService {

  private final AmazonSQSAsync amazonSQSAsync;
  private final ObjectMapper objectMapper;

  @Value("${sqs.invoiceNotification.queue}")
  private String queueName;

  @Value("${sqs.baseUrl}")
  private String queueBaseUrl;

  public void publish(String notificationDto) {
    try {
      SendMessageRequest request = new SendMessageRequest()
          .withMessageBody(notificationDto)
          .withQueueUrl(queueBaseUrl + queueName);
      amazonSQSAsync.sendMessage(request);
      log.info("Sent message to queue to notify user about invoice");
    } catch (Exception e) {
      log.error("Exception when send message no notify user about invoice");
      throw new RuntimeException(e);
    }

  }

}
