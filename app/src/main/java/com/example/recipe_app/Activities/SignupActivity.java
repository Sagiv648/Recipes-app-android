package com.example.recipe_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.recipe_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText emailInput,passwordInput;
    Button signUpBtn,returnBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailInput = findViewById(R.id.signUp_email_input);
        passwordInput = findViewById(R.id.signUpPassword_input);
        signUpBtn = findViewById(R.id.signUpBtn);
        returnBtn = findViewById(R.id.returnToLogin);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailInput.getText() != null && passwordInput.getText() != null
                && (emailInput.getText().toString().length() * passwordInput.getText().toString().length()) != 0)
                {
                    mAuth.createUserWithEmailAndPassword(emailInput.getText().toString(),passwordInput.getText().toString())
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    new AlertDialog.Builder(SignupActivity.this)
                                            .setTitle("Error")
                                            .setMessage(e.getMessage())
                                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    emailInput.setText("");
                                                    passwordInput.setText("");
                                                }
                                            })
                                            .create()
                                            .show();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    String uuid = authResult.getUser().getUid();
                                    String email = authResult.getUser().getEmail();
                                    Map<String,Object> mapping = new HashMap<String,Object>();
                                    mapping.put("email", email);
                                    mapping.put("recipes_number", 0);
                                    //TODO: Check later if null value in db causes problems
                                    mapping.put("uploaded_recipes", Arrays.asList());
                                    mapping.put("upvoted_recipes", Arrays.asList());

                                    db.collection("users")
                                            .document(uuid)
                                            .set(mapping)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(!task.isSuccessful())
                                                    {
                                                        mAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                new AlertDialog.Builder(SignupActivity.this)
                                                                        .setTitle("Error")
                                                                        .setMessage("Error occured with signing up")
                                                                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                emailInput.setText("");
                                                                                passwordInput.setText("");
                                                                            }
                                                                        })
                                                                        .create()
                                                                        .show();
                                                            }
                                                        });
                                                    }
                                                    else
                                                    {
                                                        //TODO: Write a wrapper class for the documents to pass it along the activities
                                                        startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                                                    }
                                                }
                                            });




                                }
                            });

                }
                else
                {
                    new AlertDialog.Builder(SignupActivity.this)
                            .setTitle("Error")
                            .setMessage(R.string.invalidFieldsError)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    emailInput.setText("");
                                    passwordInput.setText("");
                                }
                            })
                            .create()
                            .show();
                }
            }
        });


        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });
    }
}