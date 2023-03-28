package com.example.recipe_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;


public class HomeFragment extends Fragment {



    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {

        super.onStart();
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        startActivity(intent);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: Fetch all recipes from firebase as the data
       ArrayList<RecipeModel> allRecipes = new ArrayList<>();
        db.collection("recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {

                    for(QueryDocumentSnapshot doc : task.getResult())
                        allRecipes.add(new RecipeModelBuilder().
                                addName((String)doc.get("name"))
                                .addContent((String)doc.get("content"))
                                .addPictures((String)doc.get("picture"))
                                .addTags((String)doc.get("tags"))
                                .Build()
                        );
                }
                else{
                    new AlertDialog.Builder(getContext()).setTitle("Error")
                            .setMessage(task.getException().getMessage())
                            .create()
                            .show();
                }
            }
        });



        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        HomeListAdapter homeAdapter = new HomeListAdapter(getContext(), allRecipes);

        recyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();


    }
}