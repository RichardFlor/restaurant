package com.example.restaurant.security.services;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.example.restaurant.entities.user.User;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
//@Service
//public class JwtTokenService {
//    @Value("${api.security.token.secret}")
//    private String secret;
//    public String generateToken(User user){
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secret);
//
//            String token = JWT.create()
//                    .withIssuer("login-auth-api")
//                    .withSubject(user.getEmail())
//                    .withExpiresAt(this.generateExpirationDate())
//                    .sign(algorithm);
//            return token;
//        } catch (JWTCreationException exception){
//            throw new RuntimeException("Error while authenticating");
//        }
//    }
//
//    public String validateToken(String token){
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secret);
//            return JWT.require(algorithm)
//                    .withIssuer("login-auth-api")
//                    .build()
//                    .verify(token)
//                    .getSubject();
//        } catch (JWTVerificationException exception) {
//            return null;
//        }
//    }
//
//    private Instant generateExpirationDate(){
//        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
//    }
//}

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.restaurant.errors.exceptions.AuthenticationTokenCreationException;
import com.example.restaurant.errors.exceptions.UnauthorizedException;
import com.example.restaurant.security.dto.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtTokenService {

    @Value("${default-project.jwt.secret}")
    private String secret;

    @Value("${default-project.jwt.expiration}")
    private Long expiration;

    @Value("${default-project.jwt.issuer}")
    private String issuer;

    private final ZoneOffset zoneOffset = ZoneOffset.of("-03:00");


    public String generateToken(UserDetailsDTO user){
        try{
            var algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(user.getUser().getId().toString())
                    .withIssuedAt(LocalDateTime.now().toInstant(zoneOffset))
                    .withExpiresAt(LocalDateTime.now().plusMinutes(this.expiration).toInstant(zoneOffset))
                    .withPayload(Map.of("user", user.toMap()))
                    .sign(algorithm);
        }catch (JWTCreationException jwtCreationException){
            throw new AuthenticationTokenCreationException(jwtCreationException);
        }
    }

    public UUID getUserId(String token){
        try{
            var algorithm = Algorithm.HMAC256(this.secret);

            String id = JWT.require(algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();

            return UUID.fromString(id);

        }catch (JWTVerificationException jwtVerificationException){
            throw new UnauthorizedException(jwtVerificationException);
        }
    }
}