package com.elior.guesswhoyouare.RoomFavoritesPackage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FaceDaoFavorites {

    @Insert
    void insert(FaceFavorites face_);

    @Delete
    void deleteNew(FaceFavorites face_);

    @Query("DELETE FROM face_table_favorites")
    void deleteAll();

    @Query("DELETE FROM face_table_favorites WHERE age= :age_")
    void deleteByName(int age_);

    @Query("DELETE FROM face_table_favorites WHERE ID= :id_")
    void deleteByID(Long id_);

    @Query("SELECT * from face_table_favorites ORDER BY age ASC")
    LiveData<List<FaceFavorites>> getAllFace();

    @Update
    void update(FaceFavorites... face_);
}
