package com.elior.guesswhoyouare.RoomFavoritesPackage;

import com.elior.guesswhoyouare.FaceModel;

import java.util.ArrayList;

public interface IFaceDataReceived {

    void onFaceDataReceived(ArrayList<FaceModel> results_);
}
