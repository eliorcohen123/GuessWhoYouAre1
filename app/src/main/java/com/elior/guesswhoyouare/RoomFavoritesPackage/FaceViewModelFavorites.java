package com.elior.guesswhoyouare.RoomFavoritesPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class FaceViewModelFavorites extends AndroidViewModel {

    private FaceRepositoryFavorites faceRepositoryFavorites;
    private LiveData<List<FaceFavorites>> mAllFaceFavorites;

    public FaceViewModelFavorites(Application application) {
        super(application);
        faceRepositoryFavorites = new FaceRepositoryFavorites(application);
        mAllFaceFavorites = faceRepositoryFavorites.getAllFace();
    }

    public LiveData<List<FaceFavorites>> getAllFace() {
        return mAllFaceFavorites;
    }

    public void insert(FaceFavorites face) {
        faceRepositoryFavorites.insert(face);
    }

    public void deleteAll() {
        faceRepositoryFavorites.deleteLastSearch();
    }

    public void deleteFace(FaceFavorites face) {
        faceRepositoryFavorites.deleteWord(face);
    }

    public void updateFace(FaceFavorites face) {
        faceRepositoryFavorites.updateWord(face);
    }

}
