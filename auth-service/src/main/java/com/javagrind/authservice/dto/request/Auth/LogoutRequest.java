package com.javagrind.authservice.dto.request.Auth;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Validated
public class LogoutRequest {

    @NotBlank(message = "email cannot empty")
    @Email(message = "must be a valid email")
    private String email;
}
