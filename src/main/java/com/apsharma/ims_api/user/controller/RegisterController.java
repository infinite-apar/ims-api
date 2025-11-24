package com.apsharma.ims_api.user.controller;


import com.apsharma.ims_api.user.model.User;
import com.apsharma.ims_api.user.service.UserService;
import com.apsharma.ims_api.util.ApiResponseBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;


@RestController
@RequestMapping(value="/auth")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value  ="/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody @Valid User user) throws JsonProcessingException {
        userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.CREATED)
                        .message("User registered successfully")
                        .build()
        );
    }

    @PostMapping(value="/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) throws JsonProcessingException, SQLException {
        userService.validateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("User logged in successfully")
                        .build()
        );
    }

}
