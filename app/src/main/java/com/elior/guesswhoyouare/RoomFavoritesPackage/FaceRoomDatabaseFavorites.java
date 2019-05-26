package com.elior.guesswhoyouare.RoomFavoritesPackage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FaceFavorites.class}, version = 1, exportSchema = false)
public abstract class FaceRoomDatabaseFavorites extends RoomDatabase {

    public abstract FaceDaoFavorites faceDao();

    private static volatile FaceRoomDatabaseFavorites INSTANCE;

    public static FaceRoomDatabaseFavorites getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FaceRoomDatabaseFavorites.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FaceRoomDatabaseFavorites.class, "face_database_favorites")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
