package com.example.kapp.model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String fullname;
    private String token;

    public User(String username, String password, String fullname, String token) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.token = token;
    }

    public User() {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
