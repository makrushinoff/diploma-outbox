package ua.kpi.iasa.usermanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.commons.dto.UserDto;
import ua.kpi.iasa.usermanagement.facade.AuthenticationFacade;
import ua.kpi.iasa.commons.dto.AuthenticationRequest;
import ua.kpi.iasa.commons.dto.AuthenticationResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest request)
        throws JsonProcessingException {
        log.info("Request to authenticate user with email : {}", request.getEmail());
        return authenticationFacade.authenticateUser(request);
    }
}
