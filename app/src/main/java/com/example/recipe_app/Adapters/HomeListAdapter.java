package com.example.recipe_app.Adapters;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_app.Activities.HomeActivity;
import com.example.recipe_app.Models.RecipeModel;
import com.example.recipe_app.R;
import com.example.recipe_app.Utils.DownloadImageTask;

import java.util.ArrayList;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeListViewHolder> {

    Context context;
    ArrayList<RecipeModel> recipes;



    public HomeListAdapter(Context context, ArrayList<RecipeModel> recipes) {
        this.context = context;
        this.recipes = recipes;

    }

    @NonNull
    @Override
    public HomeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_recycler_view_list_item, parent,false);

        return new HomeListViewHolder(view, recipes);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeListViewHolder holder, int position) {

        RecipeModel recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeTags.setText(recipe.getTags());
        holder.recipeUpvotes.setText(recipe.getUpVotes() + " Upvotes");
        if(recipe.getPicture() != "awaiting upload"){

            new DownloadImageTask(holder.recipeImg)
                    .execute(recipe.getPicture());





        }



    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public  static class HomeListViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{


        ImageView recipeImg;
        TextView recipeName;
        TextView recipeTags;
        TextView recipeUpvotes;
        ArrayList<RecipeModel> allRecipes;
        Context context;
        public HomeListViewHolder(@NonNull View itemView, ArrayList<RecipeModel> allRecipes) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeImg = itemView.findViewById(R.id.recycler_view_list_item_img);
            recipeName = itemView.findViewById(R.id.recycler_view_list_item_name);
            recipeTags = itemView.findViewById(R.id.recycler_view_list_item_tags);
            recipeUpvotes = itemView.findViewById(R.id.recycler_view_list_item_upvotes);
            this.allRecipes = allRecipes;


        }

        @Override
        public void onClick(View v) {
            Integer position = getAdapterPosition();

            if(position >= 0)
            {

                Intent i = new Intent(itemView.getContext(), HomeActivity.class);
                i.putExtra("fragmentNav", 1);
                i.putExtra("pickedRecipe", allRecipes.get(position));

                itemView.getContext().startActivity(i);

            }


        }
    }




}
