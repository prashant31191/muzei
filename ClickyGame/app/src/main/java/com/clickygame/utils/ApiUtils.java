package com.clickygame.utils;

import com.clickygame.api.APIService;
import com.clickygame.api.RetrofitClient;

/**
 * Created by prashant.patel on 7/18/2017.
 */

public class ApiUtils {

    private ApiUtils() {}
//https://earthview.withgoogle.com/_api/istanbul-turkey-1888.json
    public static final String BASE_URL = "https://earthview.withgoogle.com";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
