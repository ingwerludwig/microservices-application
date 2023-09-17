package com.javagrind.authservice.security.jwt;

import com.javagrind.authservice.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${oauth.practice.jwtSecret}")
    private String jwtSecret;

    @Value("${oauth.practice.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Date expiration = new Date(System.currentTimeMillis() + jwtExpirationMs);
        System.err.println(expiration);

        return Jwts.builder()
                .setSubject((userPrincipal.getEmail()))
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Date getExpiredAt(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        // Extract and print the "exp" claim (expiration time)
        Date expirationDate = claims.getExpiration();
        return expirationDate;
    }

    private String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new JwtException("Invalid JWT Signature");
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid JWT Token is Malformed");
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new JwtException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new JwtException("JWT claims string is empty");
        }
    }
}