package com.example.recipe_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RecipeModel implements Serializable
{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private ArrayList<String> pictures;
    private Date createdAt;
    private Integer thumbsUp;
    private Integer thumbsDown;
    private Integer visits;
    private ArrayList<String> tags;
    private String content;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public ArrayList<String> getTags()
    {
        return tags;
    }
    public ArrayList<String> getPictures()
    {
        return pictures;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public Integer getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(Integer thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public class Builder{
        RecipeModel recipe;

        public Builder addPictures(ArrayList<String> pics)
        {
            if(recipe.pictures == null)
                recipe.pictures = new ArrayList<>();
            recipe.pictures.addAll(pics);
            return this;
        }
        public Builder addThumbsUp(Integer tUp)
        {
            thumbsUp = tUp;
            return this;
        }
        public Builder addThumbsDown(Integer tDown)
        {
            thumbsDown = tDown;
            return this;
        }
        public Builder addVisits(Integer rVisits)
        {
            visits = rVisits;
            return  this;
        }
        public Builder addTags (ArrayList<String> rTags)
        {
            if(tags == null)
            tags = new ArrayList<>();
            tags.addAll(rTags);
            return this;
        }
        public Builder addName(String rName)
        {
            name = rName;
            return this;
        }
        public Builder addContent(String rContent)
        {
            content = rContent;
            return this;
        }
        public RecipeModel Build()
        {
            return recipe;
        }
    }
}
