package com.javagrind.authservice.controllers.Auth;

import com.javagrind.authservice.dto.Response;
import com.javagrind.authservice.dto.request.Auth.LoginRequest;
import com.javagrind.authservice.dto.request.Auth.LogoutRequest;
import com.javagrind.authservice.dto.request.User.RegisterRequest;
import com.javagrind.authservice.entity.UserEntity;
import com.javagrind.authservice.services.AuthService;
import com.javagrind.authservice.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @PostMapping("/signin")
    public ResponseEntity<Response<Object>> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Object result = authService.login(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Token created successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<Response<Object>> registerUser(@Valid @RequestBody RegisterRequest request){
        UserEntity result = userService.create(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "User registered successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signout")
    public ResponseEntity<Response<Object>> logoutUser(@Valid @RequestBody LogoutRequest request){
        Object result = authService.logout(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Logout success", result);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<Response<Object>> validateUserToken(HttpServletRequest request){
        String headerValue = request.getHeader(AUTHORIZATION_HEADER);
        if (headerValue == null || !headerValue.startsWith(BEARER_PREFIX))
            throw new AuthenticationServiceException("Missing or invalid Authorization header");

        Object result = authService.validate(headerValue.substring(BEARER_PREFIX.length()));
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Token validated", result);
        return ResponseEntity.ok().body(response);
    }
}