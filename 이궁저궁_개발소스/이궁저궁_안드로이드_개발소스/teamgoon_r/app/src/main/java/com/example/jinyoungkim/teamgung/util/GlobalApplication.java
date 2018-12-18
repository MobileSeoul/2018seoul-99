package com.example.jinyoungkim.teamgung.util;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.jinyoungkim.teamgung.network.NetworkService;
import com.kakao.auth.KakaoSDK;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalApplication extends Application {
    private static GlobalApplication mInstance;
    private static volatile Activity currentActivity = null;
    private static String baseUrl = "http://52.78.138.126:3000";
    private NetworkService networkService;

    public NetworkService getNetworkService() {
        return networkService;
    }

        public static Activity getCurrentActivity() {
            Log.e("TAG", "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
            return currentActivity;
        }

        public static void setCurrentActivity (Activity currentActivity){
            GlobalApplication.currentActivity = currentActivity;
        }

        /**
         * singleton
         * @return singleton
         */
        public static GlobalApplication getGlobalApplicationContext() {
            if (mInstance == null)
                throw new IllegalStateException("this application does not inherit GlobalApplication");
            return mInstance;
        }

        @Override
        public void onCreate () {
            super.onCreate();
            mInstance = this;
            KakaoSDK.init(new KakaoSDKAdapter());
            buildService();
        }


        public void buildService () {
            Retrofit.Builder builder = new Retrofit.Builder();
            Retrofit retrofit = builder
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            networkService = retrofit.create(NetworkService.class);
        }
        public void makeToast (String message){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
