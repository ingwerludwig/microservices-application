package com.javagrind.authservice.controllers;

import com.javagrind.authservice.dto.Response;
import com.javagrind.authservice.dto.request.User.DeleteRequest;
import com.javagrind.authservice.dto.request.User.UpdateUserRequest;
import com.javagrind.authservice.entity.UserEntity;
import com.javagrind.authservice.handler.GlobalExceptionHandler;
import com.javagrind.authservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.logging.Logger;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    @PutMapping("/delete")
    public ResponseEntity<Response<UserEntity>> deleteUser(@Valid @RequestBody DeleteRequest request, Errors errors){
        String result = userService.delete(request);
        Response<UserEntity> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Delete "+ result + " successfully", null);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Response<UserEntity>> updateUser(@RequestParam String id, @RequestBody UpdateUserRequest request, Errors errors){
        UserEntity result = userService.update(id,request);
        Response<UserEntity> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "User updated successfully", result);
        return ResponseEntity.ok().body(response);
    }

}
