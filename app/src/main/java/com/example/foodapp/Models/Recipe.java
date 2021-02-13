package com.example.foodapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Recipe implements Parcelable {

    @SerializedName("id")
    private String receipe_id = null;
    private String title ;
    private String readyInMinutes ;
    private String image ;
    private String sourceName ;
    private Float spoonacularScore ;
    private String instructions ;

    public Recipe() {
    }

    public Recipe(String receipe_id, String title, String readyInMinutes, String image,
                   String sourceName, Float spoonacularScore, String instructions) {
        this.receipe_id = receipe_id;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.image = image;
        this.sourceName = sourceName;
        this.spoonacularScore = spoonacularScore;
        this.instructions = instructions;
    }

    protected Recipe(Parcel in) {
        receipe_id = in.readString();
        title = in.readString();
        readyInMinutes = in.readString();
        image = in.readString();
        sourceName = in.readString();
        if (in.readByte() == 0) {
            spoonacularScore = null;
        } else {
            spoonacularScore = in.readFloat();
        }
        instructions = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getReceipe_id() {
        return receipe_id;
    }

    public void setReceipe_id(String receipe_id) {
        this.receipe_id = receipe_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(String readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Float getSpoonacularScore() {
        return spoonacularScore;
    }

    public void setSpoonacularScore(Float spoonacularScore) {
        this.spoonacularScore = spoonacularScore;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "receipe_id='" + receipe_id + '\'' +
                ", title='" + title + '\'' +
                ", readyInMinutes='" + readyInMinutes + '\'' +
                ", image='" + image + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", spoonacularScore=" + spoonacularScore +
                ", instructions='" + instructions + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(receipe_id);
        dest.writeString(title);
        dest.writeString(readyInMinutes);
        dest.writeString(image);
        dest.writeString(sourceName);
        if (spoonacularScore == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(spoonacularScore);
        }
        dest.writeString(instructions);
    }
}
