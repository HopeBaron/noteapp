package com.hopebaron.noteapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter  extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private final List<Note> viewNotes = new ArrayList<>();
    private List<Note> notes;
    private final Filter filter = new NotesFilter();
    private ItemPressedListener listener = null;

    public NoteListAdapter(List<Note> notes) {
        this.notes = notes;
        this.viewNotes.addAll(this.notes);
    }

    public Filter getFilter() {
        return filter;
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
            if (listener == null) return;
            cardView.setOnClickListener(view -> listener.listener(position, note));
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = viewNotes.get(position);
        holder.getTitleText().setText(note.getTitle());
        holder.getContentText().setText(note.getContent());
        holder.getDateText().setText(note.getDate());
        holder.bind(position, note);
    }

    @Override
    public int getItemCount() {
        return viewNotes.size();
    }
    private class NotesFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Note> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(notes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Note note : notes) {
                    boolean titleMatchPattern = note.getTitle().toLowerCase().contains(filterPattern);
                    boolean contentMatchPattern = note.getContent().toLowerCase().contains(filterPattern);
                    if (titleMatchPattern || contentMatchPattern) {
                        filteredList.add(note);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            viewNotes.clear();
            viewNotes.addAll((List) results.values);
            notifyDataSetChanged();
        }
    }
}
