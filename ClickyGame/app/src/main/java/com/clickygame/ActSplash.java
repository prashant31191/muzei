package com.clickygame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Random;

/**
 * Created by Admin on 10/21/2016.
 */
public class ActSplash extends Activity {

    private boolean isAppUpdated = false;
    private static int TIME = 2000;

    /**
     * The M ken burns.
     */
    KenBurnsView mKenBurns;


    private final static String TAG = ActSplash.class.getSimpleName();

    //private Elasticode elasticode;
    //int i=0;


    App app;


    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.act_splash_screen);

        app = (App) getApplicationContext();

        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);


        String strImgUrl = "http://www.hdwallpapers.in/download/blue_retina_abstract_4k-1080x1920.jpg";

        Glide.with(this)
                .load(strImgUrl )
                .asBitmap()
                .placeholder(R.color.colorPrimaryDark)
                .into(new BitmapImageViewTarget(mKenBurns) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        // Do bitmap magic here
                     //   bitmapWallpaper = resource;
                        super.setResource(resource);
                    }
                });

       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int i = random.nextInt(array.length);

                Animation animationFadeIn = AnimationUtils.loadAnimation(ActSplash.this, R.anim.fadein);
                mKenBurns.setImageDrawable(ActSplash.this.getResources().getDrawable(array[i]));
                mKenBurns.startAnimation(animationFadeIn);


            }
        });*/

        Animation animationFadeIn = AnimationUtils.loadAnimation(ActSplash.this, R.anim.fadein);
        //mKenBurns.setImageDrawable(ActSplash.this.getResources().getDrawable(array[i]));


        mKenBurns.startAnimation(animationFadeIn);




        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        String refreshedToken = App.sharePrefrences.getStringPref("strDeviceId");
        App.showLog("====refreshedToken===device token===" + refreshedToken);
        if (refreshedToken != null && refreshedToken.length() > 5) {
            App.sharePrefrences.setPref("strDeviceId", refreshedToken);
            TIME = 2000;
        } else {
            getDeviceToken();
        }

        displaySplash();

        try {


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Get Device token - Function
    private void getDeviceToken() {
        try {

            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);
            // [START subscribe_topics]
            //FirebaseMessaging.getInstance().subscribeToTopic("news");
            Log.d(TAG, "Subscribed to news topic");
            // [END subscribe_topics]
            Log.d(TAG, "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());

            if (refreshedToken != null && refreshedToken.length() > 5) {
                App.sharePrefrences.setPref("strDeviceId", refreshedToken);

                TIME = 2000;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // splash screen set with timing
    private void displaySplash() {
        // TODO Auto-generated method stub b

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (isAppUpdated == false) {
                    Intent iv = new Intent(ActSplash.this, MainActivity.class);
                    iv.putExtra("from", "splash");
                    startActivity(iv);
                    finish();
                }
            }
        }, TIME);
    }



}
