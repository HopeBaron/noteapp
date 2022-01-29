package com.hopebaron.noteapp;

import androidx.room.*;

import java.util.List;

@Dao
public abstract class NoteDao {
    @Query("SELECT * FROM note")
    public abstract List<Note> getAll();

    @Query("SELECT * FROM note WHERE id=:id")
    public abstract Note get(long id);
    @Query("UPDATE note SET title=:title, content=:content WHERE id=:id")
    public abstract void update(long id, String title, String content);
    @Update
    public abstract void update(Note note);

    @Query("DELETE FROM note WHERE id=:id")
    public abstract void delete(long id);
    @Delete
    public abstract void delete(Note note);

    @Insert
    public abstract long insert(Note note);

    @Transaction
    public Note upsert(Note note) {
        long id = insert(note);
        if(id == -1) {
            update(note);
            return get(note.getId());
        }
        return get(id);

    }

}
