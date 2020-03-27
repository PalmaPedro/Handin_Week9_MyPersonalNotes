package com.example.handin_week9_mypersonalnotes.model;

public class Note {

    String title = "";
    String description = "";
    String id = "";

    public Note(String id, String title, String description) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

}
