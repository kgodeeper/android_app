package com.example.kapp.model;

import java.io.Serializable;

public class Author implements Serializable {
    private int id;
    private String fullname;
    private String phone;

    public Author(String fullname, String phone) {
        this.fullname = fullname;
        this.phone = phone;
    }

    public Author(int id, String fullname, String phone) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
