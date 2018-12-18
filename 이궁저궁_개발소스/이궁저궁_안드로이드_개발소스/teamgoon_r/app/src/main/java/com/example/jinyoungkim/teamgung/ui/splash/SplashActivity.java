package com.example.jinyoungkim.teamgung.ui.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        splash = (ImageView)findViewById(R.id.splash);

        Glide.with(this).load(R.drawable.splash2).into(splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        }, 3000);// 2 초
    }
}
