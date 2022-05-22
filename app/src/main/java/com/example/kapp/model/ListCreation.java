package com.example.kapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListCreation {
    private List<Creation> creations;

    public ListCreation(List<Creation> creations) {
        this.creations = creations;
    }

    public ListCreation() {
        creations = new ArrayList<>();
    }

    public List<Creation> getCreations() {
        return creations;
    }

    public void setCreations(List<Creation> creations) {
        this.creations = creations;
    }
}
