package com.example.jinyoungkim.teamgung.ui.gung_ticket;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.model.PayPost;
import com.example.jinyoungkim.teamgung.model.PayResult;
import com.example.jinyoungkim.teamgung.network.NetworkService;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.PayResultActivity;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;

import java.util.Calendar;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.CancelListener;
import kr.co.bootpay.CloseListener;
import kr.co.bootpay.ConfirmListener;
import kr.co.bootpay.DoneListener;
import kr.co.bootpay.ErrorListener;
import kr.co.bootpay.ReadyListener;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialLookingActivity extends AppCompatActivity {

    private NetworkService networkService;
    private PayPost payPost;

    CalendarView calendarView;
    ImageView btn_normalperson,btn_hanbok,btn_old;
    ImageView adult_minus_special,adult_plus_special;
    TextView adult_number_special;
    ImageView btn_time1,btn_time2,btn_time3;
    RelativeLayout payment_special;
    int adult_number_special_i;
    int r_year, r_month, r_day;
    Calendar calendar; // 달력을 클릭 안 했을 때 날짜 default 값을 오늘 날짜로 하기위해 사용

    //  통신에 넘길 데이터
    int palace_id; // 궁 아이디(0 : 경복궁, 1 : 창덕궁, 2 : 창경궁, 3 : 덕수궁, 4 : 종묘 )
    String ticket_title; // 티켓 이름
    String ticket_start; // 티켓 시작 날짜
    String ticket_end; // 티켓 끝나는 날짜
    String ticket_people; // 사람 정보
    int ticket_special; // 특별권 여부
    int ticket_jongro; // 중구인 구분

    private int stuck = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.special);
        setContentView(R.layout.activity_special_looking);

        //        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffEF5E5E);
        }

        networkService = GlobalApplication.getGlobalApplicationContext().getNetworkService();

        // 초기화
        calendarView = (CalendarView)findViewById(R.id.calendar_special);
        btn_normalperson = (ImageView)findViewById(R.id.btn_normalperson);
        btn_hanbok = (ImageView)findViewById(R.id.btn_hanbok);
        btn_old = (ImageView)findViewById(R.id.btn_old);
        adult_minus_special = (ImageView)findViewById(R.id.adult_minus_special);
        adult_plus_special = (ImageView)findViewById(R.id.adult_plus_special);
        adult_number_special = (TextView)findViewById(R.id.adult_number_special);
        adult_number_special.setText("0");

        btn_time1 = (ImageView)findViewById(R.id.btn_time1);
        btn_time2 = (ImageView)findViewById(R.id.btn_time2);
        btn_time3 = (ImageView)findViewById(R.id.btn_time3);

        // 부트페이 초기 설정
        BootpayAnalytics.init(this,"5baa746eb6d49c5a2452ee7f");


        payment_special = (RelativeLayout)findViewById(R.id.payment_special);

        // 날짜 default
        calendar = Calendar.getInstance();
        r_year= calendar.get(Calendar.YEAR);
        r_month = calendar.get(Calendar.MONTH)+1;
        r_day = calendar.get(Calendar.DAY_OF_MONTH);
        ticket_start=r_year+"."+r_month+"."+r_day;
        ticket_end=r_year+"."+r_month+"."+r_day;

        // 1. 가감 버튼
        adult_minus_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adult_number_special_i>0){
                    adult_number_special_i --;
                    adult_number_special.setText(String.valueOf(adult_number_special_i));
                }
            }
        });

        adult_plus_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adult_number_special_i ++;
                adult_number_special.setText(String.valueOf(adult_number_special_i));
            }
        });

        // 2. 달력 리스너
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                r_year = year;
                r_month = month+1;
                r_day = dayOfMonth;
                Log.e("date",String.valueOf(r_year));
                ticket_start=r_year+"."+r_month+"."+r_day;
                ticket_end=r_year+"."+r_month+"."+r_day;
            }
        });

        // 3. 버튼 색깔 변경
        btn_normalperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_normalperson.setImageResource(R.drawable.booking_special_normal_button_yes);
                btn_hanbok.setImageResource(R.drawable.booking_special_hanbok_button_no);
                btn_old.setImageResource(R.drawable.booking_special_old_button_no);
            }
        });

        btn_hanbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_normalperson.setImageResource(R.drawable.booking_special_normal_button_no);
                btn_hanbok.setImageResource(R.drawable.booking_special_hanbok_button_yes);
                btn_old.setImageResource(R.drawable.booking_special_old_button_no);
            }
        });

        btn_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_normalperson.setImageResource(R.drawable.booking_special_normal_button_no);
                btn_hanbok.setImageResource(R.drawable.booking_special_hanbok_button_no);
                btn_old.setImageResource(R.drawable.booking_special_old_button_yes);
            }
        });

        btn_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_time1.setImageResource(R.drawable.booking_special_time_button_yes);
                btn_time2.setImageResource(R.drawable.booking_special_time_button_no);
                btn_time3.setImageResource(R.drawable.booking_special_time_button_no);
            }
        });

        btn_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_time1.setImageResource(R.drawable.booking_special_time_button_no);
                btn_time2.setImageResource(R.drawable.booking_special_time_button_yes);
                btn_time3.setImageResource(R.drawable.booking_special_time_button_no);
            }
        });

        btn_time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_time1.setImageResource(R.drawable.booking_special_time_button_no);
                btn_time2.setImageResource(R.drawable.booking_special_time_button_no);
                btn_time3.setImageResource(R.drawable.booking_special_time_button_yes);
            }
        });


        // 4. 결제하기 버튼
        payment_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adult_number_special_i==0){
                    Toast.makeText(getApplicationContext(),"인원수를 선택해주세요:)",Toast.LENGTH_SHORT).show();
                }else{
                    palace_id=0; // 현재 특별관람은 경복궁 밖에 없음
                    ticket_jongro=0;
                    ticket_special=0;
                    ticket_title="경복궁 특별관람권";
                    Log.e("궁 아이디", String.valueOf(palace_id));
                    Log.e("티켓 종류) ",ticket_title);
                    Log.e("티켓 시작날짜) ",ticket_start);
                    Log.e("티켓 끝나날짜) ", ticket_end);
                    ticket_people = "대인 " + String.valueOf(adult_number_special_i);
                    Log.e("사람정보) ", ticket_people);
                    ticket_special=1;
                    Log.e("특별권 구분) ", String.valueOf(ticket_special));
                    Log.e("종로 구분) ",String.valueOf(ticket_jongro));

                    pay();

                    Bootpay.init(getFragmentManager())
                            .setApplicationId("5baa746eb6d49c5a2452ee7f") // 해당 프로젝트(안드로이드)의 application id 값
                            .setPG(PG.KAKAO) // 결제할 PG 사
                            .setUserPhone("010-1234-5678") // 구매자 전화번호
                            .setMethod(Method.EASY) // 결제수단
                            .setName(ticket_title) // 결제할 상품명
                            .setOrderId("1234") //고유 주문번호로, 생성하신 값을 보내주셔야 합니다.
                            .setPrice(1000) // 결제할 금액
                            //.setAccountExpireAt("2018-09-22") // 가상계좌 입금기간 제한 ( yyyy-mm-dd 포멧으로 입력해주세요. 가상계좌만 적용됩니다. 오늘 날짜보다 더 뒤(미래)여야 합니다 )
                            .setQuotas(new int[] {0,2,3}) // 일시불, 2개월, 3개월 할부 허용, 할부는 최대 12개월까지 사용됨 (5만원 이상 구매시 할부허용 범위)
                            .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                                @Override
                                public void onConfirm(@Nullable String message) {
                                    if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
//                                else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                                    Log.d("confirm", message);

                                }
                            })
                            .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                                @Override
                                public void onDone(@Nullable String message) {
                                    Log.d("done", message);
                                    Intent i = new Intent(getApplicationContext(), PayResultActivity.class);
                                    i.putExtra("palace_type","changdeok");
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                                @Override
                                public void onReady(@Nullable String message) {
                                    Log.d("ready", message);
                                }
                            })
                            .onCancel(new CancelListener() { // 결제 취소시 호출
                                @Override
                                public void onCancel(@Nullable String message) {
                                    Log.d("cancel", message);
                                    Intent i = new Intent(getApplicationContext(), PayResultActivity.class);
                                    i.putExtra("palace_type","changdeok");
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                                @Override
                                public void onError(@Nullable String message) {
                                    Log.d("error", message);
                                }
                            })
                            .onClose(new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                @Override
                                public void onClose(String message) {
                                    Log.d("close", "close");
                                }
                            })
                            .show();
                }



            }
        });

    }

    //    네트워킹
    public void pay() {
        payPost = new PayPost(palace_id,ticket_title,ticket_start,ticket_end,ticket_people,ticket_special,ticket_jongro);
        Call<PayResult> payResultCall = networkService.pay(SharePreferenceController.getTokenHeader(getApplicationContext()),payPost);
        payResultCall.enqueue(new Callback<PayResult>() {
            @Override
            public void onResponse(Call<PayResult> call, Response<PayResult> response) {
                if(response.isSuccessful()){
                    Log.e("SERVER IN","success");
                }
            }

            @Override
            public void onFailure(Call<PayResult> call, Throwable t) {
                GlobalApplication.getGlobalApplicationContext().makeToast("네트워크 상태를 확인해 주세요 :)");
            }
        });
    }
}
