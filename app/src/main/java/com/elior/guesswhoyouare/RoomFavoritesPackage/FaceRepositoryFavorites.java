package com.elior.guesswhoyouare.RoomFavoritesPackage;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FaceRepositoryFavorites {

    private FaceDaoFavorites mFaceDaoFavorites;
    private LiveData<List<FaceFavorites>> mAllFaceFavorites;

    public FaceRepositoryFavorites(Application application) {
        FaceRoomDatabaseFavorites db = FaceRoomDatabaseFavorites.getDatabase(application);
        mFaceDaoFavorites = db.faceDao();
        mAllFaceFavorites = mFaceDaoFavorites.getAllFace();
    }

    public LiveData<List<FaceFavorites>> getAllFace() {
        return mAllFaceFavorites;
    }

    private static class DeleteLastSearch extends AsyncTask<Void, Void, Void> {

        private FaceDaoFavorites faceDaoFavorites;

        private DeleteLastSearch(FaceDaoFavorites dao) {
            faceDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            faceDaoFavorites.deleteAll();
            return null;
        }
    }

    void deleteAllFace() {
        DeleteLastSearch deleteLastSearch = new DeleteLastSearch(mFaceDaoFavorites);
        deleteLastSearch.execute();
    }

    private static class updateFaceAsyncTask extends AsyncTask<FaceFavorites, Void, Void> {

        private FaceDaoFavorites faceDaoFavorites;

        private updateFaceAsyncTask(FaceDaoFavorites dao) {
            faceDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(final FaceFavorites... params) {
            faceDaoFavorites.update(params[0]);
            return null;
        }
    }

    void updateFace(FaceFavorites face) {
        new updateFaceAsyncTask(mFaceDaoFavorites).execute(face);
    }

    private static class deleteFaceAsyncTask extends AsyncTask<FaceFavorites, Void, Void> {

        private FaceDaoFavorites faceDaoFavorites;

        private deleteFaceAsyncTask(FaceDaoFavorites dao) {
            faceDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(final FaceFavorites... params) {
            faceDaoFavorites.deleteNew(params[0]);
            return null;
        }
    }

    void deleteFace(FaceFavorites face) {
        new deleteFaceAsyncTask(mFaceDaoFavorites).execute(face);
    }

    private static class insertFaceAsyncTask extends AsyncTask<FaceFavorites, Void, Void> {

        private FaceDaoFavorites faceDaoFavorites;

        private insertFaceAsyncTask(FaceDaoFavorites dao) {
            faceDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(final FaceFavorites... params) {
            faceDaoFavorites.insert(params[0]);
            return null;
        }
    }

    public void insertFace(FaceFavorites face) {
        new insertFaceAsyncTask(mFaceDaoFavorites).execute(face);
    }

    public void insertFace(List<FaceFavorites> faceList_) {
        for (FaceFavorites p : faceList_) {
            insertFace(p);
        }
    }

}
