package com.example.javaDemo.Util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponse {
    private final String token;
    private final String message;
}
