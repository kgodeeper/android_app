package com.example.kapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListCategory {
    private List<Category> categories;

    public ListCategory(List<Category> categories) {
        this.categories = categories;
    }

    public ListCategory() {
        categories = new ArrayList<>();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
