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
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_normal.ChanggyeongNormalActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_normal.DuksuNormalActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_special.ChanggyeongSpecial1Activity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_special.DuksuSpecial1Activity;

public class BookingDuksuActivity extends AppCompatActivity {

    ImageView btn_normal_duksu, btn_special_duksu, profile_booking_duksu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.duksu);
        setContentView(R.layout.activity_booking_duksu);

        //        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff394E7E);
        }

        // 초기화
        btn_normal_duksu = (ImageView) findViewById(R.id.btn_normal_duksu);
        btn_special_duksu = (ImageView)findViewById(R.id.btn_special_duksu);

        // 프로필 사진
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String profile = preferences.getString("profile","");


        // 버튼 리스너

//       창덕궁 일반권
        btn_normal_duksu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_special_duksu.setImageResource(R.drawable.deoksugung_special_x);
                btn_normal_duksu.setImageResource(R.drawable.deoksugung_normal_o);
                startActivity(new Intent(getApplicationContext(), DuksuNormalActivity.class));
                finish();
            }
        });

//       창덕궁 특별권
        btn_special_duksu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_special_duksu.setImageResource(R.drawable.deoksugung_special_o);
                btn_normal_duksu.setImageResource(R.drawable.deoksugung_normal_x);
                startActivity(new Intent(getApplicationContext(), DuksuSpecial1Activity.class));
                finish();
            }
        });



    }
}
