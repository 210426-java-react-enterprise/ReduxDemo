package com.revature.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;

@Component
public class JwtConfig {


    private static String header;

    private static String prefix;

    private static String secret;

    @Value("#{24*60*60*1000}")
    private long expiration;

    private static final SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;

    private Key signingKey;

    static {
        header = System.getenv("Header");
        prefix = System.getenv("Prefix");
        secret = System.getenv("Secret");
    }


    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public SignatureAlgorithm getSigAlg() {
        return sigAlg;
    }

    public Key getSigningKey() {
        return signingKey;
    }

    @PostConstruct
    public void postConstruct() {
        signingKey = Keys.secretKeyFor(sigAlg);
    }

}
