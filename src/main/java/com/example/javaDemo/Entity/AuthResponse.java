package com.example.javaDemo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponse {
    private final String token;
    private final String message;
}
