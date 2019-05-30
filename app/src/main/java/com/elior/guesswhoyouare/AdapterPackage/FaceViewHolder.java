package com.elior.guesswhoyouare.AdapterPackage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elior.guesswhoyouare.R;

class FaceViewHolder extends RecyclerView.ViewHolder {

    TextView age1;
    TextView gender1;
    TextView appearance1;
    ImageView image1;
    LinearLayout linearLayout1;

    FaceViewHolder(View itemView) {
        super(itemView);
        age1 = itemView.findViewById(R.id.myAge1);
        gender1 = itemView.findViewById(R.id.myGender1);
        appearance1 = itemView.findViewById(R.id.myAppearance1);
        image1 = itemView.findViewById(R.id.myImage1);
        linearLayout1 = itemView.findViewById(R.id.linearLayout1);
    }

}
