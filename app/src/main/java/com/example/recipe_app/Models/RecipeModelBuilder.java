package com.example.recipe_app.Models;

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
        public RecipeModelBuilder addUpvotes(Long up)
        {
            recipe.setUpVotes(up);
            return this;
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
        public RecipeModelBuilder addId(String rId)
        {
            recipe.setId(rId);
            return this;
        }
        public RecipeModel Build()
        {
            return recipe;
        }

}
