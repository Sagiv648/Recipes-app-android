package com.example.recipe_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe_app.Models.RecipeModel;
import com.example.recipe_app.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MyRecipesFragment extends Fragment {


    FirebaseFirestore db;
    ArrayList<RecipeModel> allRecipes;
    Button myRecipesSearchBtn;
    EditText MyRecipesInput;

    ArrayList<RecipeModel> allRecipesHolder;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        allRecipes = new ArrayList<>();
        allRecipesHolder = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_recipes, container, false);
        myRecipesSearchBtn = view.findViewById(R.id.ownRecipeNameSearchBtn);
        MyRecipesInput = view.findViewById(R.id.ownRecipeNameSearchInput);


        return view;
    }
}