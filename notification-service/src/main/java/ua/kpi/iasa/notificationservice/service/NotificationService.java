package ua.kpi.iasa.notificationservice.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.commons.dto.NotificationDto;
import ua.kpi.iasa.commons.dto.UserDto;
import ua.kpi.iasa.notificationservice.constant.EmailConstants;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

  private final JavaMailSender emailSender;

  public void sendEmailNotification(NotificationDto request) {
    UserDto userDto = request.getUserDto();
    int textArgsAmount = request.getRepairmentApplicationDto().getAddress().getApartment() == null
        ? 9
        : 10;
    String[] textArgs = new String[textArgsAmount];
    List<String> args = new ArrayList<>(List.of(
        userDto.getLastName(),
        userDto.getFirstName(),
        userDto.getFatherName(),
        request.getInvoiceId().toString(),
        request.getRepairmentApplicationDto().getAddress().getCity(),
        request.getRepairmentApplicationDto().getAddress().getStreet(),
        request.getRepairmentApplicationDto().getAddress().getHouse(),
        request.getRepairmentApplicationDto().getCreationDateTime().toString(),
        request.getReimbursementAmount().toString()
    ));
    String text;
    if (textArgsAmount == 10) {
      args.add(7, request.getRepairmentApplicationDto().getAddress().getApartment());
      text = EmailConstants.REPAIRMENT_TEXT_WITH_APARTMENT;
    } else {
      text = EmailConstants.REPAIRMENT_TEXT_WITHOUT_APARTMENT;
    }
    String emailText = MessageFormat.format(text, args.toArray(textArgs));
    sendSimpleMessage(userDto.getEmail(), EmailConstants.REPAIRMENT_SUBJECT, emailText);
  }

  public void sendSimpleMessage(String userEmail, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@diplomasync.com");
    message.setTo(userEmail);
    message.setSubject(subject);
    message.setText(text);
    log.info("Send email to : {} with subject: {}", userEmail, subject);
//    emailSender.send(message);
    throw new RuntimeException("Test error");
  }
}
