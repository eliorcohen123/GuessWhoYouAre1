package com.elior.guesswhoyouare.ClassesPackage;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.elior.guesswhoyouare.OtherPackage.NearByApplication;
import com.elior.guesswhoyouare.R;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceViewModelFavorites;

public class DeleteAllDataFavorites extends AppCompatActivity implements View.OnClickListener {

    private Button btnOK, btnCancel;
    private FaceViewModelFavorites faceViewModelFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_all_data_favorites);

        initUI();
        initListeners();
    }

    private void initUI() {
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);

        faceViewModelFavorites = new FaceViewModelFavorites(NearByApplication.getApplication());
    }

    private void initListeners() {
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                faceViewModelFavorites.deleteAll();

                Toast.makeText(DeleteAllDataFavorites.this, "All the data of favorites are deleted!", Toast.LENGTH_LONG).show();

                Intent intentDeleteAllDataToMain = new Intent(DeleteAllDataFavorites.this, MainActivity.class);
                startActivity(intentDeleteAllDataToMain);
                break;
            case R.id.btnCancel:
                onBackPressed();
                break;
        }
    }

}
