package com.example.springboottargethesis.controller;


import com.example.springboottargethesis.dto.LoginRequest;
import com.example.springboottargethesis.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "passu123salis";

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        if (VALID_USERNAME.equals(request.getUsername()) && VALID_PASSWORD.equals(request.getPassword())){
            return ResponseEntity.ok(new LoginResponse("validation-token-123","bearer"));
        }
        return ResponseEntity.ok(new LoginResponse("","bearer"));

    }


}
