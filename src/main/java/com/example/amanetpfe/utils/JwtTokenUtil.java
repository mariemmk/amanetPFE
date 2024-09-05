package com.example.amanetpfe.utils;

import com.example.amanetpfe.Entities.User;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

@Component
public class JwtTokenUtil {
    
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;

    private RSAPrivateKey privateKey;

    public static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    public String generateAccessToken(User user){
        try {
            privateKey = RsaKeyStore.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jwts.builder()
            .setSubject(String.format("%s,%s",user.getIdUser(), user.getEmail()))
            .setIssuer("amennet")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
            .signWith(SignatureAlgorithm.RS256, privateKey)
            .compact();
    }

    public boolean validateAccessToken(String token){
        try {
            Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            System.out.println(ex);
            LOGGER.error("Signature validation failed");
        }

        return false;
    }

        public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
     
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
