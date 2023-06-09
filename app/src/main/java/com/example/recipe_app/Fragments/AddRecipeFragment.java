package com.example.recipe_app.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recipe_app.Models.UserModel;
import com.example.recipe_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddRecipeFragment extends Fragment {

    EditText contentInput,tagsInput,recipeNameInput;
    View generalView;
    Button uploadImage, uploadRecipe;
    Uri imageUri;
    TextView imgStatus;
    FirebaseFirestore db;
    FirebaseStorage storage;
    String uniqueRecipeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        UserModel user = (UserModel)(getArguments().getSerializable("user"));

        db = FirebaseFirestore.getInstance();

        generalView = inflater.inflate(R.layout.fragment_add_recipe,container,false);
        contentInput = generalView.findViewById(R.id.contentInput);
        uploadImage = generalView.findViewById(R.id.upload_image_btn);
        imgStatus = generalView.findViewById(R.id.image_uploaded_status);
        uploadRecipe = generalView.findViewById(R.id.upload_recipe_btn);
        recipeNameInput = generalView.findViewById(R.id.recipeNameInput);
        tagsInput = generalView.findViewById(R.id.tagsInput);
        storage = FirebaseStorage.getInstance();


        //25 - 29 chars per line

        uploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((recipeNameInput.getText().toString().length() *
                        tagsInput.getText().length() *
                        contentInput.getText().length()) == 0 || imageUri == null)
                        new AlertDialog.Builder(getContext()).
                                setMessage("All fields must be present along with a picture.").
                                setTitle("Error")
                                .create().show();


                else
                {

                    ProgressDialog pD = new ProgressDialog(getContext());
                    pD.setTitle("Uploading");
                    pD.setMessage("Uploading recipe...");
                    pD.create();
                    pD.show();
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", recipeNameInput.getText().toString());
                    map.put("picture", "awaiting upload");
                    map.put("content", contentInput.getText().toString());
                    map.put("tags", tagsInput.getText().toString());
                    map.put("upvotes", 0);
                    user.setNum_recipes(user.getNum_recipes() + 1);
                    uniqueRecipeId = user.getNum_recipes() + "_" + user.getUuid();
                    user.getUploaded_recipes().add(uniqueRecipeId);
                    db.collection("recipes").document(uniqueRecipeId).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Map<String, Object> userMap = new HashMap<>();


                            userMap.put("recipes_number", user.getNum_recipes());
                            userMap.put("uploaded_recipes", user.getUploaded_recipes());

                            if (task.isSuccessful()) {
                                if(imageUri != null){
                                    storage.getReference("images/"+uniqueRecipeId).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                task.getResult().getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Uri> task) {
                                                        if (task.isSuccessful()) {
                                                            Uri uri = task.getResult();
                                                            Map<String, String> mapping = new HashMap<>();
                                                            mapping.put("picture", uri.toString());
                                                            db.collection("recipes").document(uniqueRecipeId).set(mapping, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (!task.isSuccessful()) {


                                                                        new AlertDialog.Builder(getContext()).
                                                                                setMessage(task.getException().getMessage())
                                                                                .setTitle("Error")
                                                                                .create()
                                                                                .show();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            new AlertDialog.Builder(getContext()).
                                                                    setMessage(task.getException().getMessage())
                                                                    .setTitle("Error")
                                                                    .create()
                                                                    .show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                new AlertDialog.Builder(getContext()).
                                                        setMessage(task.getException().getMessage())
                                                        .setTitle("Error")
                                                        .create()
                                                        .show();
                                            }
                                            imageUri = null;
                                        }
                                    });
                                }

                                db.collection("users").document(user.getUuid()).set(userMap, SetOptions.merge())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (!task.isSuccessful()) {
                                                    new AlertDialog.Builder(getContext()).
                                                            setMessage(task.getException().getMessage())
                                                            .setTitle("Error")
                                                            .create()
                                                            .show();
                                                }
                                            }
                                        });

                            } else {
                                new AlertDialog.Builder(getContext()).
                                        setMessage(task.getException().getMessage())
                                        .setTitle("Error")
                                        .create()
                                        .show();
                            }
                        }
                    });

                    pD.dismiss();
                    imgStatus.setText("");
                    imgStatus.setVisibility(View.INVISIBLE);
                    contentInput.setText("");
                    recipeNameInput.setText("");
                    tagsInput.setText("");
                }


            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);

            }
        });
        return generalView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            Log.d("URI Image:", imageUri.toString());
            imgStatus.setText("Image selected.");
            imgStatus.setVisibility(View.VISIBLE);
        }
        else{
            Log.d("Something else:", "didn't get URI");
        }
    }
}