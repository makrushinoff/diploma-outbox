package ua.kpi.iasa.applicationservice.service;

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
import ua.kpi.iasa.applicationservice.entity.Outbox;
import ua.kpi.iasa.applicationservice.repository.OutboxRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxMessageReaderService {

  private final InvoiceCreationSenderService invoiceCreationSenderService;
  private final OutboxRepository outboxRepository;

  @Transactional
  @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
  public void sendOutboxMessages() {
    log.info("Start to fetch outbox messages");
    Page<Outbox> outboxMessages = outboxRepository.findAll(PageRequest.of(0, 10, Sort.by(Direction.ASC, "time")));
    if(outboxMessages.getContent().isEmpty()) {
      log.info("No new messages to send");
      return;
    }
    List<Outbox> content = outboxMessages.getContent();
    outboxRepository.deleteAll(content);
    content.stream()
        .map(Outbox::getMessage)
        .forEach(invoiceCreationSenderService::publish);
    log.info("Successfully sent {} messages", content.size());
  }

}
