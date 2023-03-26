package com.example.recipe_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button logout;
    Toolbar toolbar;
    TextView navHeaderTitle;
    HashMap<Integer, Fragment> idFragmentMapping;
    Bundle bundle;
    UserModel user;
    ArrayList<RecipeModel> allRecipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        idFragmentMapping = new HashMap<>();
        idFragmentMapping.put(R.id.home_nav, new HomeFragment());
        idFragmentMapping.put(R.id.my_recipes_nav, new MyRecipesFragment());
        idFragmentMapping.put(R.id.add_recipe_nav, new AddRecipeFragment());
        idFragmentMapping.put(R.id.remove_recipe_nav, new RemoveRecipeFragment());
        idFragmentMapping.put(R.id.user_settings_nav, new UserSettingsFragment());
        bundle = new Bundle();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String email = mAuth.getCurrentUser().getEmail();
        String uuid = mAuth.getUid();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if(currentUser == null)
                {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                }



            }
        });

//        allRecipes = new ArrayList<>();
//        db.collection("recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful())
//                {
//
//                    for(QueryDocumentSnapshot doc : task.getResult())
//                        allRecipes.add(new RecipeModelBuilder().
//                                addName((String)doc.get("name"))
//                                .addContent((String)doc.get("content"))
//                                .addPictures((String)doc.get("picture"))
//                                .addTags((String)doc.get("tags"))
//                                .Build()
//                        );
//                }
//                else{
//                    new AlertDialog.Builder(HomeActivity.this).setTitle("Error")
//                            .setMessage(task.getException().getMessage())
//                            .create()
//                            .show();
//                }
//            }
//        });


        DocumentReference docRef = db.collection("users").document(uuid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists())
                    {

                        user = new UserModelBuilder().
                                setEmail(email).
                                setUuid(uuid)
                                .setNumRecipes((Long) doc.get("recipes_number"))
                                .setUploadedRecipes(new ArrayList<String>((List) (doc.get("uploaded_recipes"))))
                                        .build();


//                        user.setEmail(email);
//                        user.setUuid(uuid);
//                        user.setUploaded_recipes(new ArrayList<String>((List) (doc.get("uploaded_recipes"))));




                    }

                }
                else
                {
                    new AlertDialog.Builder(HomeActivity.this).
                            setTitle("Error")
                            .setMessage(task.getException().getMessage())
                            .create().show();
                }
            }
        });

        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        ((TextView)headerView.findViewById(R.id.nav_Header_title)).setText(email);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        bundle.putParcelableArrayList("allRecipes", allRecipes);


        if(savedInstanceState==null)
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home_nav);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        Bundle bundle = new Bundle();
        toolbar.setTitle(item.getTitle());
        Integer id = item.getItemId();
        if(id == R.id.logout_nav)
            mAuth.signOut();
        else
        {
            if(id == R.id.home_nav)
            {
                Log.d("Is it home nav?", "yes");

                //bundle.putParcelableArrayList("allRecipes", allRecipes);
            }



            bundle.putSerializable("user", user);
            idFragmentMapping.get(item.getItemId()).setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, idFragmentMapping.get(id)).commit();
        }


        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}