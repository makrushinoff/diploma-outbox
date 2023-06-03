package ua.kpi.iasa.invoiceservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.kpi.iasa.commons.config.FeignClientConfig;
import ua.kpi.iasa.commons.dto.NotificationDto;

@FeignClient(value = "notification-service", path = "/api/notification/notifications", configuration = FeignClientConfig.class)
public interface NotificationClient {

  @PostMapping
  void sendEmailNotification(@RequestBody NotificationDto notificationDto);

}
