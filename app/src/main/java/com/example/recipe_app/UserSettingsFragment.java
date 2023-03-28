package com.example.recipe_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class UserSettingsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int id = getArguments().getInt("the item", 0);
        if(id == 100)
        {
            Log.d("I got the item", "the click got me the item to here to display in full");
        }
        else{
            Log.d("No item", "no");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_settings, container, false);
    }
}