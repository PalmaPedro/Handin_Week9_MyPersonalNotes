package com.example.handin_week9_mypersonalnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DetailsActivity extends AppCompatActivity {

    //views
    EditText mTitle, mDescription;
    Button mSaveBtn;
    Button mDeleteBtn;

    //progress dialog
    ProgressDialog pd;

    //firestore instance
    FirebaseFirestore db;

    String pId, pTitle, pDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
        pd = new ProgressDialog(this);

        //firestore
        db = FirebaseFirestore.getInstance();

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
                    updateNote(id, title, description);

                } else {
                    //adding new
                    //input data
                    String title = mTitle.getText().toString().trim();
                    String description = mDescription.getText().toString().trim();
                    // function call to add note
                    addNote(title, description);
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
                deleteNote(id, title, description);
            }
        });
    }

    private void deleteNote(String id, String title, String description) {
        //set title of progress bar
        pd.setTitle("Deleting note...");
        //show progress bar when user clicks save button
        pd.show();
        db.collection("notes").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when updated successfully
                        pd.dismiss();
                        Toast.makeText(DetailsActivity.this, "Deleted...", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is an error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateNote(String id, String title, String description) {
        //set title of progress bar
        pd.setTitle("Updating note...");
        //show progress bar when user clicks save button
        pd.show();
        db.collection("notes").document(id)
            .update("title", title, "description", description)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //called when updated successfully
                    pd.dismiss();
                    Toast.makeText(DetailsActivity.this, "Updated...", Toast.LENGTH_SHORT).show();

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //called when there is an error
                    pd.dismiss();
                    //get and show error message
                    Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }

    private void addNote(String title, String description) {
        //set title of progress bar
        pd.setTitle("Adding note to Firestore");
        //show progress bar when user clicks save button
        pd.show();
        //random id for each note to be stored in Firestore
        String id = UUID.randomUUID().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("id", id); // generated id for note
        map.put("title", title);
        map.put("description", description);

        //add note
        db.collection("notes").document(id).set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when note is added successfully
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(DetailsActivity.this, "Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //added if error occurs
                        Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

