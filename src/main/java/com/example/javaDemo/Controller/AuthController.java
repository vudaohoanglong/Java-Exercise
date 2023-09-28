package com.example.javaDemo.Controller;

import com.example.javaDemo.Entity.AuthResponse;
import com.example.javaDemo.Entity.User;
import com.example.javaDemo.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        AuthResponse authResponse = authService.register(user);
        if (authResponse.getMessage().startsWith("Failed")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(authResponse);
        } else if (authResponse.getMessage().startsWith("User")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(authResponse);
        }
        else return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        AuthResponse authResponse = authService.login(user);
        if (authResponse.getMessage().startsWith("Failed")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(authResponse);
        } else if (authResponse.getMessage().startsWith("User")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(authResponse);
        }
        else return ResponseEntity.ok(authResponse);
    }
}
