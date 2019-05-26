package com.elior.guesswhoyouare;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elior.guesswhoyouare.RoomFavoritesPackage.IFaceDataReceived;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceViewModelFavorites;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;

import java.util.ArrayList;
import java.util.List;

public class MeFavorites extends AppCompatActivity implements IFaceDataReceived {

    private FaceViewModelFavorites mFaceViewModelFavorites;
    private RecyclerView recyclerView;
    private FaceListAdapterFavorites adapterFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.face_list);

        try {
            adapterFavorites = new FaceListAdapterFavorites(this);
            recyclerView.setAdapter(adapterFavorites);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ItemDecoration itemDecoration = new ItemDecoration(20);
            recyclerView.addItemDecoration(itemDecoration);
        } catch (Exception e) {

        }

        mFaceViewModelFavorites = ViewModelProviders.of(this).get(FaceViewModelFavorites.class);

        NetWorkDataProviderFavorites dataProvider = new NetWorkDataProviderFavorites();
        dataProvider.getFaceByLocation(this);
    }

    @Override
    public void onFaceDataReceived(ArrayList<FaceModel> results_) {
        // pass data result to adapter
        mFaceViewModelFavorites.getAllFace().observe(this, new Observer<List<FaceFavorites>>() {
            @Override
            public void onChanged(@Nullable final List<FaceFavorites> words) {
                // Update the cached copy of the words in the adapter.
                adapterFavorites.setWords(words);
            }
        });
    }

}
