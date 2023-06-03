package ua.kpi.iasa.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.commons.dto.NotificationDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceNotificationListenerService {

  private final NotificationService notificationService;

  @SqsListener(value = "${sqs.invoiceNotification.queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void notifyUserAboutInvoice(NotificationDto notificationDto) {
    log.info("Receive message to notify user with id : {} about invoice", notificationDto.getUserDto().getId());
    try {
      notificationService.sendEmailNotification(notificationDto);
      log.info("Successfully notified user with id : {} about invoice", notificationDto.getUserDto().getId());
    } catch (Exception e) {
      log.error("Error:", e);
    }
  }

}
