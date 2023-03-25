package com.example.recipe_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.google.firebase.firestore.QuerySnapshot;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String user = mAuth.getCurrentUser().getUid();
        String email = mAuth.getCurrentUser().getEmail();



        //logout = findViewById(R.id.testLogout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//            }
//        });
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

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home_nav);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        toolbar.setTitle(item.getTitle());
        switch (item.getItemId())
        {
            case R.id.home_nav :

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.user_settings_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserSettingsFragment()).commit();
                break;
            case R.id.my_recipes_nav :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyRecipesFragment()).commit();
                break;
            case R.id.add_recipe_nav :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddRecipeFragment()).commit();
                break;
            case R.id.remove_recipe_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RemoveRecipeFragment()).commit();
                break;
            case R.id.logout_nav:
                //Logout
                Log.d("Logout", "logged out");
                mAuth.signOut();
            default:
                break;
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