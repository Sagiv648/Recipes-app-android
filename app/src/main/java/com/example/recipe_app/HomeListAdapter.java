package com.example.recipe_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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

        return new HomeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeListViewHolder holder, int position) {

        RecipeModel recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeTags.setText(recipe.getTags());
        if(recipe.getPicture() != "awaiting upload"){

            new DownloadImageTask(holder.recipeImg)
                    .execute(recipe.getPicture());



            //holder.recipeImg.setImageURI(Uri.parse(recipe.getPicture()));

        }



    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public  static class HomeListViewHolder extends RecyclerView.ViewHolder{


        ImageView recipeImg;
        TextView recipeName;
        TextView recipeTags;

        public HomeListViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeImg = itemView.findViewById(R.id.recycler_view_list_item_img);
            recipeName = itemView.findViewById(R.id.recycler_view_list_item_name);
            recipeTags = itemView.findViewById(R.id.recycler_view_list_item_tags);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
