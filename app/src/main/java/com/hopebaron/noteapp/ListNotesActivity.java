package com.hopebaron.noteapp;

import android.content.Intent;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListNotesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NoteRepository repository;
    TextView messageView;
    SearchView searchView;
    List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        searchView = findViewById(R.id.notes_search_view);
        recyclerView = findViewById(R.id.recycler_view);
        messageView = findViewById(R.id.no_notes_message);
        repository = NoteRepository.getInstance(getApplicationContext());
        setupRecycleView(notes);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String terms) {
                NoteListAdapter adapter = (NoteListAdapter) recyclerView.getAdapter();
                adapter.getFilter().filter(terms);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        searchView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        messageView.setVisibility(View.INVISIBLE);
        notes.clear();
        notes.addAll(repository.getAll());
        NoteListAdapter adapter = (NoteListAdapter) recyclerView.getAdapter();
        adapter.getFilter().filter(searchView.getQuery());
        if(notes.isEmpty()) {
            searchView.setVisibility(View.INVISIBLE);
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