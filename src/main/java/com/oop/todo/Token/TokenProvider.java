package com.oop.todo.Token;

import com.oop.todo.Domain.User.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY="NMA8JPctFuna59f5";

    public String create(UserEntity userEntity) {

        Date expireDate = Date.from(Instant.now().plusSeconds(3600));

        return Jwts.builder()
                .setSubject(userEntity.getId())
                .setIssuer("todo app")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
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
