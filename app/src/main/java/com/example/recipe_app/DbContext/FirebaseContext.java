package com.example.recipe_app.DbContext;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseContext {

    public static class Auth
    {
        private final static FirebaseAuth auth = FirebaseAuth.getInstance();

    }
    public static class Firestore
    {
        private final static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    }
    public static class Storage
    {
        private final static FirebaseStorage storage = FirebaseStorage.getInstance();
    }
    public static class RealtimeDb
    {
        private final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    }
}
