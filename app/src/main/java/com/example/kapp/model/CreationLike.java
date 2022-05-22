package com.example.kapp.model;

public class CreationLike {
    private String username;
    private int creation;

    public CreationLike(String username, int creation) {
        this.username = username;
        this.creation = creation;
    }

    public CreationLike() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCreation() {
        return creation;
    }

    public void setCreation(int creation) {
        this.creation = creation;
    }
}
