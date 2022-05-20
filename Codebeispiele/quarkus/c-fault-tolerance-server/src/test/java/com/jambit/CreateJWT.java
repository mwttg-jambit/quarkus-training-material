package com.jambit;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Set;

@QuarkusTest
public class CreateJWT {

    @Test
    void generateToken(){
        String token = Jwt.issuer("https://example.com/issuer")
                .upn("michael@java.at")
                .groups(Set.of("User", "Admin"))
                .claim(Claims.birthdate, "2001-01-01")
                .expiresIn(Duration.ofDays(1))
                .sign();
        System.out.println(token);

    }

    @Test
    void generateUserToken(){
        String token = Jwt.issuer("https://example.com/issuer")
                .upn("michael@java.at")
                .groups(Set.of("User"))
                .claim(Claims.birthdate, "2001-01-01")
                .expiresIn(Duration.ofDays(1))
                .sign();
        System.out.println(token);

    }
}
