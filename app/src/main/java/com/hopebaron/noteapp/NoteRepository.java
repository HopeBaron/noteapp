package com.hopebaron.noteapp;

import android.content.Context;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private final AppDatabase database;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static NoteRepository INSTANCE = null;

    public static NoteRepository getInstance(Context context) {
        if(INSTANCE != null) return INSTANCE;
        INSTANCE = new NoteRepository(context, "notesApp");
        return INSTANCE;
    }
    private NoteRepository(Context context, String name) {
        database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, name)
                .fallbackToDestructiveMigration()
                .build();
    }

    public long insert(String title, String content) throws EmptyNoteParameterException {
        String parsedTitle = title.trim();
        String parsedContent = content.trim();
        if (parsedTitle.isEmpty()) throw new EmptyNoteParameterException("title");
        if (parsedContent.isEmpty()) throw new EmptyNoteParameterException("content");
        Callable<Long> callable = () -> database.noteDao().insert(new Note(parsedTitle, parsedContent));
        long id = -1;
        try {
            id = executor.submit(callable).get();
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return id;
    }

    public Note update(long id, String title, String content) {

        Runnable callable = () -> database.noteDao().update(id, title, content);
        try {
            executor.submit(callable).get();
        } catch (Exception ignore) {
        }
        return get(id);
    }
    public Note update(Note note) {
        Runnable callable = () -> database.noteDao().update(note);
        try {
            executor.submit(callable).get();
        } catch (Exception ignore) {
        }
        return get(note.getId());
    }

    public List<Note> getAll() {
        ArrayList<Note> notes = new ArrayList<>();
        Callable<List<Note>> callable = () -> database.noteDao().getAll();
        try {
            List<Note> fetchedNotes = executor.submit(callable).get();
            if (fetchedNotes != null) notes.addAll(fetchedNotes);
        } catch (Exception ignored) {
        }
        return notes;
    }

    public Note get(long id) {
        Callable<Note> callable = () -> database.noteDao().get(id);
        Note note = null;
        try {
            note = executor.submit(callable).get();
        } catch (Exception ignore) {
        }
        return note;
    }

    public void delete(Note note) {
        delete(note.getId());
    }

    public void delete(long id) {
        Runnable callable = () -> database.noteDao().delete(id);
        try {
            executor.submit(callable).get();
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }
}
