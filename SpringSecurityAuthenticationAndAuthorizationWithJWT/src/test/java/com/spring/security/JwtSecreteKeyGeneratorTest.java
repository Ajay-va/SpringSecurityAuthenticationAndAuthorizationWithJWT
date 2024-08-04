package com.spring.security;


import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;

public class JwtSecreteKeyGeneratorTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(JwtSecreteKeyGeneratorTest.class);

    @Test
    public void generateKey(){

      SecretKey secretKey= Jwts.SIG.HS512.key().build();
        String encodeKey=DatatypeConverter.printHexBinary(secretKey.getEncoded());
        System.out.println(encodeKey);
        LOGGER.info("secrete key {} --> %s",encodeKey);

    }



}
