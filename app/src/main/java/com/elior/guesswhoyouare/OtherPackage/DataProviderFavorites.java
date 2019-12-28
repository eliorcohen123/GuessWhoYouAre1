package com.elior.guesswhoyouare.OtherPackage;

import android.os.AsyncTask;

import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceViewModelFavorites;
import com.elior.guesswhoyouare.RoomFavoritesPackage.IFaceDataReceived;

import java.util.ArrayList;

public class DataProviderFavorites {

    public void getFace(IFaceDataReceived resultListener_) {

        //go get data from google API
        // take time....
        //more time...
        //Data received -> resultListener_

        GetFaceAsyncTask getFaceAsyncTask = new GetFaceAsyncTask(resultListener_);
        getFaceAsyncTask.execute();
    }

    private class GetFaceAsyncTask extends AsyncTask<String, Integer, IFaceDataReceived> {

        private ArrayList<FaceModel> mFaceModels;
        private IFaceDataReceived mIFaceDataReceived;
        private FaceViewModelFavorites faceViewModelFavorites;

        public GetFaceAsyncTask(IFaceDataReceived iFaceDataReceived) {
            mIFaceDataReceived = iFaceDataReceived;
        }

        @Override
        protected IFaceDataReceived doInBackground(String... urls) {
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
            return mIFaceDataReceived;
        }

        @Override
        protected void onPostExecute(IFaceDataReceived iFaceDataReceived_) {
            try {
                iFaceDataReceived_.onFaceDataReceived();
            } catch (Exception e) {
                iFaceDataReceived_.onFaceDataReceived();
            }
        }
    }

}
