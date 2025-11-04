package com.me.cinemaapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.me.cinemaapp.constant.SecurityConstants;
import com.me.cinemaapp.model.security.TokenDetail;
import com.me.cinemaapp.util.DateHelper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JWTProvider {
    private final SecurityConstants securityConstants;
    private final JWTVerifier jwtVerifier;

    public JWTProvider(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
        this.jwtVerifier = JWT.require(Algorithm.HMAC256(securityConstants.getJWTSecret()))
                .withIssuer(securityConstants.getJWTIssuer())
                .build();
    }
    @SneakyThrows
    public TokenDetail generateToken(String userId) {
        Date createDate = DateHelper.getUtcNow();
        Date expirationDate = getExpirationDate();
        String token = JWT.create()
                .withClaim("id", userId)
                .withIssuedAt(createDate)
                .withExpiresAt(expirationDate)
                .withIssuer(securityConstants.getJWTIssuer())
                .sign(Algorithm.HMAC256(securityConstants.getJWTSecret()));

        return TokenDetail.builder()
                .createDate(createDate.getTime())
                .expirationDate(expirationDate.getTime())
                .token(token)
                .build();
    }

    @SneakyThrows
    public String getUserIdByToken(String token) {
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("id").asString();
    }

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateHelper.getUtcNow());
        calendar.add(Calendar.MINUTE, securityConstants.getJWTDefaultExpirationTime());
        return calendar.getTime();
    }


}
