package ua.kpi.iasa.invoiceservice.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.kpi.iasa.commons.config.FeignClientConfig;
import ua.kpi.iasa.commons.dto.AuthenticationRequest;
import ua.kpi.iasa.commons.dto.AuthenticationResponse;
import ua.kpi.iasa.commons.dto.UserDto;

@FeignClient(value = "user-management-service", path = "/api/user", configuration = FeignClientConfig.class)
public interface UsersClient {

  @GetMapping("/users/{id}")
  UserDto getUser(@PathVariable Long id);

  @PostMapping("/auth")
  AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest request);

}
