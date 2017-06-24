package com.vasyaevstropov.ucozapitest;


import android.util.Base64;
import android.util.Log;

import com.vasyaevstropov.ucozapitest.Retrofit.ApiRetrofit;

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
    private Map<String, String> config;
    private Map<String, String> params;
    private final String ENC = "UTF-8";
     String time, oauth_nonce;
    private final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    public RequestToUcoz() {


    }

    private String getTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    private void setConfig (Map<String, String> config){
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
        } catch (Exception e) {
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

    private String getSignature(String method, String url, String params)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {

        String baseString = method+"&"
                + URLEncoder.encode(url, "UTF-8") +
                "&" + URLEncoder.encode(params, "UTF-8");
        Log.d("Log.d", "Base string: " + baseString);

        String a = "";
        try{
            a =Base64.encodeToString(calculateRFC2104HMAC(baseString, config.get("oauth_consumer_secret")+"&"+config.get("oauth_token_secret")), Base64.CRLF).trim();

        }catch (Exception e){
            e.printStackTrace();
        }
        return a;
    }

    public String get(Map<String, String> config)
    throws UnsupportedEncodingException,NoSuchAlgorithmException, InvalidKeyException{
        setConfig(config);

        final StringBuilder answer = null;

        String signature = getSignature("GET","http://artmurka.com/uapi/shop/request",
                "oauth_consumer_key="+params.get("oauth_consumer_key")+"&"+
                        "oauth_nonce="+params.get("oauth_nonce")+"&"+
                        "oauth_signature_method="+"HMAC-SHA1"+"&"+
                        "oauth_timestamp="+params.get("oauth_timestamp")+"&"+
                        "oauth_token="+params.get("oauth_token")+"&"+
                        "oauth_version="+"1.0"+"&"+
                        "page="+"categories");
        Log.d("Log.d", signature);

        HashMap<String, String> map = new HashMap<>();
        map.put("oauth_signature",signature);
        map.put("oauth_signature_method","HMAC-SHA1");
        map.put("oauth_version","1.0");
        map.put("oauth_consumer_key","murka");
        map.put("oauth_token",params.get("oauth_token"));
        map.put("oauth_nonce", params.get("oauth_nonce"));
        map.put("oauth_timestamp",params.get("oauth_timestamp"));
        map.put("page","categories");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://artmurka.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiRetrofit api = retrofit.create(ApiRetrofit.class);

        api.getShopCategories(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        answer.append(response.body().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return answer.toString();
    }


    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    private byte[] calculateRFC2104HMAC(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        return mac.doFinal(data.getBytes());
       // return toHexString(mac.doFinal(data.getBytes()));
    }

}
