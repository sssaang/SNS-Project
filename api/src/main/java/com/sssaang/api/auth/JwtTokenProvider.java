package com.sssaang.api.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger =
            LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    private Key getSigninKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.id))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(this.getSigninKey(this.jwtSecret), SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims =
                Jwts.parserBuilder()
                        .setSigningKey(getSigninKey(jwtSecret))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigninKey(jwtSecret))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            this.logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            this.logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            this.logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            this.logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            this.logger.error("JWT claims string is empty.");
        }
        return false;
    }

}
