package com.elior.guesswhoyouare.ClassesPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.elior.guesswhoyouare.R;

public class DeleteFace extends AppCompatActivity {

    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_face);

        initUI();
        showUI();
    }

    private void initUI() {
        btnOK = findViewById(R.id.btnOK);
    }

    private void showUI() {
        // A button are passes from DeleteFace to Favorites
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
