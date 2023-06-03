package ua.kpi.iasa.usermanagement.mapper;

import org.mapstruct.Mapper;
import ua.kpi.iasa.commons.dto.UserDto;
import ua.kpi.iasa.usermanagement.dto.RegistrationDto;
import ua.kpi.iasa.usermanagement.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    User registrationDtoToUser(RegistrationDto registrationDto);

}
