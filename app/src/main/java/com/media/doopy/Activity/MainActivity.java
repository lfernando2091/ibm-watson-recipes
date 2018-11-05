package com.media.doopy.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ibm.watson.developer_cloud.service.exception.NotFoundException;
import com.ibm.watson.developer_cloud.service.exception.RequestTooLargeException;
import com.ibm.watson.developer_cloud.service.exception.ServiceResponseException;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.media.doopy.Activity.Fragment.MainActivityFragment;
import com.media.doopy.Controller.Component;
import com.media.doopy.CoreData.IBM.Classes;
import com.media.doopy.CoreData.IBM.ClassifiedImagesResult;
import com.media.doopy.CoreData.IBM.Classifiers;
import com.media.doopy.CoreData.IBM.Images;
import com.media.doopy.CoreData.Recipe.Hits;
import com.media.doopy.CoreData.Recipe.RecipeResult;
import com.media.doopy.HttpLibrary.HttpUtils;
import com.media.doopy.Post.ASendDataToIBM;
import com.media.doopy.Post.PostListener;
import com.media.doopy.Post.PostMetod;
import com.media.doopy.R;
import com.media.doopy.Variables.Credentials;
import com.media.doopy.Variables.LocalRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

import static com.media.doopy.Log4J.Logger.print;
import static com.media.doopy.Log4J.Logger.setClass;
import static com.media.doopy.Log4J.Logger.error;

public class MainActivity extends AppCompatActivity implements PostListener{

    private String mCurrentPhotoPath;

    private View mMainFormView;

    private MainActivityFragment fragment;

    private Toolbar toolbar;

    public static String WATSON_DATA_RESULT = "Aguacate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setClass(MainActivity.class);

        mMainFormView = findViewById(R.id.form_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        MostrarSnackAlmacenamiento();
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, LocalRequest.REQUEST_WRITE_PERMISSION);
                    }
                }else
                    dispatchTakePictureIntent();
            }
        });

        fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.content_main);

        if (fragment == null) {
            fragment = new MainActivityFragment();
            fragment.setMain(this);
            getSupportFragmentManager().beginTransaction().add(R.id.content_main, fragment).commit();
        }
    }

    private void MostrarSnackAlmacenamiento(){
        Snackbar.make(mMainFormView, "Es necesario el acceso al disco de almacenamiento.", Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, LocalRequest.REQUEST_WRITE_PERMISSION);
                    }
                }).show();
    }

    private void MostrarSnackInternet(){
        Snackbar.make(mMainFormView, "Es necesario el acceso a internet.", Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, LocalRequest.REQUEST_INTERNET_PERMISSION);
                    }
                }).show();
    }

    private void SnackZeroResult(String msg){
        Snackbar.make(mMainFormView, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getApplicationContext().getPackageName() + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, LocalRequest.REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LocalRequest.REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    dispatchTakePictureIntent();
                else
                    MostrarSnackAlmacenamiento();
                break;
            case LocalRequest.REQUEST_INTERNET_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        SendDataToIBM(mCurrentPhotoPath);
                }
                else
                    MostrarSnackInternet();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LocalRequest.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //Glide.with(this).load(mCurrentPhotoPath).into(mImageView);
                if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.INTERNET)) {
                        SendDataToIBM(mCurrentPhotoPath);
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, LocalRequest.REQUEST_INTERNET_PERMISSION);
                    }
                }else
                    SendDataToIBM(mCurrentPhotoPath);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                onBackPressed();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void SendDataToIBM(String path) {

        try {
            fragment.refreshLayout.setRefreshing(true);
            toolbar.setSubtitle("");
            new ASendDataToIBM(this).execute(path);
        } catch (NotFoundException | RequestTooLargeException e) {
            // Handle Not Found (404) exception
        } catch (ServiceResponseException e) {
            // Base class for all exceptions caused by error responses from the service
            error("Service returned status code " + e.getStatusCode() + ": " + e.getMessage());
        }
    }

    private void TrimIBM(Component classified){
        for (Component i: ((ClassifiedImagesResult)classified).getImages()) {
            for (Component c: ((Images)i).getClassifiers()) {
                SearchRecipe(((Classes)((Classifiers)c).getClasses().get(0)).gettClass());
                //for (Component cl: ((Classifiers)c).getClasses())
            }
        }
    }

    public void SearchRecipe(String question){

        toolbar.setSubtitle(question);
        RequestParams rp = new RequestParams();
        rp.add("q",question);
        WATSON_DATA_RESULT = question;
        rp.add("app_id",Credentials.EDAMAM_APP_ID);
        rp.add("app_key", Credentials.EDAMAM_APP_KEY);
        HttpUtils.post(Credentials.EDAMAM_URL, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Component rr = new RecipeResult().JSONValues(response);
                    if(((RecipeResult)rr).getHits().size() > 0)
                        fragment.setListado(((RecipeResult)rr).getHits());
                    else
                        SnackZeroResult("¡No hay alguna fruta o verdura en la imagen!");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fragment.refreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
    }

    @Override
    public void onConnectionStarts() {

    }

    @Override
    public void onDataReceived(String s) {
        try {
            TrimIBM(new ClassifiedImagesResult().JSONValues(new JSONObject(s)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
