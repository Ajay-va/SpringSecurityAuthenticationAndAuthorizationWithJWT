package com.spring.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JWTService {

    private static  final String SECRETE_KEY="C6EA1FD0F548A6493102B4B525A09CC7AC2C50FBC71FEBC13D223DD0B192D4757199BBE634C342465B5A9E11730882F5FB44E4209D87C3CB01BDDB64785F5914";

    private static final long VALIDITY= TimeUnit.MINUTES.toMillis(30);

    public String generateKey(UserDetails userDetails){

        Map<String,String> claims=new HashMap<>();
        claims.put("ajay","claims data passing here");

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    public SecretKey generateKey(){

      byte[] decodedKey=  Base64.getDecoder().decode(SECRETE_KEY);
      return Keys.hmacShaKeyFor(decodedKey);


    }


    public String exctractUserName(String jwt) {

        Claims claims = getClaims(jwt);

        return claims.getSubject();

    }

    private Claims getClaims(String jwt) {
        Claims claims= Jwts.parser()
                  .verifyWith(generateKey())
                  .build()
                  .parseSignedClaims(jwt)
                  .getPayload();
        return claims;
    }

    public boolean isTokenValid(String jwt) {

      Claims claims=  getClaims(jwt);
    return   claims.getExpiration().after(Date.from(Instant.now()));

    }
}
