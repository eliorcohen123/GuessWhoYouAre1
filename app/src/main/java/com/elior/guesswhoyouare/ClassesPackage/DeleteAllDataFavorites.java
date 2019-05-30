package com.elior.guesswhoyouare.ClassesPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.elior.guesswhoyouare.OtherPackage.NearByApplication;
import com.elior.guesswhoyouare.R;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceViewModelFavorites;

public class DeleteAllDataFavorites extends AppCompatActivity {

    private Button btnOK, btnCancel;
    private FaceViewModelFavorites faceViewModelFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_all_data_favorites);

        initUI();
        showUI();
    }

    private void initUI() {
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void showUI() {
        // Button are delete all the data of the Favorites
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceViewModelFavorites = new FaceViewModelFavorites(NearByApplication.getApplication());
                faceViewModelFavorites.deleteAll();

                Toast.makeText(DeleteAllDataFavorites.this, "All the data of favorites are deleted!", Toast.LENGTH_LONG).show();

                Intent intentDeleteAllDataToMain = new Intent(DeleteAllDataFavorites.this, MainActivity.class);
                startActivity(intentDeleteAllDataToMain);
            }
        });

        // Button are back to the previous activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
