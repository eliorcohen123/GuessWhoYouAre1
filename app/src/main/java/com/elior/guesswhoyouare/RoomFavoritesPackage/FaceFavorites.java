package com.elior.guesswhoyouare.RoomFavoritesPackage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "face_table_favorites")
public class FaceFavorites implements Serializable {

    public FaceFavorites(int age, @NonNull String gender, @NonNull String appearance, @NonNull byte[] image) {
        this.age = age;
        this.gender = gender;
        this.appearance = appearance;
        this.image = image;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    private long ID;

    @NonNull
    @ColumnInfo(name = "age")
    private int age;

    @NonNull
    @ColumnInfo(name = "gender")
    private String gender;

    @NonNull
    @ColumnInfo(name = "appearance")
    private String appearance;

    @NonNull
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public FaceFavorites() {

    }


    @NonNull
    public long getID() {
        return ID;
    }

    public void setID(@NonNull long ID) {
        this.ID = ID;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NonNull
    public String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }

    @NonNull
    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(@NonNull String appearance) {
        this.appearance = appearance;
    }

    @NonNull
    public byte[] getImage() {
        return image;
    }

    public void setImage(@NonNull byte[] image) {
        this.image = image;
    }

}
