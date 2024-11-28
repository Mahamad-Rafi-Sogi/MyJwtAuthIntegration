package com.rafi.jwttoken.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> userDatabase = new HashMap<>();

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        userDatabase.put("admin", passwordEncoder.encode("password"));
    }

    public boolean authenticate(String username, String password) {
        return userDatabase.containsKey(username) &&
                passwordEncoder.matches(password, userDatabase.get(username));
    }
}