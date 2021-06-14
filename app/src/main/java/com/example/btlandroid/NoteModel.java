package com.example.btlandroid;

public class NoteModel {
    String id;
    String note_content;
    String created_at;

    public NoteModel(){

    }
    public NoteModel(String id, String note_content, String created_at) {
        this.id = id;
        this.note_content = note_content;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
