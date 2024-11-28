package com.rafi.jwttoken.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private static final long EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes
	private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key)
				.compact();
	}
	
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
    
    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
    	
}
