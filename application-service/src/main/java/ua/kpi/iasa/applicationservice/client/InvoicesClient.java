package ua.kpi.iasa.applicationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.iasa.commons.config.FeignClientConfig;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;

@FeignClient(value = "invoice-service", path = "/api/invoice/invoices", configuration = FeignClientConfig.class)
public interface InvoicesClient {

  @PostMapping
  Long createInvoiceForApplication(RepairmentApplicationDto repairmentApplicationDto);

}
