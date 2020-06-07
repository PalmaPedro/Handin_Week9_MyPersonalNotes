package com.example.handin_week9_mypersonalnotes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.handin_week9_mypersonalnotes.R;
import com.example.handin_week9_mypersonalnotes.storage.FirebaseRepo;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsActivity extends AppCompatActivity {
    //views
    EditText mTitle, mDescription;
    Button mSaveBtn;
    Button mDeleteBtn;

    String pId, pTitle, pDescription;

    FirebaseRepo firebaseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        firebaseRepo = new FirebaseRepo(this);

        //initialize views with its xml
        mTitle = findViewById(R.id.etTitle);
        mDescription = findViewById(R.id.etDescription);
        mSaveBtn = findViewById(R.id.btnSaveNote);
        mDeleteBtn = findViewById(R.id.btnDeleteNote);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSaveBtn.setText("Update");
            //get data
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");
            pDescription = bundle.getString("pDescription");
            //set data
            mTitle.setText(pTitle);
            mDescription.setText(pDescription);
        } else {
            mSaveBtn.setText("Save");
        }
        //progress dialog
        firebaseRepo.pd = new ProgressDialog(this);

        //firestore
        firebaseRepo.db = FirebaseFirestore.getInstance();

        //click button to save note to firestore
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    //updating
                    //input data
                    String id = pId;
                    String title = mTitle.getText().toString().trim();
                    String description = mDescription.getText().toString().trim();
                    //function call to update data
                    firebaseRepo.updateNote(id, title, description);

                } else {
                    //adding new
                    //input data
                    String title = mTitle.getText().toString().trim();
                    String description = mDescription.getText().toString().trim();
                    // function call to add note
                    firebaseRepo.addNote(title, description);
                }
            }
        });

        //click button to delete note from firebase
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleting
                String id =pId;
                String title = mTitle.getText().toString().trim();
                String description = mDescription.getText().toString().trim();
                //function call to delete note
                firebaseRepo.deleteNote(id);
            }
        });
    }


    //go back to main activity if back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}

