package com.me.cinemaapp.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SecurityConstants {
    @Value("${security.auth.whitelist}")
    private String[] whitelist;

    @Value("${security.jwt.secret}")
    private String JWTSecret;

    @Value("${security.jwt.default-expiration-time}")
    private int JWTDefaultExpirationTime;

    @Value("${security.jwt.issuer}")
    private String JWTIssuer;

}
