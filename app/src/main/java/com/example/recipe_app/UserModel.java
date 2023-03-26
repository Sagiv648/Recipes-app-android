package com.example.recipe_app;

import com.google.firebase.firestore.auth.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class UserModel implements Serializable
{
    private String uuid;
    private String email;
    private ArrayList<String> uploaded_recipes;

    public String getUuid()
    {
        return uuid;
    }
    public String getEmail()
    {
        return email;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getUploaded_recipes() {
        return uploaded_recipes;
    }

    public void setUploaded_recipes(ArrayList<String> uploaded_recipes) {
        this.uploaded_recipes = uploaded_recipes;
    }


}
