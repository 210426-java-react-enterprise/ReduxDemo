package com.revature.dtos;

import io.jsonwebtoken.Claims;

public class PrincipalDTO {
    private int id;
    private String username;

    public PrincipalDTO()
    {
        super();
    }

    public PrincipalDTO(Claims jwtClaims) {
        this.id = Integer.parseInt(jwtClaims.getId());
        this.username = jwtClaims.getSubject();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
