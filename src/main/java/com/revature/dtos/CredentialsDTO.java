package com.revature.dtos;

import javax.validation.constraints.NotEmpty;

public class CredentialsDTO
{
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public CredentialsDTO(){
        super();
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CredentialsDTO{" +
                "username='" + username +
                ", \n  password='" + password  +
                '}';
    }
}
