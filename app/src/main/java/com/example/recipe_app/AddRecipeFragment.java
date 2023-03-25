package com.example.recipe_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AddRecipeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        UserModel user = (UserModel)(getArguments().getSerializable("user"));

        Log.d("email:", user.getEmail());
        Log.d("uuid:",user.getUuid());
        for (Object o : user.getUploaded_recipes())
        {
            Log.d("record:", o.toString());
        }

        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }
}