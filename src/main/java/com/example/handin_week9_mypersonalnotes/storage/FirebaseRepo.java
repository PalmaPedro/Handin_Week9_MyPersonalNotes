package com.example.handin_week9_mypersonalnotes.storage;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.handin_week9_mypersonalnotes.ui.DetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebaseRepo {

    public DetailsActivity detailsActivity;

    //progress dialog
    public ProgressDialog pd;

    //firestore instance
    public FirebaseFirestore db;

    public FirebaseRepo(DetailsActivity detailsActivity) {
        this.detailsActivity = detailsActivity;
    }

    public void deleteNote(String id) {
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
                        Toast.makeText(detailsActivity, "Deleted...", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is an error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(detailsActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateNote(String id, String title, String description) {
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
                        Toast.makeText(detailsActivity, "Updated...", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is an error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(detailsActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void addNote(String title, String description) {
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
                        Toast.makeText(detailsActivity, "Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //added if error occurs
                        Toast.makeText(detailsActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}