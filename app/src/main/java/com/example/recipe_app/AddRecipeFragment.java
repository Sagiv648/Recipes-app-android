package com.example.recipe_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;


public class AddRecipeFragment extends Fragment {

    EditText contentInput;

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
        View view = inflater.inflate(R.layout.fragment_add_recipe,container,false);
        contentInput = view.findViewById(R.id.contentInput);

        return view;
    }
}