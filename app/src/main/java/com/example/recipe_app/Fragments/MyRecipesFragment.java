package com.example.recipe_app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe_app.Adapters.RecipeListAdapter;
import com.example.recipe_app.Models.RecipeModel;
import com.example.recipe_app.Models.RecipeModelBuilder;
import com.example.recipe_app.Models.UserModel;
import com.example.recipe_app.Models.UserModelBuilder;
import com.example.recipe_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class MyRecipesFragment extends Fragment {


    FirebaseFirestore db;
    ArrayList<RecipeModel> allRecipes;
    Button myRecipesSearchBtn;
    EditText MyRecipesInput;
    FirebaseUser fbUser;
    ArrayList<RecipeModel> allRecipesHolder;

    RecipeListAdapter listAdapter;

    UserModel user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        allRecipes = new ArrayList<>();
        allRecipesHolder = new ArrayList<>();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_recipes, container, false);
        myRecipesSearchBtn = view.findViewById(R.id.ownRecipeNameSearchBtn);
        MyRecipesInput = view.findViewById(R.id.ownRecipeNameSearchInput);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.myRecipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        listAdapter = new RecipeListAdapter(getContext(),allRecipes);
        recyclerView.setAdapter(listAdapter);



        db.collection("users").document(fbUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot doc) {

                if(doc.exists())
                {
                    Log.e("doc", doc.get("email").toString());
                    user = new UserModelBuilder().

                            setUploadedRecipes(new ArrayList<String>((List) (doc.get("uploaded_recipes"))))
                            .setUpvotedRecipes(new ArrayList<>((List)(doc.get("upvoted_recipes"))))
                            .build();
                    ArrayList<String> uploaded = user.getUploaded_recipes();
                    Log.e("uploaded", uploaded.toString());

                    for(String s : uploaded)
                    {
                        Log.e("recipe", s);
                        db.collection("recipes").document(s).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                Log.d("recipe", documentSnapshot.get("name").toString());

                                allRecipes.add(new RecipeModelBuilder().addId(s).
                                        addName((String)documentSnapshot.get("name")).
                                        addPictures((String) documentSnapshot.get("picture"))
                                        .addContent((String)documentSnapshot.get("content"))
                                        .addTags((String) documentSnapshot.get("tags")).
                                        addUpvotes((Long)documentSnapshot.get("upvotes") ).Build());
                                listAdapter.notifyDataSetChanged();

                            }
                        });
                    }
                }

            }

        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        listAdapter.notifyDataSetChanged();
                    }
            }
        });

    }


}