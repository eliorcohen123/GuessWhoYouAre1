package com.elior.guesswhoyouare.AdapterPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.elior.guesswhoyouare.R;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;

import java.util.List;

public class FaceListAdapterFavorites extends RecyclerView.Adapter<FaceViewHolder> {

    private final LayoutInflater mInflater;
    private List<FaceFavorites> mFaceList;

    public FaceListAdapterFavorites(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public FaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_favorites, parent, false);
        return new FaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FaceViewHolder holder, final int position) {
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

            setFadeAnimation(holder.itemView);
        } else {
            // Covers the case of data not being ready yet.
            holder.age1.setText("No FaceSearch");
        }
    }

    public void setFaces(List<FaceFavorites> faceFavorites) {
        mFaceList = faceFavorites;
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

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        view.startAnimation(anim);
    }

}
