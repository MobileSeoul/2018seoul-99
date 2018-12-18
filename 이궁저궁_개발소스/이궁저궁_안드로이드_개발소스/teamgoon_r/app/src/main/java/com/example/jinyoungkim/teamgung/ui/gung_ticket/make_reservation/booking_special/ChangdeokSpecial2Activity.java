package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_special;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ChangdeokSpecial2Activity extends AppCompatActivity {

    private NetworkService networkService;
    private PayPost payPost;

    ImageView changdeok_special_btn1,changdeok_special_btn2,changdeok_special_btn3; // 하단 정보 보기 버튼
    ImageView adult_minus_changdeok_special,adult_plus_changdeok_special; // 인원 수
    TextView adult_number_changdeok_special;
    int adult_number_changdeok_special_i;
    LinearLayout changdeok_special_layout1,changdeok_special_layout2,changdeok_special_layout3; // 하단 정보
    RelativeLayout payment_changdeok_special; // 결제하기 버튼
    int r_year, r_month, r_day;
    Intent i;

//  통신에 넘길 데이터
    int palace_id; // 궁 아이디(0 : 경복궁, 1 : 창덕궁, 2 : 창경궁, 3 : 덕수궁, 4 : 종묘 )
    String ticket_title; // 티켓 이름
    String ticket_start; // 티켓 시작 날짜
    String ticket_end; // 티켓 끝나는 날짜
    String ticket_people; // 사람 정보
    int ticket_special; // 특별권 여부
    int ticket_jongro; // 종로인 구분

    private int stuck = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.changdeok);
        setContentView(R.layout.activity_changdeok_special2);

        //        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffF9C210);
        }

        networkService = GlobalApplication.getGlobalApplicationContext().getNetworkService();

        // Special1Activity에서 받은 예매 날짜
        i = getIntent();
        r_year = i.getIntExtra("r_year",0);
        r_month = i.getIntExtra("r_month",0);
        r_day = i.getIntExtra("r_day",0);

        ticket_start = r_year+"."+r_month+"."+r_day;


        // 초기화
        changdeok_special_btn1 = (ImageView)findViewById(R.id.changdeok_special_btn1);
        changdeok_special_btn2 = (ImageView)findViewById(R.id.changdeok_special_btn2);
        changdeok_special_btn3 = (ImageView)findViewById(R.id.changdeok_special_btn3);

        adult_minus_changdeok_special = (ImageView)findViewById(R.id.adult_minus_changdeok_special);
        adult_number_changdeok_special = (TextView)findViewById(R.id.adult_number_changdeok_special);
        adult_number_changdeok_special.setText("0");
        adult_plus_changdeok_special = (ImageView)findViewById(R.id.adult_plus_changdeok_special);

        changdeok_special_layout1 = (LinearLayout)findViewById(R.id.changdeok_special_layout1);
        changdeok_special_layout2 = (LinearLayout)findViewById(R.id.changdeok_special_layout2);
        changdeok_special_layout3 = (LinearLayout)findViewById(R.id.changdeok_special_layout3);

        // 부트페이 초기 설정
        BootpayAnalytics.init(this,"5baa746eb6d49c5a2452ee7f");


        payment_changdeok_special = (RelativeLayout)findViewById(R.id.payment_changdeok_special);


        // 1. 궁궐 통합 관람권
        changdeok_special_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changdeok_special_btn1.setImageResource(R.drawable.changdeokgung_booking_every_yes);
                changdeok_special_btn2.setImageResource(R.drawable.changdeokgung_booking_always_no);
                changdeok_special_btn3.setImageResource(R.drawable.changdeokgung_booking_lunch_no);

                changdeok_special_layout1.setVisibility(View.VISIBLE);
                changdeok_special_layout2.setVisibility(View.GONE);
                changdeok_special_layout3.setVisibility(View.GONE);

                ticket_title = "궁궐 통합 관람권";
                r_year = i.getIntExtra("r_year",0);
                r_month = i.getIntExtra("r_month",0);
                r_day = i.getIntExtra("r_day",0);
                r_month = r_month+3;
                ticket_end = r_year+"."+r_month+"."+r_day;
            }
        });

        // 2. 상시 관람권
        changdeok_special_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changdeok_special_btn1.setImageResource(R.drawable.changdeokgung_booking_every_no);
                changdeok_special_btn2.setImageResource(R.drawable.changdeokgung_booking_always_yes);
                changdeok_special_btn3.setImageResource(R.drawable.changdeokgung_booking_lunch_no);

                changdeok_special_layout1.setVisibility(View.GONE);
                changdeok_special_layout2.setVisibility(View.VISIBLE);
                changdeok_special_layout3.setVisibility(View.GONE);

                ticket_title="상시 관람권";
                r_year = i.getIntExtra("r_year",0);
                r_month = i.getIntExtra("r_month",0);
                r_day = i.getIntExtra("r_day",0);
                r_month = r_month+1;
                ticket_end = r_year+"."+r_month+"."+r_day;
                ticket_special = 1;
            }
        });

        // 3. 점심시간 관람권
        changdeok_special_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changdeok_special_btn1.setImageResource(R.drawable.changdeokgung_booking_every_no);
                changdeok_special_btn2.setImageResource(R.drawable.changdeokgung_booking_always_no);
                changdeok_special_btn3.setImageResource(R.drawable.changdeokgung_booking_lunch_yes);

                changdeok_special_layout1.setVisibility(View.GONE);
                changdeok_special_layout2.setVisibility(View.GONE);
                changdeok_special_layout3.setVisibility(View.VISIBLE);

                ticket_title = "점심시간 관람권";
                r_year = i.getIntExtra("r_year",0);
                r_month = i.getIntExtra("r_month",0);
                r_day = i.getIntExtra("r_day",0);
                r_month = r_month+3;
                ticket_end = r_year+"."+r_month+"."+r_day;
            }
        });

        // 4. 인원 수 가감 버튼
        adult_minus_changdeok_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adult_number_changdeok_special_i>0){
                    adult_number_changdeok_special_i --;
                    adult_number_changdeok_special.setText(String.valueOf(adult_number_changdeok_special_i));
                }
            }
        });

        adult_plus_changdeok_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adult_number_changdeok_special_i ++;
                adult_number_changdeok_special.setText(String.valueOf(adult_number_changdeok_special_i));
            }
        });




        // 5. 결제하기 버튼
        payment_changdeok_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adult_number_changdeok_special_i==0){
                    Toast.makeText(getApplicationContext(),"인원수를 선택해주세요:)",Toast.LENGTH_SHORT).show();
                }
                else {
                    palace_id = 1; // 창덕궁 아이디
                    ticket_jongro = 0;
                    Log.e("궁 아이디", String.valueOf(palace_id));
                    Log.e("특별권 종류) ",ticket_title);
                    Log.e("티켓 시작날짜) ",ticket_start);
                    Log.e("티켓 끝나날짜) ", ticket_end);
                    ticket_people = "대인 " + adult_number_changdeok_special_i;
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
