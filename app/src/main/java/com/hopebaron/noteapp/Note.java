package com.hopebaron.noteapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "content")
    private String content;

    protected void setId(long id) {
        this.id = id;
    }

    protected void setDate(String date) {
        this.date = date;
    }

    private String date;

    public Note(String title, String content) {
        setTitle(title);
        setContent(content);
        this.date = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }
}
