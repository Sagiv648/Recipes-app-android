package com.example.recipe_app.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipe_app.Activities.HomeActivity;
import com.example.recipe_app.Models.RecipeModel;
import com.example.recipe_app.R;
import com.example.recipe_app.Utils.DownloadImageTask;

import java.io.Serializable;


public class ClickedRecipeFragment extends Fragment {

    RecipeModel pickedRecipe;
    TextView name,tags,content;
    ImageView img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Serializable recipe = getArguments().getSerializable("item");
        View view = inflater.inflate(R.layout.fragment_clicked_recipe, container, false);
        name = view.findViewById(R.id.nameDisplay);
        tags = view.findViewById(R.id.tagsDisplay);
        content = view.findViewById(R.id.contentDisplay);
        img = view.findViewById(R.id.recipeImg);
        if(recipe == null)
        {
            Log.d("nav", "this is clicked recipe fragment");
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        }
        else{
            pickedRecipe = (RecipeModel) recipe;
            name.setText(pickedRecipe.getName());
            tags.setText(pickedRecipe.getTags());
            content.setText(pickedRecipe.getContent());

            new DownloadImageTask(img)
                    .execute(pickedRecipe.getPicture());

            Log.d("name", pickedRecipe.getName());
            Log.d("tags", pickedRecipe.getTags());
            Log.d("picture", pickedRecipe.getPicture());
            Log.d("content", pickedRecipe.getContent());



        }





        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}