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
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_normal.GyeongbokNormalActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_special.ChangdeokSpecial1Activity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_special.GyeongbokSpecial1Activity;

// 경복궁 예매 액티비티

public class BookingGyeongbokActivity extends AppCompatActivity {

    ImageView btn_normal_gyeongbok, btn_special_gyeongbok, profile_booking_gyeongbok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_gyeongbok);

        //        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffB54141);
        }

        // 초기화
        btn_normal_gyeongbok = (ImageView) findViewById(R.id.btn_normal_gyeongbok);
        btn_special_gyeongbok = (ImageView)findViewById(R.id.btn_special_gyeongbok);

        // 프로필 사진
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String profile = preferences.getString("profile","");


        // 버튼 리스너

//       창덕궁 일반권
        btn_normal_gyeongbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_special_gyeongbok.setImageResource(R.drawable.gyeongbokgung_booking_special_noselect);
                btn_normal_gyeongbok.setImageResource(R.drawable.gyeongbokgung_booking_normal_select);
                startActivity(new Intent(getApplicationContext(), GyeongbokNormalActivity.class));
                finish();
            }
        });

//       창덕궁 특별권
        btn_special_gyeongbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_special_gyeongbok.setImageResource(R.drawable.gyeongbokgung_booking_special_select);
                btn_normal_gyeongbok.setImageResource(R.drawable.gyeongbokgung_booking_normal_noselect);
                startActivity(new Intent(getApplicationContext(), GyeongbokSpecial1Activity.class));
                finish();
            }
        });




    }
}
