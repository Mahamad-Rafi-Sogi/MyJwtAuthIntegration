package com.rafi.jwttoken.controller;

import com.rafi.jwttoken.config.JwtUtils;
import com.rafi.jwttoken.model.AuthRequest;
import com.rafi.jwttoken.model.AuthResponse;
import com.rafi.jwttoken.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        if (authService.authenticate(request.getUsername(), request.getPassword())) {
            String token = jwtUtils.generateToken(request.getUsername());
            String refreshToken = jwtUtils.generateRefreshToken(request.getUsername());
            return ResponseEntity.ok(new AuthResponse(token, refreshToken));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        String username = jwtUtils.validateToken(refreshToken);
        if (username != null) {
            String token = jwtUtils.generateToken(username);
            return ResponseEntity.ok(new AuthResponse(token, refreshToken));
        }
        return ResponseEntity.status(401).body("Invalid refresh token");
    }
}