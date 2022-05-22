package com.example.kapp.model;

import java.io.Serializable;
import java.util.List;

public class Creation implements Serializable {
    private int id;
    private String name;
    private String image;
    private Author author;
    private List<Category> category;
    private String description;

    public Creation(int id, String name, String image, Author author, List<Category> category,String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.author = author;
        this.category = category;
        this.description = description;
    }

    public Creation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }
}
