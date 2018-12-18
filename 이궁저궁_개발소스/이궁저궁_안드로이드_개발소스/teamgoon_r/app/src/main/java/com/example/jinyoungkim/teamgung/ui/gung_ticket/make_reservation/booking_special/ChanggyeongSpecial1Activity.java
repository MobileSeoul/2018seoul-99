package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_special;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RelativeLayout;

import com.example.jinyoungkim.teamgung.R;

import java.util.Calendar;

public class ChanggyeongSpecial1Activity extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;
    RelativeLayout next_btn_changgyeong;
    int r_year, r_month, r_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.changgyeong);
        setContentView(R.layout.activity_changgyeong_special1);

        //        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffC8D509);
        }

        // 초기화
        calendarView = (CalendarView)findViewById(R.id.calendar_changgyeong_special);
        next_btn_changgyeong = (RelativeLayout)findViewById(R.id.next_btn_changgyeong);

        // 날짜 default
        calendar = Calendar.getInstance();
        r_year= calendar.get(Calendar.YEAR);
        r_month = calendar.get(Calendar.MONTH)+1;
        r_day = calendar.get(Calendar.DAY_OF_MONTH);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                r_year = year;
                r_month = month+1;
                r_day = dayOfMonth;
            }
        });

        next_btn_changgyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("예매날짜) ", r_year+"년 "+r_month+"월 "+r_day+"일");

                Intent i = new Intent(getApplicationContext(),ChanggyeongSpecial2Activity.class);
                i.putExtra("r_year",r_year);
                i.putExtra("r_month",r_month);
                i.putExtra("r_day",r_day);
                startActivity(i);
                finish();
            }
        });
    }
}
