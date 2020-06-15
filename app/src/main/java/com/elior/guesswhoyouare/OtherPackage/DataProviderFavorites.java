package com.elior.guesswhoyouare.OtherPackage;

import android.os.AsyncTask;

import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceViewModelFavorites;

import java.util.ArrayList;

public class DataProviderFavorites {

    public void getFace() {

        //go get data from google API
        // take time....
        //more time...
        //Data received -> resultListener_

        GetFaceAsyncTask getFaceAsyncTask = new GetFaceAsyncTask();
        getFaceAsyncTask.execute();
    }

    private static class GetFaceAsyncTask extends AsyncTask<String, Integer, ArrayList<FaceModel>> {

        private ArrayList<FaceModel> mFaceModels;
        private FaceViewModelFavorites faceViewModelFavorites;

        public GetFaceAsyncTask() {

        }

        @Override
        protected ArrayList<FaceModel> doInBackground(String... urls) {
            mFaceModels = new ArrayList<FaceModel>();
            faceViewModelFavorites = new FaceViewModelFavorites(NearByApplication.getApplication());
            ArrayList<FaceFavorites> listFace = new ArrayList<>();
            for (FaceModel faceModel : mFaceModels) {
                try {
                    FaceFavorites face = new FaceFavorites(faceModel.getAge(), faceModel.getGender(), faceModel.getAppearance(), faceModel.getImage());
                    listFace.add(face);
                } catch (Exception e) {

                }
            }
            faceViewModelFavorites.insert(listFace);
            return mFaceModels;
        }

        @Override
        protected void onPostExecute(ArrayList<FaceModel> arrayList) {

        }
    }

}
