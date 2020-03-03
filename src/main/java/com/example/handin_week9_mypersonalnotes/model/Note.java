package com.example.handin_week9_mypersonalnotes.model;

public class Note {

    private String title;
    private String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // empty constructor needed
    public Note() { }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
