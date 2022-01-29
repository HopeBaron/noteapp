package com.hopebaron.noteapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class NoteListAdapter  extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private final List<Note> notes;
    private ItemPressedListener listener = null;
    private static final int RGB_MAX_RAND = 256;
    public NoteListAdapter(List<Note> notes) {
        this.notes = notes;
    }
    public void setOnItemPressedListener(ItemPressedListener listener) {
        this.listener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView titleText;
        private final TextView contentText;
        private final TextView dateText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            titleText = itemView.findViewById(R.id.card_title_text);
            contentText = itemView.findViewById(R.id.card_content_text);
            dateText = itemView.findViewById(R.id.card_date_text);
        }

        public TextView getTitleText() {
            return titleText;
        }

        public TextView getContentText() {
            return contentText;
        }

        public TextView getDateText() {
            return dateText;
        }

        public CardView getCardView() {
            return cardView;
        }


        public void bind(int position, Note note) {
            if(listener == null) return;
            cardView.setOnClickListener(view -> listener.listener(position,note));
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.getTitleText().setText(note.getTitle());
        holder.getContentText().setText(note.getContent());
        holder.getDateText().setText(note.getDate());
        holder.bind(position, note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
