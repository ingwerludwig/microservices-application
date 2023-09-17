package com.javagrind.authservice.dto.request.User;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
public class DeleteRequest {
    @NotBlank(message = "email cannot empty")
    @Email(message = "must be a valid email")
    private String email;
}