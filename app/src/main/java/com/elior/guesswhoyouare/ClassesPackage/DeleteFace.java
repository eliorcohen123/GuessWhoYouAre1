package com.elior.guesswhoyouare.ClassesPackage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.elior.guesswhoyouare.R;

public class DeleteFace extends AppCompatActivity implements View.OnClickListener {

    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_face);

        initUI();
        initListeners();
    }

    private void initUI() {
        btnOK = findViewById(R.id.btnOK);
    }

    private void initListeners() {
        btnOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                onBackPressed();
                break;
        }
    }

}
