package ua.kpi.iasa.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.iasa.commons.dto.NotificationDto;
import ua.kpi.iasa.notificationservice.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
@Slf4j
public class NotificationController {

  private final NotificationService notificationService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void sendEmailNotification(@RequestBody NotificationDto request) {
    log.info("Receive request to notify user with id : {}", request.getUserDto().getId());
    notificationService.sendEmailNotification(request);
  }

}
