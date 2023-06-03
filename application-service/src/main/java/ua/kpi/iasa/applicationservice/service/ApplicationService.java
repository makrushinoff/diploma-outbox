package ua.kpi.iasa.applicationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.iasa.applicationservice.entity.Application;
import ua.kpi.iasa.applicationservice.entity.Outbox;
import ua.kpi.iasa.applicationservice.mapper.ApplicationMapper;
import ua.kpi.iasa.applicationservice.repository.ApplicationRepository;
import ua.kpi.iasa.applicationservice.repository.OutboxRepository;
import ua.kpi.iasa.commons.dto.ApplicationMessageDto;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;
import ua.kpi.iasa.commons.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;
  private final OutboxRepository outboxRepository;
  private final ObjectMapper objectMapper;

  @Transactional
  public Long createApplication(RepairmentApplicationDto request) throws JsonProcessingException {
    Application application = applicationMapper.repairmentApplicationDtoToApplication(request);
    Application saved = applicationRepository.save(application);
    UUID messageId = UUID.randomUUID();
    ApplicationMessageDto dto = applicationMapper.applicationToApplicationMessageDto(saved);
    dto.setMessageId(messageId);
    outboxRepository.save(
        Outbox.builder()
            .id(messageId)
            .time(LocalDateTime.now())
            .message(objectMapper.writeValueAsString(dto))
            .build()
    );
    return saved.getId();
  }

  public List<RepairmentApplicationDto> getAllUserApplications(Long userId) {
    return applicationRepository.findAllByUserIdOrderByCreationDateTimeAsc(userId).stream()
        .map(applicationMapper::applicationToRepairmentApplicationDto)
        .toList();
  }

  public RepairmentApplicationDto updateApplication(RepairmentApplicationDto request, Long id) {
    Application existingApplication = applicationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Can not find application with such id"));
    Application application = applicationMapper.repairmentApplicationDtoToApplication(request);
    application.setId(id);
    application.setCreationDateTime(existingApplication.getCreationDateTime());
    Application saved = applicationRepository.save(application);
    return applicationMapper.applicationToRepairmentApplicationDto(saved);
  }
  
  private void validateApplicationExists(Long id) {
    boolean applicationExists = applicationRepository.existsById(id);
    if(!applicationExists) {
      throw new ResourceNotFoundException("Can not find application with such id");
    }
  }
  
  public void deleteApplication(Long id) {
    this.validateApplicationExists(id);
    applicationRepository.deleteById(id);
  }
}
