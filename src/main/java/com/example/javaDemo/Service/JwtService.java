package com.example.javaDemo.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+864_000_000))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }
    private static final String SECRET_KEY = "K3X4yJcSaDPlcjYBOs4AilRwckikyxEoyCps+zK8o5YHV2F/67CUxnPHrkBulWmC6kiGqd4shAiI2NesZs1ocaKObNNwbZJWwKVjTeKBXeyQUlC+YOZKyulWN8WgYUKqSIEEHSzT8DaSVgVJ4CQ0+258mzm818rSe8+zhkdEQBSyy8rwvxkBJxN/k3nFzvAMrkOKDSHSgsuAJK+ojw5TC6PksL30zMLjecPkbtq7WJmulqnI+I7N+Z4yp/2K/DbqB0rTbXY3GakBbGtqznHUQvmUG/BRk9+r1g+nvZzNWHGJmQk5Iv/yWwoE+QRCBGwQyW3kfk4CVmnbOEPd0BqKD3sHuq6jFamyeRSIF+az78o=";
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()));
    }
}
