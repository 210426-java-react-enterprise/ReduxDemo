package com.revature.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class JwtUtility {

    private JwtConfig jwtConfig;
    private TokenGenerator tokenGenerator;

    @Autowired
    public JwtUtility(JwtConfig jwtConfig, TokenGenerator tokenGenerator){
        this.jwtConfig = jwtConfig;
        this.tokenGenerator = tokenGenerator;
    }

    public String getUserNameFromJwtToken(String token) {
        // Needed to be explicit because when chained together couldnt see JWT token
        JwtParser parser = Jwts.parserBuilder().setSigningKey(jwtConfig.getSigningKey()).build();
        String jwtToken = token.split(" ")[1].trim();
        Jws<Claims> claimsJws = parser.parseClaimsJws(jwtToken);



        return claimsJws.getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws ExpiredJwtException {
        try {
            Jwts.parserBuilder().setSigningKey(jwtConfig.getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | IllegalArgumentException | UnsupportedJwtException | MalformedJwtException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String refreshJwtToken(ExpiredJwtException authToken){
        return tokenGenerator.refreshJwt(authToken);
    }
}
