package com.example.kapp.model;

import java.util.ArrayList;
import java.util.List;

public class ChapterList {
    private List<Chapter> chapters;

    public ChapterList(List<Chapter> list) {
        this.chapters = list;
    }

    public ChapterList() {
        chapters = new ArrayList<>();
    }

    public List<Chapter> getList() {
        return chapters;
    }

    public void setList(List<Chapter> list) {
        this.chapters = list;
    }
}
