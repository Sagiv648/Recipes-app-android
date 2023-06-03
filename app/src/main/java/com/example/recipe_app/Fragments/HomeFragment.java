package com.example.recipe_app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe_app.Adapters.HomeListAdapter;
import com.example.recipe_app.Models.RecipeModel;
import com.example.recipe_app.Models.RecipeModelBuilder;
import com.example.recipe_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;


public class HomeFragment extends Fragment {


    HomeListAdapter homeAdapter;
    FirebaseFirestore db;
    ArrayList<RecipeModel> allRecipes;
    Button searchBtn;
    EditText inputName;


    ArrayList<RecipeModel> allRecipesHolder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        allRecipes = new ArrayList<>();
        allRecipesHolder = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        inputName = view.findViewById(R.id.recipeNameToSearchInput);
        searchBtn = view.findViewById(R.id.searchRecipeByNameBtn);








        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        startActivity(intent);




    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch all recipes from firebase as the data
        allRecipes = new ArrayList<>();

        //Recycler view configuration
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        homeAdapter = new HomeListAdapter(getContext(), allRecipes);

        recyclerView.setAdapter(homeAdapter);



        db.collection("recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {

                        allRecipes.add(new RecipeModelBuilder()
                                .addId(doc.getId())
                                .addName((String)doc.get("name"))
                                .addContent((String)doc.get("content"))
                                .addPictures((String)doc.get("picture"))
                                .addTags((String)doc.get("tags"))
                                .addUpvotes((Long)doc.get("upvotes"))
                                .Build()
                        );
                        allRecipesHolder.add(allRecipes.get(allRecipes.size()-1));
                    }

                    homeAdapter.notifyDataSetChanged();
                }
                else{
                    new AlertDialog.Builder(getContext()).setTitle("Error")
                            .setMessage(task.getException().getMessage())
                            .create()
                            .show();
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allRecipes.clear();
                if(inputName.getText().toString().length() > 0)
                {

                    for(RecipeModel r : allRecipesHolder)
                    {
                        if(r.getName().equals( inputName.getText().toString())){
                            allRecipes.add(r);


                        }

                    }

                }
                else
                {



                    for(RecipeModel r : allRecipesHolder)
                    {
                        allRecipes.add(r);

                    }


                }
                homeAdapter.notifyDataSetChanged();

            }
        });



    }
}