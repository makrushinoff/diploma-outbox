package ua.kpi.iasa.usermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String fatherName;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

}
