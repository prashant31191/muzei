package com.clickygame.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by prashant.patel on 7/18/2017.
 */

public class EarthviewDetailModel implements Serializable
{
    @SerializedName("id")
    @Expose
    public   String id;

    @SerializedName("slug")
    @Expose
    public   String slug;

    @SerializedName("url")
    @Expose
    public   String url;

    @SerializedName("api")
    @Expose
    public   String api;

    @SerializedName("title")
    @Expose
    public   String title;

    @SerializedName("lat")
    @Expose
    public   String lat;

    @SerializedName("lan")
    @Expose
    public   String lan;

    @SerializedName("photoUrl")
    @Expose
    public   String photoUrl;

    @SerializedName("thumbUrl")
    @Expose
    public   String thumbUrl;


    @SerializedName("downloadUrl")
    @Expose
    public   String downloadUrl;

    @SerializedName("region")
    @Expose
    public   String region;

    @SerializedName("country")
    @Expose
    public   String country;

    @SerializedName("attribution")
    @Expose
    public   String attribution;

    @SerializedName("mapsLink")
    @Expose
    public   String mapsLink;

    @SerializedName("mapsTitle")
    @Expose
    public   String mapsTitle;

    @SerializedName("nextUrl")
    @Expose
    public   String nextUrl;

    @SerializedName("nextApi")
    @Expose
    public   String nextApi;

    @SerializedName("prevUrl")
    @Expose
    public   String prevUrl;

    @SerializedName("prevApi")
    @Expose
    public   String prevApi;

}
