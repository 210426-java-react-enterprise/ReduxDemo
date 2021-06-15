package com.revature.dtos;

import com.revature.models.User;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String email;

    private String city;

    private String state;

    private String firstName;

    private String lastName;

    private int id;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Boolean wantsWeeklyUpdates;


    public UserDTO(){
        super();
    }

    public UserDTO(User user){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getWantsWeeklyUpdates() { return wantsWeeklyUpdates; }

    public void setWantsWeeklyUpdates(Boolean wantsWeeklyUpdates) { this.wantsWeeklyUpdates = wantsWeeklyUpdates; }
}
