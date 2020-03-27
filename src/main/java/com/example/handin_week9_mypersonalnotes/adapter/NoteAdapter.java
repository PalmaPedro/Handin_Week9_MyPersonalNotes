package com.example.handin_week9_mypersonalnotes.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handin_week9_mypersonalnotes.DetailsActivity;
import com.example.handin_week9_mypersonalnotes.MainActivity;
import com.example.handin_week9_mypersonalnotes.R;
import com.example.handin_week9_mypersonalnotes.model.Note;
import com.example.handin_week9_mypersonalnotes.view.ViewHolder;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<ViewHolder> {

    private MainActivity mainActivity;
    private List<Note> notes;
    Context context;

    public NoteAdapter(MainActivity mainActivity, List<Note> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item clicks here
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                //this will be called when user clicks item

                //show data in toast on clicking
                String title = notes.get(position).getTitle();
                String description = notes.get(position).getDescription();
                Toast.makeText(mainActivity, title + "\n" + description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(View view, final int position) {
                //this will be called when user long clicks item
                // update is clicked
                //get data
                String id = notes.get(position).getId();
                String title = notes.get(position).getTitle();
                String description = notes.get(position).getDescription();
                //intent to start activity
                Intent intent = new Intent(mainActivity, DetailsActivity.class);
                //put data in the intent
                intent.putExtra("pId", id);
                intent.putExtra("pTitle", title);
                intent.putExtra("pDescription", description);
                //start activity
                mainActivity.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind views / set data
        holder.mTitleTv.setText(notes.get(position).getTitle());
        holder.mDescriptionTv.setText(notes.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}