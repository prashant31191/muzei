package com.clickygame;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.clickygame.api.APIService;
import com.clickygame.api.EarthviewDetailModel;
import com.clickygame.utils.ApiUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prashant.patel on 7/18/2017.
 */

public class ActWPList extends AppCompatActivity
{
    DisplayMetrics metrics;
    WallpaperManager wallpaperManager;
    int height = 1920;
    int width = 1080;

    private APIService mAPIService;
    FloatingActionButton fab;
    FloatingActionButton fabSetWallpaper;
    ImageView ivPhoto;
    Bitmap bitmapWallpaper = null;

    String TAG = "===ActWPList==";
    String strPath = "istanbul-turkey-1888.json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wplist);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabSetWallpaper = (FloatingActionButton) findViewById(R.id.fabSetWallpaper);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intActInsetDatabase = new Intent(ActWPList.this,ActInsertDatabase.class);
                //Intent intActInsetDatabase = new Intent(ActWPList.this,GridViewExampleActivity.class);
                startActivity(intActInsetDatabase);
                return false;
            }
        });

        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"===image click==sendPost==");
                sendPost();
            }
        });

        fabSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"===fabSetWallpaper click==sendPost==");
                if(bitmapWallpaper !=null)
                {

                    try {

                        setSaveImageWallpaper();

                       /* Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setDataAndType(uri, "image/jpeg");
                        intent.putExtra("mimeType", "image/jpeg");
                        this.startActivity(Intent.createChooser(intent, "Set as:"));

                        //direct set wallpaper
                        wallpaperManager.setBitmap(bitmapWallpaper);
                        wallpaperManager.suggestDesiredDimensions(width, height);

                        Toast.makeText(ActWPList.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //https://earthview.withgoogle.com/dubai-united-arab-emirates-1779
        mAPIService = ApiUtils.getAPIService();
        sendPost();

        wallpaperManager = WallpaperManager.getInstance(ActWPList.this);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // get the height and width of screen
        height = metrics.heightPixels;
        width = metrics.widthPixels;




    }

    private void setSaveImageWallpaper()
    {
    try{
        File filename = new File("/sdcard/wallpaper.jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bitmapWallpaper.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {

                    Uri uri = Uri.fromFile(new File("/sdcard/wallpaper.jpg"));


                    Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(uri, "image/*");
                    intent.putExtra("mimeType", "image/*");
                    this.startActivity(Intent.createChooser(intent, "Set as:"));

                    /*//direct set wallpaper
                    wallpaperManager.setBitmap(bitmapWallpaper);
                    wallpaperManager.suggestDesiredDimensions(width, height);
*/
                    Toast.makeText(ActWPList.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

    }


    EarthviewDetailModel earthviewDetailModel = null;

    public void sendPost() {


        mAPIService.getEarthviewDetail(strPath).enqueue(new Callback<EarthviewDetailModel>() {
            @Override
            public void onResponse(Call<EarthviewDetailModel> call, Response<EarthviewDetailModel> response) {


                if(response.isSuccessful()) {

                    //Log.i(TAG, "post submitted to API." + response.body().toString());

                    if(response !=null && response.body() !=null)
                    {

                        earthviewDetailModel = response.body();
                        showResponse();
                        Log.w(TAG,"=-Short-RESPONSE="+new Gson().toJson(response));
                     //   Log.e(TAG, "--Full--RESPONSE-->"+new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<EarthviewDetailModel> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void showResponse() {
        if(earthviewDetailModel !=null)
        {

            if(earthviewDetailModel.nextApi !=null) {
                Log.i(TAG, "===Next Page nextApi==" + earthviewDetailModel.nextApi);

                Log.i(TAG, "===Next Page url==" + earthviewDetailModel.nextApi);

                strPath = earthviewDetailModel.nextApi.replace("/_api/","") ;
                Log.i(TAG, "===Next Page strPath==" + strPath);

            }

            if(earthviewDetailModel.photoUrl !=null) {


                Log.i(TAG, "===this Page earthviewDetailModel.photoUrl==" + earthviewDetailModel.photoUrl);



//Loading image from below url into imageView
               /* Glide.with(this)
                        .load(earthviewDetailModel.photoUrl)
                        .placeholder(R.color.colorPlaceholder)
                        .error(R.color.colorError)
                        .fitCenter()
                        .into(ivPhoto);*/

                Glide.with(this)
                        .load(earthviewDetailModel.photoUrl)
                        .asBitmap()
                        .into(new BitmapImageViewTarget(ivPhoto) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                // Do bitmap magic here
                                bitmapWallpaper = resource;
                                super.setResource(resource);
                            }
                        });
            }


            if(earthviewDetailModel.photoUrl !=null)
                Log.i(TAG,"===photoUrl=="+earthviewDetailModel.photoUrl);
        }

    }
}
