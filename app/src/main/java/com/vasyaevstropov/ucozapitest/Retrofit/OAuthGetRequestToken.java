package com.vasyaevstropov.ucozapitest.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OAuthGetRequestToken {
    @SerializedName("oauth_token ")
    @Expose
    private String oauth_token;
    @SerializedName("oauth_token_secret")
    @Expose
    private String oauth_token_secret;
    @SerializedName("error")
    @Expose
    Error error;


    public String getOauth_token() {
        return oauth_token;
    }

    public String getOauth_token_secret() {
        return oauth_token_secret;
    }

    public Error getError() {
        return error;
    }


    public class Error {
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("code")
        @Expose
        private String code;


        public String getMsg() {
            return msg;
        }


        public void setMsg(String msg) {
            this.msg = msg;
        }


        public String getCode() {
            return code;
        }


        public void setCode(String code) {
            this.code = code;
        }

  }




}




