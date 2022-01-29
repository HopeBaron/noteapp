package com.hopebaron.noteapp;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListNotesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NoteRepository repository;
    TextView messageView;
    List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        recyclerView = findViewById(R.id.recycler_view);
        messageView = findViewById(R.id.no_notes_message);
        repository = NoteRepository.getInstance(getApplicationContext());
        setupRecycleView(notes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setVisibility(View.VISIBLE);
        messageView.setVisibility(View.INVISIBLE);
        notes.clear();
        notes.addAll(repository.getAll());
        recyclerView.getAdapter().notifyDataSetChanged();
        if(notes.isEmpty()) {
            recyclerView.setVisibility(View.INVISIBLE);
            messageView.setVisibility(View.VISIBLE);
        }
    }

    public void openNewNote(View view) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivity(intent);
    }

    private void setupRecycleView(List<Note> notes) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        NoteListAdapter adapter = new NoteListAdapter(notes);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemPressedListener(this::listener);
    }

    private void listener(int position, Note note) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        intent.putExtra("noteId", String.valueOf(note.getId()));
        startActivity(intent);
    }

}