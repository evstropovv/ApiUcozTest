package com.vasyaevstropov.ucozapitest.Retrofit;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRetrofit {

    @FormUrlEncoded
    @GET("uapi/shop/request")
    Call<String> getShopCategories
            (@FieldMap Map<String, String> map);

}
