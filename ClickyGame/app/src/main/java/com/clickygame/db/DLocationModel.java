package com.clickygame.db;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by prashant.patel on 7/19/2017.
 */

public class DLocationModel extends RealmObject {

    @SerializedName("Country")
    public String Country;

    @SerializedName("Region")
    public String Region;

    @SerializedName("Image_URL")
    public String Image_URL;

    @SerializedName("Google_Maps_URL")
    public String Google_Maps_URL;

    @SerializedName("isFavorite")
    public String isFavorite="0";

    @SerializedName("ID")
    public int ID;

    public void setCountry(String country) {
        Country = country;
    }

    public void setGoogle_Maps_URL(String google_Maps_URL) {
        Google_Maps_URL = google_Maps_URL;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setImage_URL(String image_URL) {
        Image_URL = image_URL;
    }

    public void setRegion(String region) {
        Region = region;
    }


    public int getID() {
        return ID;
    }

    public String getCountry() {
        return Country;
    }

    public String getGoogle_Maps_URL() {
        return Google_Maps_URL;
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public String getRegion() {
        return Region;
    }

    public String getIsFavorite() {
        return isFavorite;
    }
}
