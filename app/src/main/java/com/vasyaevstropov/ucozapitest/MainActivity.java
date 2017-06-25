package com.vasyaevstropov.ucozapitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vasyaevstropov.ucozapitest.Retrofit.ApiRetrofit;
import com.vasyaevstropov.ucozapitest.Retrofit.OAuthGetRequestToken;

import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    private Gson gson = new GsonBuilder().create();

    private String base_url = "http://uapi.ucoz.com/";
    private String oauth_consumer_key = "murka";
    private String oauth_version = "1.0";
    private String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static String oauth_consumer_secret = "v8qJbaN2NhOnVs6lrqokXJVTvDaGsB";
    private static String oauth_token_secret = "SA5hjGn4A66R2JqraD51IhxVZZX6ELLW4NHMAVWC";
    private static String base_string = "GET&http%3A%2F%2Fartmurka.com%2F%2Fuapi%2Fshop%2Frequest&oauth_consumer_key%3Dmurka%26oauth_nonce%3D7ef96e51765611dd4ef66f68475514d8%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1498371809%26oauth_token%3DpaJTN0ZA6KJGAWgHDRKPVNgFBOOe.qMOl8x5pY2W%26page%3Dcategories";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    makeRequest();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

//    public String encode(String key, String data) throws Exception {
//        Mac mac = Mac.getInstance("HmacSHA1");
//        SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), mac.getAlgorithm());
//        mac.init(secret);
//        byte[] digest = mac.doFinal(data.getBytes());
//        String retVal = Base64.encodeToString(digest, Base64.NO_WRAP);
//        return URLEncoder.encode(retVal,"UTF-8");
//    }


    private void makeRequest() {
        Map<String, String> map = new HashMap<>();
//        'oauth_consumer_key'    => 'Мой consumer_key',
//                'oauth_consumer_secret' => 'Мой consumer_secret',
//                'oauth_token'           => 'Мой token',
//                'oauth_token_secret'    => 'Мой token_secret'
        map.put("oauth_consumer_key", "murka");
        map.put("oauth_consumer_secret", "v8qJbaN2NhOnVs6lrqokXJVTvDaGsB");
        map.put("oauth_token", "paJTN0ZA6KJGAWgHDRKPVNgFBOOe.qMOl8x5pY2W");
        map.put("oauth_token_secret", "SA5hjGn4A66R2JqraD51IhxVZZX6ELLW4NHMAVWC");

        RequestToUcoz requestToUcoz = new RequestToUcoz();

            textView.setText(requestToUcoz.get(map));


    }
}




