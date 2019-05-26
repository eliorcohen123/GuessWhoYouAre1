package com.elior.guesswhoyouare.AdapterPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elior.guesswhoyouare.R;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;

import java.util.List;

public class FaceListAdapterFavorites extends RecyclerView.Adapter<FaceListAdapterFavorites.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {

        private TextView age1, gender1, appearance1;
        private ImageView image1;
        private LinearLayout linearLayout1;

        private WordViewHolder(View itemView) {
            super(itemView);
            age1 = itemView.findViewById(R.id.myAge1);
            gender1 = itemView.findViewById(R.id.myGender1);
            appearance1 = itemView.findViewById(R.id.myAppearance1);
            image1 = itemView.findViewById(R.id.myImage1);
            linearLayout1 = itemView.findViewById(R.id.linearLayout1);
        }
    }

    private final LayoutInflater mInflater;
    private List<FaceFavorites> mFaceList; // Cached copy of words

    public FaceListAdapterFavorites(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_favorites, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, final int position) {
        if (mFaceList != null) {
            final FaceFavorites current = mFaceList.get(position);
            try {
                holder.age1.setText(String.valueOf(current.getAge()));
                holder.gender1.setText(current.getGender());
                holder.appearance1.setText(current.getAppearance());

                Bitmap bmp = BitmapFactory.decodeByteArray(current.getImage(), 0, current.getImage().length);
                holder.image1.setImageBitmap(bmp);
            } catch (Exception e) {

            }
            holder.linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.age1.setText("No FaceSearch");
        }
    }

    public void setWords(List<FaceFavorites> words) {
        mFaceList = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mFaceList != null)
            return mFaceList.size();
        else return 0;
    }

    public FaceFavorites getFaceAtPosition(int position) {
        return mFaceList.get(position);
    }

}
