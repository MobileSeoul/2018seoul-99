package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_normal.JongmyoNormalActivity;

public class BookingJongmyoActivity extends AppCompatActivity {

    ImageView btn_normal_jongmyo,profile_booking_jongmyo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.jongmyo);
        setContentView(R.layout.activity_booking_jongmyo);

        //        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff60A682);
        }

        // 초기화
        btn_normal_jongmyo = (ImageView)findViewById(R.id.btn_normal_jongmyo);

        // 프로필 사진
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String profile = preferences.getString("profile","");


        btn_normal_jongmyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_normal_jongmyo.setImageResource(R.drawable.jongmyo_normal_o);
                startActivity(new Intent(getApplicationContext(), JongmyoNormalActivity.class));
                finish();
            }
        });
    }
}
