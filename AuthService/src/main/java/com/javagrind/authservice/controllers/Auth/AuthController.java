package com.javagrind.authservice.controllers.Auth;

import com.javagrind.authservice.dto.Response;
import com.javagrind.authservice.dto.request.Auth.LoginRequest;
import com.javagrind.authservice.dto.request.Auth.LogoutRequest;
import com.javagrind.authservice.dto.request.User.RegisterRequest;
import com.javagrind.authservice.entity.UserEntity;
import com.javagrind.authservice.handler.GlobalExceptionHandler;
import com.javagrind.authservice.services.AuthService;
import com.javagrind.authservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<Response<Object>> authenticateUser(@Valid @RequestBody LoginRequest request, BindingResult errors) {
        Object result = authService.login(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Token created successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<Response<Object>> registerUser(@Valid @RequestBody RegisterRequest request, BindingResult errors){
        UserEntity result = userService.create(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "User registered successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signout")
    public ResponseEntity<Response<Object>> logoutUser(@Valid @RequestBody LogoutRequest request, BindingResult errors){
        Object result = authService.logout(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Logout success", result);
        return ResponseEntity.ok().body(response);
    }
}