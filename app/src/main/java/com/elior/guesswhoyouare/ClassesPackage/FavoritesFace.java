package com.elior.guesswhoyouare.ClassesPackage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.elior.guesswhoyouare.AdapterPackage.FaceListAdapterFavorites;
import com.elior.guesswhoyouare.OtherPackage.FaceModel;
import com.elior.guesswhoyouare.OtherPackage.ItemDecoration;
import com.elior.guesswhoyouare.OtherPackage.NetWorkDataProviderFavorites;
import com.elior.guesswhoyouare.R;
import com.elior.guesswhoyouare.RoomFavoritesPackage.IFaceDataReceived;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceViewModelFavorites;
import com.elior.guesswhoyouare.RoomFavoritesPackage.FaceFavorites;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFace extends AppCompatActivity implements IFaceDataReceived, NavigationView.OnNavigationItemSelectedListener {

    private FaceViewModelFavorites mFaceViewModelFavorites;
    private RecyclerView recyclerView;
    private FaceListAdapterFavorites adapterFavorites;
    private Paint p = new Paint();
    private DrawerLayout drawer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private NetWorkDataProviderFavorites netWorkDataProviderFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        initUI();
        showUI();
        myRecyclerView();
        getData();
        enableSwipe();
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        swipeRefreshLayout = findViewById(R.id.swipe_containerFrag);
        recyclerView = findViewById(R.id.face_list);

        netWorkDataProviderFavorites = new NetWorkDataProviderFavorites();
    }

    private void showUI() {
        (this).setSupportActionBar(toolbar);

        findViewById(R.id.myButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else
                    drawer.openDrawer(GravityCompat.END);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Vibration for 0.1 second
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(100);
                }

                finish();
                startActivity(getIntent());  // Refresh activity

                Toast toast = Toast.makeText(FavoritesFace.this, "The list are refreshed!", Toast.LENGTH_SHORT);
                View view = toast.getView();
                view.getBackground().setColorFilter(getResources().getColor(R.color.colorLightBlue), PorterDuff.Mode.SRC_IN);
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.colorDarkBrown));
                toast.show();  // Toast

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void myRecyclerView() {
        try {
            adapterFavorites = new FaceListAdapterFavorites(this);
            recyclerView.setAdapter(adapterFavorites);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ItemDecoration itemDecoration = new ItemDecoration(20);
            recyclerView.addItemDecoration(itemDecoration);
        } catch (Exception e) {

        }

        mFaceViewModelFavorites = ViewModelProviders.of(this).get(FaceViewModelFavorites.class);
    }

    private void getData() {
        netWorkDataProviderFavorites.getFaceByLocation(this);
    }

    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                FaceFavorites current = adapterFavorites.getFaceAtPosition(position);

                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(FavoritesFace.this, "Deleting...", Toast.LENGTH_LONG).show();
                    mFaceViewModelFavorites.deleteFace(current);

                    Intent intentDeleteData = new Intent(FavoritesFace.this, DeleteFace.class);
                    startActivity(intentDeleteData);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.deletedicon);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.intentMainActivity) {
            Intent intentBackMainActivity = new Intent(FavoritesFace.this, MainActivity.class);
            startActivity(intentBackMainActivity);
        } else if (id == R.id.deleteAllDataFavorites) {
            Intent intentDeleteAllData = new Intent(FavoritesFace.this, DeleteAllDataFavorites.class);
            startActivity(intentDeleteAllData);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
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
