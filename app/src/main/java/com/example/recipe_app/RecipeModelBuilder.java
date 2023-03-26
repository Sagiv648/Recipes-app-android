package com.example.recipe_app;

import java.util.ArrayList;

public class RecipeModelBuilder {


        RecipeModel recipe;

    public RecipeModelBuilder() {
        this.recipe = new RecipeModel();
    }

    public RecipeModelBuilder addPictures(String pic)
        {
            recipe.setPicture(pic);
            return this;
        }
        public RecipeModelBuilder addThumbsUp(Integer tUp)
        {
            recipe.setThumbsUp(tUp);
            return this;
        }
        public RecipeModelBuilder addThumbsDown(Integer tDown)
        {
            recipe.setThumbsDown(tDown);
            return this;
        }
        public RecipeModelBuilder addVisits(Integer rVisits)
        {
            recipe.setVisits(rVisits);
            return  this;
        }
        public RecipeModelBuilder addTags (String rTags)
        {
            recipe.setTags(rTags);
            return this;
        }
        public RecipeModelBuilder addName(String rName)
        {
            recipe.setName(rName);
            return this;
        }
        public RecipeModelBuilder addContent(String rContent)
        {
            recipe.setContent(rContent);
            return this;
        }
        public RecipeModel Build()
        {
            return recipe;
        }

}
