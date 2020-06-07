package com.example.handin_week9_mypersonalnotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.handin_week9_mypersonalnotes.R;
import com.example.handin_week9_mypersonalnotes.adapter.NoteAdapter;
import com.example.handin_week9_mypersonalnotes.model.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Note> notes = new ArrayList<>();
    RecyclerView recyclerView;
    //layout manager for recycler view
    RecyclerView.LayoutManager layoutManager;
    //firestore instance;
    FirebaseFirestore db;
    NoteAdapter adapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize firestore
        db = FirebaseFirestore.getInstance();

        //initialize views
        recyclerView = findViewById(R.id.recycler_view);

        //set recycler view properties
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //init progress dialog
        pd = new ProgressDialog(this);

        //show notes in recycler view
        showNotes();

        //handle FloatingAction button click (go to AddNoteActivity)
        FloatingActionButton fab = findViewById(R.id.btnAddNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DetailsActivity.class));
                finish();
            }
        });
    }

    private void showNotes() {
        //set title of progress dialog
        pd.setTitle("Loading Data...");
        //show progress dialog
        pd.show();
        db.collection("notes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when data is retrieved
                        pd.dismiss();
                        //show notes
                        for (DocumentSnapshot doc : task.getResult()) {
                            Note note = new Note(doc.getString("id"),
                                    doc.getString("title"),
                                    doc.getString("description"));
                            notes.add(note);
                        }
                        //adapter
                        adapter = new NoteAdapter(MainActivity.this, notes);
                        //set adapter to recycler view
                        recyclerView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
