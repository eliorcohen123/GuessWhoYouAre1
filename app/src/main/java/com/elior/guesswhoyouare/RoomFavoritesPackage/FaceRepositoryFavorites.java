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

        DeleteLastSearch(FaceDaoFavorites dao) {
            faceDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            faceDaoFavorites.deleteAll();
            return null;
        }
    }

    void deleteLastSearch() {
        DeleteLastSearch deleteLastSearch = new DeleteLastSearch(mFaceDaoFavorites);
        deleteLastSearch.execute();
    }

    private static class updateWordAsyncTask extends AsyncTask<FaceFavorites, Void, Void> {

        private FaceDaoFavorites faceDaoFavorites;

        updateWordAsyncTask(FaceDaoFavorites dao) {
            faceDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(final FaceFavorites... params) {
            faceDaoFavorites.update(params[0]);
            return null;
        }
    }

    void updateWord(FaceFavorites face) {
        new updateWordAsyncTask(mFaceDaoFavorites).execute(face);
    }

    private static class deleteWordAsyncTask extends AsyncTask<FaceFavorites, Void, Void> {

        private FaceDaoFavorites faceDaoFavorites;

        deleteWordAsyncTask(FaceDaoFavorites dao) {
            faceDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(final FaceFavorites... params) {
            faceDaoFavorites.deleteNew(params[0]);
            return null;
        }
    }

    void deleteWord(FaceFavorites face) {
        new deleteWordAsyncTask(mFaceDaoFavorites).execute(face);
    }

    private static class insertAsyncTask extends AsyncTask<FaceFavorites, Void, Void> {

        private FaceDaoFavorites faceDaoFavorites;

        insertAsyncTask(FaceDaoFavorites dao) {
            faceDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(final FaceFavorites... params) {
            faceDaoFavorites.insert(params[0]);
            return null;
        }
    }

    public void insert(FaceFavorites face) {
        new insertAsyncTask(mFaceDaoFavorites).execute(face);
    }

    public void insert(List<FaceFavorites> faceList_) {
        for (FaceFavorites p : faceList_) {
            insert(p);
        }
    }

}
