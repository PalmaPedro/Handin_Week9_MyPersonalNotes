package com.example.handin_week9_mypersonalnotes.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handin_week9_mypersonalnotes.R;
import com.example.handin_week9_mypersonalnotes.storage.FirebaseRepo;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitleTv, mDescriptionTv;
    public View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
                return true;
            }
        });
        //initialize views with note_item.xml
        mTitleTv = itemView.findViewById(R.id.tView_title);
        mDescriptionTv = itemView.findViewById(R.id.tView_description);
    }

    private ViewHolder.ClickListener mClickListener;

    //interface for click listener
    public interface ClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}
