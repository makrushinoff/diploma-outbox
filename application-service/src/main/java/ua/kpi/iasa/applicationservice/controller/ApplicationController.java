package ua.kpi.iasa.applicationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.iasa.applicationservice.service.ApplicationService;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

  private final ApplicationService applicationService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Long applyForRepairment(@RequestBody @Valid RepairmentApplicationDto request) throws JsonProcessingException {
    log.info("Receive repairment application for user : {}", request.getUserId());
    return applicationService.createApplication(request);
  }

  @PutMapping("/{id}")
  public RepairmentApplicationDto updateRepairmentApplication(@RequestBody @Valid RepairmentApplicationDto request,
                                                              @PathVariable Long id) {
    log.info("Receive request to update repairment application with id: {}", id);
    return applicationService.updateApplication(request, id);
  }

  @GetMapping
  public List<RepairmentApplicationDto> getAllUserApplications(@RequestParam Long userId) {
    log.info("Receive request to fetch applications for user: {}", userId);
    return applicationService.getAllUserApplications(userId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteApplicationById(@PathVariable Long id) {
    log.info("Receive request to delete application with id: {}", id);
    applicationService.deleteApplication(id);
  }

}
