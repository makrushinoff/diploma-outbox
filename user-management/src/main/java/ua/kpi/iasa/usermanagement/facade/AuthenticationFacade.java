package ua.kpi.iasa.usermanagement.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.commons.constant.Role;
import ua.kpi.iasa.commons.dto.UserDto;
import ua.kpi.iasa.commons.dto.UserPrincipalDto;
import ua.kpi.iasa.commons.dto.AuthenticationRequest;
import ua.kpi.iasa.commons.dto.AuthenticationResponse;
import ua.kpi.iasa.usermanagement.dto.RegistrationDto;
import ua.kpi.iasa.usermanagement.entity.User;
import ua.kpi.iasa.usermanagement.mapper.UserMapper;
import ua.kpi.iasa.usermanagement.service.JwtHandlerService;
import ua.kpi.iasa.usermanagement.service.UserService;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final JwtHandlerService jwtHandlerService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) throws JsonProcessingException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User userEntityByEmail = userService.findUserEntityByEmail(request.getEmail());
        String token = jwtHandlerService.generateToken(new HashMap<>(), userEntityByEmail);
        return new AuthenticationResponse(token);
    }

    public void registerUser(RegistrationDto registrationDto) {
        User user = userMapper.registrationDtoToUser(registrationDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userService.saveUser(user);
    }

    public UserDto getAuthenticatedUserData() {
        var authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipalDto principal = (UserPrincipalDto) authentication.getPrincipal();
        User user = userService.findUserEntityByEmail(principal.getEmail());
        UserDto userDto = userMapper.userToUserDto(user);
        userDto.setPassword(null);
        return userDto;
    }
}
