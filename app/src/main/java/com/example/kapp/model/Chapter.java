package com.example.kapp.model;

public class Chapter {
    private int chapter;
    private String name;
    private String content;

    public Chapter(int chapter, String name, String content) {
        this.chapter = chapter;
        this.name = name;
        this.content = content;
    }

    public Chapter() {
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
