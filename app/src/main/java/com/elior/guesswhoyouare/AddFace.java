package com.elior.guesswhoyouare;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceViewModelFavorites;

import java.io.ByteArrayOutputStream;

public class AddFace extends AppCompatActivity {

    private FaceViewModelFavorites faceViewModelFavorites;
    private TextView age, gender, appearance;
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_face);

        Bundle extras = getIntent().getExtras();
        String age3 = extras.getString("age");
        String gender3 = extras.getString("gender");
        String appearance3 = extras.getString("appearance");

        age = findViewById(R.id.age2);  // ID of the age
        gender = findViewById(R.id.gender2);  // ID of the gender
        appearance = findViewById(R.id.appearance2);  // ID of the appearance
        imageView1 = findViewById(R.id.imageViewMe2);  // ID of the image

        Bitmap b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
        imageView1.setImageBitmap(b);

        age.setText(age3);
        if (gender3.equals(getString(R.string.masculine))) {
            gender.setText(getString(R.string.man));
        } else if (gender3.equals(getString(R.string.feminine))) {
            gender.setText(R.string.woman);
        }
        appearance.setText(appearance3);

        // Button that does the following:
        TextView textView1 = findViewById(R.id.textViewOK);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age1 = Integer.parseInt(age.getText().toString());  // GetText of the name
                String gender1 = gender.getText().toString();  // GetText of the address
                String appearance1 = appearance.getText().toString();  // GetText of the lat

                Bitmap bitmap = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] image1 = baos.toByteArray();

                FaceFavorites faceFavorites = new FaceFavorites(age1, gender1, appearance1, image1);
                faceViewModelFavorites = ViewModelProviders.of(AddFace.this).get(FaceViewModelFavorites.class);
                faceViewModelFavorites.insert(faceFavorites);

                // Pass from AddMapFromInternet to ActivityFavorites
                Intent intentAddInternetToMain = new Intent(AddFace.this, FavoritesFace.class);
                startActivity(intentAddInternetToMain);
            }
        });

        // Button are back to the previous activity
        Button button3 = findViewById(R.id.btnBack);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
