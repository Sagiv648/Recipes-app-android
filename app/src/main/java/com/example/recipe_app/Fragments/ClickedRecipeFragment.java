package com.example.recipe_app.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipe_app.Activities.HomeActivity;
import com.example.recipe_app.Models.RecipeModel;
import com.example.recipe_app.Models.UserModel;
import com.example.recipe_app.R;
import com.example.recipe_app.Utils.DownloadImageTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;


public class ClickedRecipeFragment extends Fragment {

    RecipeModel pickedRecipe;
    TextView name,tags,content;
    FirebaseFirestore db;
    ImageView img;
    Button upvote_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Serializable recipe = getArguments().getSerializable("item");
        Serializable userArg = getArguments().getSerializable("user");
        //String uuid = getArguments().getString("uuid");
        View view = inflater.inflate(R.layout.fragment_clicked_recipe, container, false);
        name = view.findViewById(R.id.nameDisplay);
        tags = view.findViewById(R.id.tagsDisplay);
        content = view.findViewById(R.id.contentDisplay);
        img = view.findViewById(R.id.recipeImg);
        upvote_btn = view.findViewById(R.id.upvote_recipe_btn);
        if(recipe == null)
        {
//            Log.d("nav", "this is clicked recipe fragment");
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        }
        else{

            UserModel user = (UserModel)userArg;


            db = FirebaseFirestore.getInstance();
            pickedRecipe = (RecipeModel) recipe;

            name.setText(pickedRecipe.getName());
            tags.setText(pickedRecipe.getTags());
            content.setText(pickedRecipe.getContent());

            new DownloadImageTask(img)
                    .execute(pickedRecipe.getPicture());
            if(user.getUpvoted_recipes().contains(pickedRecipe.getId()))
            {
                upvote_btn.setText("Upvoted");
                upvote_btn.setEnabled(false);
            }
            else

            upvote_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.collection("recipes").document(pickedRecipe.getId()).update("upvotes", FieldValue.increment(1))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                upvote_btn.setText("Upvoted");
                                                upvote_btn.setEnabled(false);
                                                db.collection("users").
                                                        document(user.getUuid()).
                                                        update("upvoted_recipes", FieldValue.arrayUnion(pickedRecipe.getId()))
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful())
                                                                        {

                                                                            Toast.makeText(getContext(),"Recipe upvoted", Toast.LENGTH_SHORT);
                                                                        }
                                                                        else
                                                                        {
                                                                            new AlertDialog.Builder(getContext()).setMessage("Error ocurred: " + task.getException())
                                                                                    .setTitle("Error")
                                                                                    .create()
                                                                                    .show();
                                                                        }
                                                                    }
                                                                });

                                            }
                                            else
                                            {
                                                new AlertDialog.Builder(getContext()).setMessage("Error ocurred: " + task.getException())
                                                        .setTitle("Error")
                                                        .create()
                                                        .show();
                                            }
                                        }
                                    });

                }
            });


        }





        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}