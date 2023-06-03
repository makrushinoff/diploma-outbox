package ua.kpi.iasa.usermanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.commons.dto.UserDto;
import ua.kpi.iasa.commons.exception.ResourceNotFoundException;
import ua.kpi.iasa.usermanagement.dto.RegistrationDto;
import ua.kpi.iasa.usermanagement.entity.User;
import ua.kpi.iasa.usermanagement.mapper.UserMapper;
import ua.kpi.iasa.usermanagement.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User findUserEntityByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find with with such email"));
    }

    public void createUser(RegistrationDto registrationDto) {
        User user = userMapper.registrationDtoToUser(registrationDto);
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find user with such id"));
        UserDto userDto = userMapper.userToUserDto(user);
        userDto.setPassword("");
        return userDto;
    }
}
