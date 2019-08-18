package com.elior.guesswhoyouare.OtherPackage;

import android.os.AsyncTask;

import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;
import com.elior.guesswhoyouare.RoomFavoritesPackage.IFaceDataReceived;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceRepositoryFavorites;

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

        public GetFaceAsyncTask(IFaceDataReceived iFaceDataReceived) {
            mIFaceDataReceived = iFaceDataReceived;
        }

        @Override
        protected IFaceDataReceived doInBackground(String... urls) {
            mFaceModels = new ArrayList<FaceModel>();
            FaceRepositoryFavorites faceRepository = new FaceRepositoryFavorites(NearByApplication.getApplication());
            ArrayList<FaceFavorites> listFace = new ArrayList<>();
            for (FaceModel faceModel : mFaceModels) {
                try {
                    FaceFavorites face = new FaceFavorites(faceModel.getAge(), faceModel.getGender(), faceModel.getAppearance(), faceModel.getImage());
                    listFace.add(face);
                } catch (Exception e) {

                }
            }
            faceRepository.insertFace(listFace);
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
