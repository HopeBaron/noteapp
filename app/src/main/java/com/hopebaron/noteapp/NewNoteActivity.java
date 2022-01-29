package com.hopebaron.noteapp;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class NewNoteActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText contentEditText;
    private NoteRepository repository;
    private Long noteId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        repository = NoteRepository.getInstance(getApplicationContext());
        String noteIdExtra = getIntent().getStringExtra("noteId");
        if (noteIdExtra != null) {
            noteId = Long.parseLong(noteIdExtra);
            Note note = repository.get(noteId);
            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        long itemId = item.getItemId();
        if (itemId == R.id.done_menu_button) onCheck();
        else if (itemId == R.id.delete_menu_button) onDelete();
        return super.onOptionsItemSelected(item);
    }

    private void onCheck() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        if (noteId == null) {
            try {
                noteId = repository.insert(title, content);
            } catch (EmptyNoteParameterException e) {
                prompt(e.getMessage()).show();
            }
            return;
        }
        repository.update(noteId, title, content);
    }

    private void onDelete() {
        if (noteId == null) {
            finish();
            return;
        }
        repository.delete(noteId);
        noteId = null;
        onDelete();
    }

    private Toast prompt(String message) {
        return Toast.makeText(this, message, Toast.LENGTH_LONG);
    }
}