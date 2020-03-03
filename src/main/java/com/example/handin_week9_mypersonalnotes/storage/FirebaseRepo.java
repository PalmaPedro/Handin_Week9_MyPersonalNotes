package com.example.handin_week9_mypersonalnotes.storage;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handin_week9_mypersonalnotes.model.Note;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirebaseRepo {

    public static List<Note> notes = new ArrayList<>();
    private final static String notesPath = "notes";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static RecyclerView.Adapter adapter;

    public static Note getNote(int index) {
        if (index >= notes.size()) return new Note("empty", "");
        return notes.get(index);
    }

    static { //make sure the listener starts as soon as possible
       startNoteListener();
    }

    /*
    public static void editNote(){}

    public static void deleteNote(int index){}
     */
    private static void startNoteListener() {
        FirebaseRepo.db.collection(notesPath).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot values, @Nullable FirebaseFirestoreException e) {
                FirebaseRepo.notes.clear();
                assert values != null;
                for (DocumentSnapshot snap : values.getDocuments()) {
                   //Log.i("all", "read from FB" + snap.get("title").toString() + " " + snap.get("description").toString());
                    FirebaseRepo.notes.add(new Note(snap.get("title").toString(), snap.get("description").toString()));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
