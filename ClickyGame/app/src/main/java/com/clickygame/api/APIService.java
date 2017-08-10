package com.clickygame.api;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by prashant.patel on 7/18/2017.
 */

public interface APIService {

    @GET("/_api/{path}")
    Call<EarthviewDetailModel> getEarthviewDetail
            (
                    @Path("path") String path
            );
}

