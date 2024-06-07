package com.oop.todo.global.jwt;

import com.oop.todo.Domain.User.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;


@Slf4j
@Service
public class TokenProvider {
    private static final Key SECRET_KEY= Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String create(UserEntity userEntity) {

        Date expireDate = Date.from(Instant.now().plusSeconds(3600));

        return Jwts.builder()
                .setSubject(userEntity.getId())
                .setIssuer("todo app")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public String validateAndGetUserId(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
