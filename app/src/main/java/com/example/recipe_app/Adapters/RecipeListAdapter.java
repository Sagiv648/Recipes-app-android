package com.example.recipe_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_app.Models.RecipeModel;
import com.example.recipe_app.R;
import com.example.recipe_app.Utils.DownloadImageTask;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {


    private Context context;
    private ArrayList<RecipeModel> recipes;

    public RecipeListAdapter(Context context, ArrayList<RecipeModel> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_recipes_recycler_view_list_item,parent,false);

        return new RecipeListViewHolder(view,recipes);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder holder, int position) {
        RecipeModel recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeTags.setText(recipe.getTags());
        holder.recipeUpvotes.setText(recipe.getUpVotes() + " upvotes");
        if(recipe.getPicture() != "awaiting upload")
        {
            new DownloadImageTask(holder.recipeImg).execute(recipe.getPicture());
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeListViewHolder extends RecyclerView.ViewHolder
    {

        ImageView recipeImg;
        TextView recipeName;
        TextView recipeTags;
        TextView recipeUpvotes;
        Button deleteBtn;
        ArrayList<RecipeModel> allRecipes;
        public RecipeListViewHolder(@NonNull View itemView, ArrayList<RecipeModel> allRecipes) {
            super(itemView);
            recipeImg = itemView.findViewById(R.id.my_recipes_recycler_view_list_item_img);
            recipeName = itemView.findViewById(R.id.my_recipe_name);
            recipeTags = itemView.findViewById(R.id.my_recipe_tags);
            recipeUpvotes = itemView.findViewById(R.id.my_recipe_upvote_count);
            deleteBtn = itemView.findViewById(R.id.delete_recipe_btn);
            this.allRecipes = allRecipes;
        }
    }
}
