package com.elior.guesswhoyouare.ClassesPackage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elior.guesswhoyouare.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Region;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, NavigationView.OnNavigationItemSelectedListener {

    private TextView myGender, myAge, myAppearance;
    private Button btnOpenExtCam, start, capture, stop, btnSave;
    private ImageView myImage;
    private static final int CAMERA_REQUEST = 1888, MY_CAMERA_PERMISSION_CODE = 100, CAMERA_REQUEST_CODE = 200;
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera.PictureCallback rawCallback, jpegCallback;
    private Camera.ShutterCallback shutterCallback;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        permissions();
        drawerLayout();
        camera();
        ext_camera();
    }

    // Initialize variables
    private void initUI() {
        // drawerLayout
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // TextView
        myGender = findViewById(R.id.myGender);
        myAge = findViewById(R.id.myAge);
        myAppearance = findViewById(R.id.myAppearance);
        // Button
        start = findViewById(R.id.btnStart);
        capture = findViewById(R.id.btnCapture);
        stop = findViewById(R.id.btnStop);
        btnOpenExtCam = findViewById(R.id.btnGallery);
        btnSave = findViewById(R.id.btnSave);
        // ImageView
        myImage = findViewById(R.id.myImage);
        // SurfaceView
        surfaceView = findViewById(R.id.surface_camera);
        // AppRater
        AppRater.app_launched(this);
    }

    private void permissions() {
        // Get permission for the camera
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

        // Detect methods whose names start with penalty and solve the crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void drawerLayout() {
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.favorites) {
            Intent intentFavorite = new Intent(this, FavoritesFace.class);
            startActivity(intentFavorite);
        } else if (id == R.id.shareIntentApp) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.elior.guesswhoyouare");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.exit) {
            ActivityCompat.finishAffinity(this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private void camera() {
        start.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                start_camera();
            }
        });

        stop.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                stop_camera();
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                captureImage();
            }
        });

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        rawCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
            }
        };

        // Handles data for jpeg picture
        shutterCallback = new Camera.ShutterCallback() {
            public void onShutter() {
            }
        };

        jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                final ByteArrayOutputStream blob = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);

                File file = new File(getCacheDir(), getString(R.string.child_file));
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // convert File to byte[]
                final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(bos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Objects.requireNonNull(oos).writeObject(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Objects.requireNonNull(oos).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data = bos.toByteArray();

                // convert byte[] to File
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(bis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File fileFromBytes;
                try {
                    fileFromBytes = (File) Objects.requireNonNull(ois).readObject();

                    ClarifaiClient client = new ClarifaiBuilder(getString(R.string.API_KEY))
                            .buildSync();

                    // Get response from Bitmap
                    ClarifaiResponse<List<ClarifaiOutput<Region>>> response =
                            client.getDefaultModels().demographicsModel().predict()
                                    .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(fileFromBytes)))
                                    .executeSync();

                    try {
                        final Region region = response.get().get(0).data().get(0);

                        // Get Gender
                        if (Objects.requireNonNull(region.genderAppearances().get(0).name()).equals(getString(R.string.masculine))) {
                            myGender.setText(getString(R.string.man));
                        } else if (Objects.requireNonNull(region.genderAppearances().get(0).name()).equals(getString(R.string.feminine))) {
                            myGender.setText(getString(R.string.woman));
                        }

                        // Get Age
                        myAge.setText(region.ageAppearances().get(0).name());

                        // Get Appearance
                        myAppearance.setText(region.multiculturalAppearances().get(0).name());

                        // Put Image
                        myImage.setImageBitmap(bitmap);

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            myImage.setRotation(90);
                        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            myImage.setRotation(180);
                        }

                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(MainActivity.this, AddFace.class);
                                i.putExtra("byteArray", blob.toByteArray());
                                i.putExtra("age", region.ageAppearances().get(0).name());
                                i.putExtra("gender", region.genderAppearances().get(0).name());
                                i.putExtra("appearance", region.multiculturalAppearances().get(0).name());
                                startActivity(i);
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, getString(R.string.fail_picture), Toast.LENGTH_LONG).show();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Objects.requireNonNull(ois).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    // Take picture from the internal camera
    private void captureImage() {
        // TODO Auto-generated method stub
        try {
            camera.takePicture(shutterCallback, rawCallback, jpegCallback);
        } catch (Exception e) {

        }
    }

    // Open internal camera
    private void start_camera() {
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        // modify parameter
        param.setPreviewFrameRate(20);
        param.setPreviewSize(176, 144);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            return;
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            camera.setDisplayOrientation(90);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(180);
        }
    }

    // Stops taking pictures on the internal camera
    private void stop_camera() {
        try {
            camera.stopPreview();
            camera.release();
        } catch (Exception e) {

        }
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    // Open external camera + option to ext_camera
    private void ext_camera() {
        btnOpenExtCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //create a file to write bitmap data
            File f = new File(getCacheDir(), getString(R.string.child_file));
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Convert bitmap to byte array
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Objects.requireNonNull(photo).compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapData = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(fos).write(bitmapData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(fos).flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(fos).close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ClarifaiClient client = new ClarifaiBuilder(getString(R.string.API_KEY))
                    .buildSync();

            // Get response from Bitmap
            ClarifaiResponse<List<ClarifaiOutput<Region>>> response =
                    client.getDefaultModels().demographicsModel().predict()
                            .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(bitmapData)))
                            .executeSync();

            try {
                final Region region = response.get().get(0).data().get(0);

                // Get Gender
                if (Objects.requireNonNull(region.genderAppearances().get(0).name()).equals(getString(R.string.masculine))) {
                    myGender.setText(getString(R.string.man));
                } else if (Objects.requireNonNull(region.genderAppearances().get(0).name()).equals(getString(R.string.feminine))) {
                    myGender.setText(getString(R.string.woman));
                }

                // Get Appearance
                myAppearance.setText(region.multiculturalAppearances().get(0).name());

                // Get Age
                myAge.setText(region.ageAppearances().get(0).name());

                // Put Image
                myImage.setImageBitmap(photo);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, AddFace.class);
                        i.putExtra("byteArray", bos.toByteArray());
                        i.putExtra("age", region.ageAppearances().get(0).name());
                        i.putExtra("gender", region.genderAppearances().get(0).name());
                        i.putExtra("appearance", region.multiculturalAppearances().get(0).name());
                        startActivity(i);
                    }
                });
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, getString(R.string.fail_picture), Toast.LENGTH_LONG).show();
            }
        }
    }

}
