package com.example.handin_week9_mypersonalnotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handin_week9_mypersonalnotes.R;
import com.example.handin_week9_mypersonalnotes.storage.FirebaseRepo;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return FirebaseRepo.notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        int rowNumber = 0;
        TextView textView;
        TextView tvTitle;
        TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tView_title);
            tvDescription = itemView.findViewById(R.id.tView_description);
        }

        public void setPosition(int position) {
            rowNumber = position;
            textView.setText(FirebaseRepo.notes.get(position).getTitle());
        }
    }
}
