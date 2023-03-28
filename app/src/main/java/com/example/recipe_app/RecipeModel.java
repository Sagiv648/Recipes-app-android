package com.example.recipe_app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RecipeModel
{

    public RecipeModel()
    {

    }

    private String name;

    private String picture;

    private Integer thumbsUp;
    private Integer thumbsDown;
    private Integer visits;

    private String tags;
    private String content;

    protected RecipeModel(Parcel in) {
        name = in.readString();
        picture = in.readString();
        if (in.readByte() == 0) {
            thumbsUp = null;
        } else {
            thumbsUp = in.readInt();
        }
        if (in.readByte() == 0) {
            thumbsDown = null;
        } else {
            thumbsDown = in.readInt();
        }
        if (in.readByte() == 0) {
            visits = null;
        } else {
            visits = in.readInt();
        }
        tags = in.readString();
        content = in.readString();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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
}
