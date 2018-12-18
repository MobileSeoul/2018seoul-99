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
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_normal.ChangdeokNormalActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_special.ChangdeokSpecial1Activity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_special.ChanggyeongSpecial1Activity;

public class BookingChangdeokActivity extends AppCompatActivity {

    private ImageView btn_normal_changdeok, btn_special_changdeok,profile_booking_changdeok; // 일반권, 특별권 버튼, 프로필사진

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_changdeok);

        //        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffF9C210);
        }

        // 초기화
        btn_normal_changdeok = (ImageView)findViewById(R.id.btn_normal_changdeok);
        btn_special_changdeok = (ImageView)findViewById(R.id.btn_special_changdeok);

        // 프로필 사진
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String profile = preferences.getString("profile","");


        // 버튼 리스너

//       창덕궁 일반권
        btn_normal_changdeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_special_changdeok.setImageResource(R.drawable.changdeokgung_special_x);
                btn_normal_changdeok.setImageResource(R.drawable.changdeokgung_normal_o);
                startActivity(new Intent(getApplicationContext(), ChangdeokNormalActivity.class));
                finish();
            }
        });

//       창덕궁 특별권
        btn_special_changdeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_special_changdeok.setImageResource(R.drawable.changdeokgung_special_o);
                btn_normal_changdeok.setImageResource(R.drawable.changdeokgung_normal_x);
                startActivity(new Intent(getApplicationContext(), ChangdeokSpecial1Activity.class));
                finish();
            }
        });


    }
}
