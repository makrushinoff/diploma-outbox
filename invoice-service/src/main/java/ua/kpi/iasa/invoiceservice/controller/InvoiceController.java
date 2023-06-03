package ua.kpi.iasa.invoiceservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;
import ua.kpi.iasa.invoiceservice.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {

  private final InvoiceService invoiceService;

  @PostMapping
  public Long createInvoiceForApplication(@RequestBody @Valid RepairmentApplicationDto request)
      throws JsonProcessingException {
    log.info("Request to create invoice for application with id: {}", request.getId());
    return invoiceService.createInvoiceForApplication(request);
  }

}
