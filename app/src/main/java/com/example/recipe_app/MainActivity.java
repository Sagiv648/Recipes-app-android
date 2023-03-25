package com.example.recipe_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


//@style/Theme.Recipe_App
//@style/Theme.AppCompat.Light
public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextInputEditText emailInput, passwordInput;
    Button signInBtn, signUpBtn;
    static boolean isRunning;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = !isRunning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRunning = !isRunning;
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if(currentUser != null)
                {
                    if(MainActivity.isRunning)
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
                else
                {
                    if(!MainActivity.isRunning)
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }


            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
        signInBtn.setOnClickListener((v) -> {
            if(passwordInput.getText() != null && emailInput.getText() != null &&
                    (passwordInput.getText().toString().length() * emailInput.getText().toString().length()) == 0)
            {
                new AlertDialog.Builder(MainActivity.this).

                        setMessage(R.string.invalidFieldsError).
                        setTitle("Error").

                        setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passwordInput.setText("");
                        emailInput.setText("");
                    }
                }).
                        create().
                        show();
            }
            else
            {
                mAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Failure", e.getMessage());

                                new AlertDialog.Builder(MainActivity.this)
                                        .setMessage(e.getMessage())
                                        .setTitle("Error")
                                        .setPositiveButton("Okay",
                                                (dialog,which) -> {
                                                    passwordInput.setText("");
                                                    emailInput.setText("");
                                        })
                                        .create()
                                        .show();
                            }
                        })      ;
            }
        });


    }
    @Override
    public void onStart()
    {
        super.onStart();


//        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//
//                Intent intent = new Intent(getApplicationContext(),currentUser != null ? HomeActivity.class : MainActivity.class);
//                //intent.setClass(getApplicationContext(),currentUser != null ? HomeActivity.class : MainActivity.class);
//                startActivity(intent);
//
//            }
//        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);

        }
    }
}