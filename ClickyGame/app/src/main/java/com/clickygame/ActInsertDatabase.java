package com.clickygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import com.clickygame.db.DLocationModel;
import com.clickygame.db.DMainLocationModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;

import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by prashant.patel on 7/19/2017.
 */

public class ActInsertDatabase extends AppCompatActivity {

    String TAG = "==ActInsertDatabase ==";
    Realm realm;
    ArrayList<DLocationModel> arrayListDLocationModel = new ArrayList<>();

    FloatingActionButton fab,fabSetWallpaper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_insertdb);

            fab = (FloatingActionButton) findViewById(R.id.fab);
            fabSetWallpaper = (FloatingActionButton) findViewById(R.id.fabSetWallpaper);


            /*
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

            // Clear the realm from last time
            Realm.deleteRealm(realmConfiguration);

            // Create a new empty instance of Realm
            realm = Realm.getInstance(realmConfiguration);
            */

            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

            // Clear the realm from last time
            Realm.deleteRealm(realmConfiguration);


            realm = Realm.getInstance(realmConfiguration);

            // Create the Realm instance
          //  realm = Realm.getDefaultInstance();


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            // inset

                    App.sLog("===========insert records====");
                    setupDataInsert();


                }
            });


            fabSetWallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // inset

                    App.sLog("===========display records====");
                    //getAllRecords();

                    Intent intent = new Intent(ActInsertDatabase.this,ActNotification.class);
                    startActivity(intent);



                }
            });

            gettingRecordFromJson();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }


    private void gettingRecordFromJson()
    {
        try{
            //Get Data From Text Resource File Contains Json Data.

            InputStream inputStream = getResources().openRawResource(R.raw.data);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int ctr;
            try {
                ctr = inputStream.read();
                while (ctr != -1) {
                    byteArrayOutputStream.write(ctr);
                    ctr = inputStream.read();
                }
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.v("Text Data", byteArrayOutputStream.toString());
            try {

                // Parse the data into jsonobject to get original data in form of json.
                JSONObject jObject = new JSONObject(byteArrayOutputStream.toString());

                JSONArray jArray = jObject.getJSONArray("datalist");
                String  strCountry="";
                String  strRegion="";
                String strImage_URL ="";
                String strGMap_URL ="";


                for (int i = 0; i < jArray.length(); i++) {
                    strCountry = jArray.getJSONObject(i).getString("Country");
                    strRegion = jArray.getJSONObject(i).getString("Region");
                    strImage_URL = jArray.getJSONObject(i).getString("Image URL");
                    strGMap_URL = jArray.getJSONObject(i).getString("Google Maps URL");

                    DLocationModel dLocationModel = new DLocationModel();
                    dLocationModel.Country = strCountry;
                    dLocationModel.Region = strRegion;
                    dLocationModel.Image_URL = strImage_URL;
                    dLocationModel.Google_Maps_URL = strGMap_URL;
                    arrayListDLocationModel.add(dLocationModel);

                    Log.i(TAG,"==Country=="+strCountry);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setupDataInsert() {
        try {


            if(arrayListDLocationModel !=null && arrayListDLocationModel.size() > 0){


                realm.beginTransaction();
                Collection<DLocationModel> realmDLocationModel = realm.copyToRealm(arrayListDLocationModel);
                realm.commitTransaction();


                ArrayList<DLocationModel> arrTemp = new ArrayList<DLocationModel>(realmDLocationModel);
                for(int i=0; i < arrTemp.size(); i++){
                    App.sLog(i+"===data -- =="+arrTemp.get(i).getImage_URL());
                }


                /*for(int i=0; i < arrayListDLocationModel.size(); i++){


                    final DLocationModel dLocationModel =  arrayListDLocationModel.get(i);
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            // This will create a new object in Realm or throw an exception if the
                            // object already exists (same primary key)
                            // realm.copyToRealm(obj);

                            // This will update an existing object with the same primary key
                            // or create a new object if an object with no primary key = 42
                            realm.copyToRealmOrUpdate(dLocationModel );
                        }
                    });
                }*/

            }



        /*
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                }
            });

            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllRecords(){
        try{

            RealmResults<DLocationModel> arrDLocationModel = realm.where(DLocationModel.class).findAll();

            App.sLog("===arrDLocationModel=="+arrDLocationModel);

            List<DLocationModel> arraDLocationModel = arrDLocationModel;

            for(int k=0;k<arraDLocationModel.size();k++)
            {
                App.sLog(k+"===arraDLocationModel=="+arraDLocationModel.get(k).getImage_URL());
            }



            RealmQuery<DLocationModel> query = realm.where(DLocationModel.class);
            /*
            for (String id : ids) {
                query.or().equalTo("myField", id);
            }*/


            RealmResults<DLocationModel> results = query.findAll();

            App.sLog("===results=="+results);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
