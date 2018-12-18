package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.TicketMainActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking.BookingChangdeokActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking.BookingChanggyeongActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking.BookingDuksuActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking.BookingGyeongbokActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking.BookingJongmyoActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_normal.JongmyoNormalActivity;
import com.example.jinyoungkim.teamgung.ui.main.MainActivity;

public class Loading2Activity extends AppCompatActivity {
    ImageView loading;
    String flag;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading2);

        loading = (ImageView)findViewById(R.id.loading);
        Glide.with(this).load(R.drawable.loading_splash3).into(loading);

        Intent i = getIntent();
        flag = i.getStringExtra("fragment_type2");

        if(flag.equals("changdeok")){
            intent = new Intent(getApplicationContext(),BookingChangdeokActivity.class);
        }else if (flag.equals("changgyeong")){
            intent = new Intent(getApplicationContext(),BookingChanggyeongActivity.class);
        }else if(flag.equals("duksu")){
            intent = new Intent(getApplicationContext(), BookingDuksuActivity.class);
        }else if(flag.equals("gyeongbok")){
            intent = new Intent(getApplicationContext(), BookingGyeongbokActivity.class);
        }else if(flag.equals("jongmyo")){
            intent = new Intent(getApplicationContext(),BookingJongmyoActivity.class);
        }else if(flag.equals("confirm")){
            intent = new Intent(getApplicationContext(),TicketMainActivity.class);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        }, 3000);// 3 초


    }
}
