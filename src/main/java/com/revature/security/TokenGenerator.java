package com.revature.security;

import com.revature.models.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenGenerator {

    private JwtConfig jwtConfig;

    @Autowired
    public TokenGenerator(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }


    public String createJwt(User user){
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(jwtConfig.getExpiration(), ChronoUnit.MILLIS);
        String builder = Jwts.builder()
                                 .setId(Integer.toString(user.getId()))
                                 .setSubject(user.getUsername())
                                 .setIssuer("revature")
                                 .setIssuedAt(Date.from(issuedAt))
                                 .setExpiration(Date.from(expiration))
                                 .signWith(jwtConfig.getSigningKey(),jwtConfig.getSigAlg()).compact();

        return jwtConfig.getPrefix() + " " + builder;
    }

    public JwtConfig getJwtConfig(){
        return  jwtConfig;
    }

    public String refreshJwt(ExpiredJwtException authToken) {
       Claims claims = authToken.getClaims();
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(jwtConfig.getExpiration(), ChronoUnit.MILLIS);
       JwtBuilder builder = Jwts.builder()
                                        .setId(claims.getId())
                                        .setSubject(claims.getSubject())
                                        .setIssuer("revature")
                                        .setIssuedAt(Date.from(issuedAt))
                                        .setExpiration(Date.from(expiration))
                                        .signWith(jwtConfig.getSigningKey(),jwtConfig.getSigAlg());

        return jwtConfig.getPrefix() + builder.compact();
    }

}
