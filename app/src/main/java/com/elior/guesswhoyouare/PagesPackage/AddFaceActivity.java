package com.elior.guesswhoyouare.PagesPackage;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elior.guesswhoyouare.R;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceViewModelFavorites;

public class AddFaceActivity extends AppCompatActivity implements View.OnClickListener {

    private FaceViewModelFavorites faceViewModelFavorites;
    private TextView age, gender, appearance, textViewOK;
    private ImageView imageView1;
    private Button btnBack;
    private String age3, gender3, appearance3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_face);

        initUI();
        initListeners();
        showUI();
    }

    private void initUI() {
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        age3 = extras.getString("age");
        gender3 = extras.getString("gender");
        appearance3 = extras.getString("appearance");

        age = findViewById(R.id.age2);  // ID of the age
        gender = findViewById(R.id.gender2);  // ID of the gender
        appearance = findViewById(R.id.appearance2);  // ID of the appearance
        imageView1 = findViewById(R.id.imageViewMe2);  // ID of the image

        textViewOK = findViewById(R.id.textViewOK);

        btnBack = findViewById(R.id.btnBack);

        faceViewModelFavorites = ViewModelProviders.of(AddFaceActivity.this).get(FaceViewModelFavorites.class);
    }

    private void initListeners() {
        textViewOK.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void showUI() {
        Bitmap b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
        imageView1.setImageBitmap(b);

        age.setText(age3);
        if (gender3.equals(getString(R.string.masculine))) {
            gender.setText(getString(R.string.man));
        } else if (gender3.equals(getString(R.string.feminine))) {
            gender.setText(R.string.woman);
        }
        appearance.setText(appearance3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewOK:
                int age1 = Integer.parseInt(age.getText().toString());  // GetText of the name
                String gender1 = gender.getText().toString();  // GetText of the address
                String appearance1 = appearance.getText().toString();  // GetText of the lat

                FaceFavorites faceFavorites = new FaceFavorites(age1, gender1, appearance1, getIntent().getByteArrayExtra("byteArray"));
                faceViewModelFavorites.insert(faceFavorites);

                // Pass from AddFace to ActivityFavorites
                Intent intentAddInternetToMain = new Intent(AddFaceActivity.this, FavoritesFaceActivity.class);
                startActivity(intentAddInternetToMain);
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

}
