package com.vasyaevstropov.ucozapitest;


import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.vasyaevstropov.ucozapitest.Retrofit.ApiRetrofit;
import com.vasyaevstropov.ucozapitest.Retrofit.PagesCategories;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RequestToUcoz {
    private static Map<String, String> config;
    private static Map<String, String> params;
    private static final String ENC = "UTF-8";
    String time, oauth_nonce;

    private String getTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    private void setConfig(Map<String, String> config) {
        this.config = config;

        time = getTime();
        oauth_nonce = oauth_nonce();

        params = new HashMap<>();
        params.put("oauth_version", "1.0");
        params.put("oauth_timestamp", time);
        params.put("oauth_nonce", oauth_nonce);
        params.put("oauth_signature_method", "HMAC-SHA1");
        params.put("oauth_consumer_key", config.get("oauth_consumer_key"));
        params.put("oauth_token", config.get("oauth_token"));
    }


    private static String oauth_nonce() {
        Long time = System.currentTimeMillis();
        String st = time + "" + Math.random();

        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

    /**
     * Создание подписи запроса
     * String method    Метод запроса, например GET
     * string url       URL запроса, например /blog
     * String $params    Все параметры, передаваемые через URL при запросе к API
     * return string
     */

    private String getSignature(String method, String url, String params) {
        String baseString = "";
        try {
            baseString = method + "&" + URLEncoder.encode(url, "UTF-8") + "&" + URLEncoder.encode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("Log.d", "Base string: " + baseString);

        String a = "";
        try {
            a = encode("oauth_consumer_secret" + "&" + config.get("oauth_token_secret"), baseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }

    public String get(Map<String, String> config) {
        setConfig(config);

        final StringBuilder answer = null;

        String signature = getSignature("GET", "http://artmurka.com/uapi/shop/request",
                "oauth_consumer_key=" + params.get("oauth_consumer_key") + "&" +
                        "oauth_nonce=" + params.get("oauth_nonce") + "&" +
                        "oauth_signature_method=" + "HMAC-SHA1" + "&" +
                        "oauth_timestamp=" + params.get("oauth_timestamp") + "&" +
                        "oauth_token=" + params.get("oauth_token") + "&" +
                        "oauth_version=" + "1.0" + "&" +
                        "page=" + "categories");
        Log.d("Log.d", "oauth_signature: " + signature);

        HashMap<String, String> map = new HashMap<>();
        map.put("oauth_signature", signature);
        map.put("oauth_signature_method", "HMAC-SHA1");
        map.put("oauth_version", "1.0");
        map.put("oauth_consumer_key", "murka");
        map.put("oauth_token", params.get("oauth_token"));
        map.put("oauth_nonce", params.get("oauth_nonce"));
        map.put("oauth_timestamp", params.get("oauth_timestamp"));
        map.put("page", "categories");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://artmurka.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRetrofit apiRetrofit = retrofit.create(ApiRetrofit.class);

        apiRetrofit.getShopCategories(signature, "HMAC-SHA1", "1.0", "murka", params.get("oauth_token"),params.get("oauth_nonce"),params.get("oauth_timestamp"),"categories")
                .enqueue(new Callback<List<PagesCategories>>() {
            @Override
            public void onResponse(Call<List<PagesCategories>> call, Response<List<PagesCategories>> response) {
                Log.d("Log.d", response.toString());
            }

            @Override
            public void onFailure(Call<List<PagesCategories>> call, Throwable t) {

            }
        });

        return answer.toString();
    }

    public String encode(String key, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(secret);
        byte[] digest = mac.doFinal(data.getBytes());
        String retVal = Base64.encodeToString(digest, Base64.NO_WRAP);
        return URLEncoder.encode(retVal, "UTF-8");
    }
}
