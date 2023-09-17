package com.javagrind.authservice.dto.request.Auth;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
public class LoginRequest {

    @NotBlank(message = "password cannot empty")
    @Size(min = 8, message = "password minimal length is 8 character")
    private String password;

    @NotBlank(message = "email cannot empty")
    @Email(message = "must be a valid email")
    private String email;
}
