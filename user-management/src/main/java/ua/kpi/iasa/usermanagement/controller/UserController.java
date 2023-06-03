package ua.kpi.iasa.usermanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.kpi.iasa.commons.dto.UserDto;
import ua.kpi.iasa.usermanagement.dto.RegistrationDto;
import ua.kpi.iasa.usermanagement.facade.AuthenticationFacade;
import ua.kpi.iasa.usermanagement.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @PostMapping("/register")
    public void registerNewUser(@RequestBody @Valid RegistrationDto registrationDto) {
        log.info("Request to register new user with email : {}", registrationDto.getEmail());
        authenticationFacade.registerUser(registrationDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        log.info("Request to find user by id: {}", userId);
        return userService.getUserById(userId);
    }

    @GetMapping("/info")
    public UserDto getAuthenticatedUserData() {
        return authenticationFacade.getAuthenticatedUserData();
    }

}
