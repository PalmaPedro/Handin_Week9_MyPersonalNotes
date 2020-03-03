package com.example.handin_week9_mypersonalnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.handin_week9_mypersonalnotes.adapter.NoteAdapter;
import com.example.handin_week9_mypersonalnotes.storage.FirebaseRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final static String notes = "notes";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NoteAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    public static final String INDEX_KEY = "INDEX_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        addNewNote();
        FirebaseRepo.adapter = adapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addNewNote(){
        DocumentReference docRef= db.collection(notes).document();
        Map<String, String> map=new HashMap<>();
        map.put("title1", "add to fb");
        map.put("title2", "also add to fb");
        docRef.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("all", "added successfully");
            }
        });
    }
}
