package com.vasyaevstropov.ucozapitest.Retrofit;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRetrofit {


    @GET("uapi/shop/request?")
    Call<List<PagesCategories>> getShopCategories (@Query("oauth_signature") String oauth_signature,
                                                   @Query("oauth_signature_method") String oauth_signature_method,
                                                   @Query("oauth_version") String oauth_version,
                                                   @Query("oauth_consumer_key") String oauth_consumer_key,
                                                   @Query("oauth_token") String oauth_token,
                                                   @Query("oauth_nonce") String oauth_nonce,
                                                   @Query("oauth_timestamp") String oauth_timestamp,
                                                   @Query("page") String page);

}
