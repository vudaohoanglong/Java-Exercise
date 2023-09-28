package com.example.javaDemo.Service;

import com.example.javaDemo.Entity.AuthResponse;
import com.example.javaDemo.Entity.User;
import com.example.javaDemo.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(User user) {
        if (!validate(user)) {
            return new AuthResponse("","Failed to register new user");
        }
        User existedUser = userRepo.findByUserName(user.getUsername()).orElse(null);
        if (existedUser != null) {
            return new AuthResponse("","User already existed");
        }
        else {
            User newUser = User.builder()
                    .userName(user.getUsername())
                    .password(user.getPassword())
                    .build();
            userRepo.save(newUser);
            return new AuthResponse("","Create successfully");
        }
    }
    public boolean validate(User user){
        return true;
    }

    public AuthResponse login(User user) {
        if (!validate(user)) {
            return new AuthResponse("", "Failed to login");
        }
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            )
        );
        User existedUser = userRepo.findByUserName(user.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(new HashMap<>(),user);
        return new AuthResponse(jwtToken,"Hello");
    }
}
