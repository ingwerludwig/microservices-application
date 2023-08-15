package com.javagrind.authservice.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javagrind.authservice.dto.Response;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException, IOException, java.io.IOException {
        logger.error("JWT Error Unauthorized: {}", authException.getMessage());

        Response<Object> responseBody = new Response<>();
        responseBody.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        responseBody.setSuccess(false);
        responseBody.setMessage("Unauthorized: Token Authentication Failed");
        responseBody.setData(null);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }
}