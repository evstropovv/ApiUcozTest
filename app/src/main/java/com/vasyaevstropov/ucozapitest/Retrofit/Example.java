package com.vasyaevstropov.ucozapitest.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Example {

    @SerializedName("success")
    @Expose
    private List<Success> success = null;
    public List<Success> getSuccess() {
        return success;
    }
}
