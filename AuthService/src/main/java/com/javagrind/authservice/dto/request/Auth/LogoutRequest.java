package com.javagrind.authservice.dto.request.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class LogoutRequest {

    @NotBlank(message = "email cannot empty")
    @Email(message = "must be a valid email")
    private String email;
}
