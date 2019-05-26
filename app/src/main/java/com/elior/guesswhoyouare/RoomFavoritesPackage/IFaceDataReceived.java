package com.elior.guesswhoyouare.RoomFavoritesPackage;

import com.elior.guesswhoyouare.OtherPackage.FaceModel;

import java.util.ArrayList;

public interface IFaceDataReceived {

    void onFaceDataReceived(ArrayList<FaceModel> results_);
}
