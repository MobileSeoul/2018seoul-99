package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.TicketMainActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.confirm_reservation.ConfirmReservationFragment;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;

public class PayResultActivity extends AppCompatActivity {

    TextView goto_confirm_reservation, goto_home;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i=getIntent();
        Log.e("palace_type",i.getStringExtra("palace_type"));
        switch (i.getStringExtra("palace_type")){
            case "changdeok":
            {
                setTheme(R.style.changdeok);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(0xffF9C210);
                }
            }
            case "changgyeong":
            {
                setTheme(R.style.changgyeong);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(0xffC8D509);
                }

            }
            case "duksu":
            {
                setTheme(R.style.duksu);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(0xff394E7E);
                }
            }
            case "gyeongbok":
            {
                setTheme(R.style.gyeongbok);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(0xffB54141);
                }
            }
            case "jongmyo":
            {
                setTheme(R.style.jongmyo);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(0xff60A682);
                }
            }
            default:
                setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_pay_result);

        goto_confirm_reservation = (TextView)findViewById(R.id.goto_confirm_reservation);
        goto_home = (TextView)findViewById(R.id.goto_home);


        goto_confirm_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceController.setDataChange(getApplicationContext(),0);
                Intent i = new Intent(getApplicationContext(),TicketMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        goto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceController.setDataChange(getApplicationContext(),1);
                Intent i = new Intent(getApplicationContext(),TicketMainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
