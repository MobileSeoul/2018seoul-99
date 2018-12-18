package com.example.jinyoungkim.teamgung.ui.main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.TicketMainActivity;
import com.example.jinyoungkim.teamgung.ui.gung_tour.TourMainActivity;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.ChangdeokDialog;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.ChanggyeongDialog;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.DuksuDialog;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.GyeongbokDialog;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.JongmyoDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {


    RelativeLayout btn_ticket, btn_tour; //예매정보, 궁궐정보 페이지로 넘어가는 이미지 버튼

    //슬라이드 패널 안 외국인 뷰 - 각 궁별로 텍스트뷰
    TextView foreign_gyeongbok;
    TextView foreign_changdeok;
    TextView foreign_changgyeong;
    TextView foreign_duksu;
    TextView foreign_jongmyo;

    // 슬라이드 패널 안 외국인 뷰 - 각 궁별 텍스트뷰 눌렀을시 나오는 다이얼로그
    GyeongbokDialog gd;
    ChangdeokDialog cd;
    ChanggyeongDialog cgd;
    DuksuDialog dd;
    JongmyoDialog jd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHashKey();

//        뷰 초기화
        btn_ticket = (RelativeLayout)findViewById(R.id.btn_ticket);
        btn_tour = (RelativeLayout)findViewById(R.id.btn_tour);

//        슬라이드 패널 안 외국인 뷰 초기화
        foreign_gyeongbok = (TextView)findViewById(R.id.btn_gyeongbok_foreign);
        foreign_changdeok = (TextView)findViewById(R.id.btn_changdeok_foreign);
        foreign_changgyeong = (TextView)findViewById(R.id.btn_changgyong_foreign);
        foreign_duksu = (TextView)findViewById(R.id.btn_duksu_foreign);
        foreign_jongmyo = (TextView)findViewById(R.id.btn_jongmyo_foreign);

//       1. 티켓 예매/화인 메뉴로 가기
        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), TicketMainActivity.class);
                startActivity(i1);
            }
        });

//       2. 궁 투어 메뉴로 가기
        btn_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(),TourMainActivity.class);
                startActivity(i2);
            }
        });

//        3. 슬라이드 패널 안 외국인 뷰 - 다이얼로그 설정

        DisplayMetrics dm = this.getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해

        gd = new GyeongbokDialog(this);
        cd = new ChangdeokDialog(this);
        cgd = new ChanggyeongDialog(this);
        dd = new DuksuDialog(this);
        jd = new JongmyoDialog(this);

        WindowManager.LayoutParams wm = gd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(gd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미

        //외국인 뷰 - 경복궁
        foreign_gyeongbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gd.show();  //다이얼로그
            }
        });
        //외국인 뷰 - 창덕궁
        foreign_changdeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cd.show();  //다이얼로그
            }
        });
        //외국인 뷰 - 창경궁
        foreign_changgyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cgd.show();  //다이얼로그
            }
        });
        //외국인 뷰 - 덕수궁
        foreign_duksu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dd.show();  //다이얼로그
            }
        });
        //외국인 뷰 - 종묘
        foreign_jongmyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jd.show();  //다이얼로그
            }
        });


    }

//   카카오 로그인 해시키 가져오는 함수
    private void getHashKey(){

        try{

            PackageInfo info = getPackageManager().getPackageInfo("com.example.jinyoungkim.teamgung", PackageManager.GET_SIGNATURES);

            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("TAG: ","key_hash: "+ Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

    }

}
