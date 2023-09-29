package com.example.javaDemo.Service;

import com.example.javaDemo.Util.AuthResponse;
import com.example.javaDemo.Entity.User;
import com.example.javaDemo.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
        if (user.getUsername().length()>20) return false;
        if (user.getPassword().length()>15 || user.getPassword().length()<6) return false;
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
        User existedUser = userRepo.findByUserName(user.getUsername()).orElse(null);
        if (existedUser == null) {
            return new AuthResponse("","User not exists");
        }
        var jwtToken = jwtService.generateToken(new HashMap<>(),user);
        return new AuthResponse(jwtToken,"Login successfully");
    }
}
