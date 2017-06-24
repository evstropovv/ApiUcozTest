package com.vasyaevstropov.ucozapitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vasyaevstropov.ucozapitest.Retrofit.ApiRetrofit;
import com.vasyaevstropov.ucozapitest.Retrofit.OAuthGetRequestToken;

import org.w3c.dom.Text;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

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
    private String oauth_signature_method = "HMAC-SHA1";
    private String oauth_version = "1.0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        textView= (TextView)findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });
    }

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

        RequestToUcoz requestToUcoz = new RequestToUcoz(map);
        try{
            textView.setText(requestToUcoz.get());
        } catch (Exception e){

        }

    }

}



