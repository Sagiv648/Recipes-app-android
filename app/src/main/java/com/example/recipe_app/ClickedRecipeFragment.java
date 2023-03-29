package com.example.recipe_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;


public class ClickedRecipeFragment extends Fragment {

    RecipeModel pickedRecipe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Serializable recipe = getArguments().getSerializable("item");
        if(recipe == null)
        {
            Log.d("nav", "this is clicked recipe fragment");
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        }
        else{
            pickedRecipe = (RecipeModel) recipe;
            Log.d("name", pickedRecipe.getName());
            Log.d("tags", pickedRecipe.getTags());
            Log.d("picture", pickedRecipe.getPicture());
            Log.d("content", pickedRecipe.getContent());
        }
        View view = inflater.inflate(R.layout.fragment_clicked_recipe, container, false);




        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}