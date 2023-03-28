package com.example.recipe_app;

import java.io.Serializable;
import java.util.ArrayList;

public class CollectionWrapper<T> implements Serializable {

    private ArrayList<T> collection;


    public ArrayList<T> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<T> collection) {
        this.collection = collection;
    }
}
