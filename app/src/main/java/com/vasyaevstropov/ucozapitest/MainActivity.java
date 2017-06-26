package com.vasyaevstropov.ucozapitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vasyaevstropov.ucozapitest.Retrofit.Example;
import com.vasyaevstropov.ucozapitest.Retrofit.Success;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });
    }

    private Example makeRequest() {
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
        return  requestToUcoz.get(map);
    }
}




