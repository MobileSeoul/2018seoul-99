package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking_normal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.model.PayPost;
import com.example.jinyoungkim.teamgung.model.PayResult;
import com.example.jinyoungkim.teamgung.network.NetworkService;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.PayResultActivity;
import com.example.jinyoungkim.teamgung.ui.gung_tour.TourMainActivity;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;

import org.w3c.dom.Text;

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

import static android.os.Build.ID;

// 창덕궁 일반권 예매 페이지

public class ChangdeokNormalActivity extends AppCompatActivity {

    private NetworkService networkService;
    private PayPost payPost;

    //  통신에 넘길 데이터
    int palace_id; // 궁 아이디(0 : 경복궁, 1 : 창덕궁, 2 : 창경궁, 3 : 덕수궁, 4 : 종묘 )
    String ticket_title; // 티켓 이름
    String ticket_start; // 티켓 시작 날짜
    String ticket_end; // 티켓 끝나는 날짜
    String ticket_people=""; // 사람 정보
    int ticket_special; // 특별권 여부
    int ticket_jongro; // 종로인 구분
    Calendar calendar;

    private int stuck = 10;

    LinearLayout yesselect_palace_changdeok, yesselect_garden_changdeok; // 궁궐전각/왕실후원 탭 (선택)
    ImageView noselect_palace_changdeok, noselect_garden_changdeok; // 궁궐전각/왕실후원 탭 (미선택)
    CalendarView calendar_changdeok; // 캘린더
    int r_year, r_month, r_day; // 예약 날짜
    RelativeLayout payment_changdeok_normal; // 결제하기 버튼

//   궁궐전각 티켓 +,- 버튼 , 숫자
    ImageView adult_minus_changdeok_palace,  adult_plus_changdeok_palace; //궁궐 전각 대인(3000원)
    ImageView jongro_minus_changdeok_palace, jongro_plus_changdeok_palace; // 종로구민 50% 할인
    TextView adult_number_changdeok_palace, jongro_number_changdeok_palace;
    int adult_number_changdeok_palace_i,jongro_number_changdeok_palace_i;


//   왕실 정원 (후원) +,- 버튼, 숫자
    ImageView student1_minus_changdeok_garden, student1_plus_changdeok_garden; // 청소년 (2500원)
    ImageView jongro1_minus_changdeok_garden, jongro1_plus_changdeok_garden; //종로구민 50%할인
    TextView student1_number_changdeok_garden, jongro1_number_changdeok_garden;
    int student1_number_changdeok_garden_i, jongro1_number_changdeok_garden_i;


    ImageView student2_minus_changdeok_garden, student2_plus_changdeok_garden; // 청년 (5000원)
    ImageView jongro2_minus_changdeok_garden, jongro2_plus_changdeok_garden; //종로구민 50%할인
    TextView student2_number_changdeok_garden, jongro2_number_changdeok_garden;
    int student2_number_changdeok_garden_i,jongro2_number_changdeok_garden_i;

    ImageView adult_minus_changdeok_garden, adult_plus_changdeok_garden; // 성인 (8000원)
    ImageView jongro3_minus_changdeok_garden, jongro3_plus_changdeok_garden; //종로구민 50%할인
    TextView adult_number_changdeok_garden, jongro3_number_changdeok_garden;
    int adult_number_changdeok_garden_i, jongro3_number_changdeok_garden_i;

    ImageView old_minus_changdeok_garden, old_plus_changdeok_garden; // 경로 (5000원)
    ImageView jongro4_minus_changdeok_garden, jongro4_plus_changdeok_garden; //종로구민 50%할인
    TextView old_number_changdeok_garden, jongro4_number_changdeok_garden;
    int old_number_changdeok_garden_i, jongro4_number_changdeok_garden_i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.changdeok);
        setContentView(R.layout.activity_changdeok_normal);


        //        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffF9C210);
        }

        // 초기화
        // 날짜 default
        calendar = Calendar.getInstance();
        r_year= calendar.get(Calendar.YEAR);
        r_month = calendar.get(Calendar.MONTH)+1;
        r_day = calendar.get(Calendar.DAY_OF_MONTH);
        ticket_start=r_year+"."+r_month+"."+r_day;
        ticket_end=r_year+"."+r_month+"."+r_day;

        // 부트페이 초기 설정
        BootpayAnalytics.init(this,"5baa746eb6d49c5a2452ee7f");
        networkService = GlobalApplication.getGlobalApplicationContext().getNetworkService();

        // 1. 하단 메뉴 설정
        yesselect_garden_changdeok = (LinearLayout)findViewById(R.id.yesselect_garden_changdeok);
        yesselect_palace_changdeok = (LinearLayout)findViewById(R.id.yesselect_palace_changdeok);

        noselect_garden_changdeok = (ImageView)findViewById(R.id.noselect_garden_changdeok);
        noselect_palace_changdeok = (ImageView)findViewById(R.id.noselect_palace_changdeok);

        // 2. 캘린더
        calendar_changdeok = (CalendarView)findViewById(R.id.calendar_changdeok);

        // 3.  궁궐전각 티켓 +,- 버튼 , 숫자
        adult_minus_changdeok_palace = (ImageView)findViewById(R.id.adult_minus_changdeok_palace);
        adult_plus_changdeok_palace = (ImageView)findViewById(R.id.adult_plus_changdeok_palace);
        jongro_minus_changdeok_palace = (ImageView)findViewById(R.id.jongro_minus_changdeok_palace);
        jongro_plus_changdeok_palace = (ImageView)findViewById(R.id.jongro_plus_changdeok_palace);

        adult_number_changdeok_palace = (TextView)findViewById(R.id.adult_number_changdeok_palace); adult_number_changdeok_palace.setText("0");
        adult_number_changdeok_palace_i = Integer.parseInt(adult_number_changdeok_palace.getText().toString());
        jongro_number_changdeok_palace = (TextView)findViewById(R.id.jongro_number_changdeok_palace); jongro_number_changdeok_palace.setText("0");
        jongro_number_changdeok_palace_i = Integer.parseInt(jongro_number_changdeok_palace.getText().toString());

        // 4. 왕실 정원 (후원) +,- 버튼, 숫자
        student1_minus_changdeok_garden = (ImageView)findViewById(R.id.student1_minus_changdeok_garden);
        student1_plus_changdeok_garden = (ImageView)findViewById(R.id.student1_plus_changdeok_garden);
        jongro1_minus_changdeok_garden = (ImageView)findViewById(R.id.jongro1_minus_changdeok_garden);
        jongro1_plus_changdeok_garden = (ImageView)findViewById(R.id.jongro1_plus_changdeok_garden);

        student1_number_changdeok_garden = (TextView)findViewById(R.id.student1_number_changdeok_garden); student1_number_changdeok_garden.setText("0");
        student1_number_changdeok_garden_i = Integer.parseInt(student1_number_changdeok_garden.getText().toString());
        jongro1_number_changdeok_garden = (TextView)findViewById(R.id.jongro1_number_changdeok_garden); jongro1_number_changdeok_garden.setText("0");
        jongro1_number_changdeok_garden_i = Integer.parseInt(jongro1_number_changdeok_garden.getText().toString());


        student2_minus_changdeok_garden = (ImageView)findViewById(R.id.student2_minus_changdeok_garden);
        student2_plus_changdeok_garden = (ImageView)findViewById(R.id.student2_plus_changdeok_garden);
        jongro2_minus_changdeok_garden = (ImageView)findViewById(R.id.jongro2_minus_changdeok_garden);
        jongro2_plus_changdeok_garden = (ImageView)findViewById(R.id.jongro2_plus_changdeok_garden);

        student2_number_changdeok_garden = (TextView)findViewById(R.id.student2_number_changdeok_garden); student2_number_changdeok_garden.setText("0");
        student2_number_changdeok_garden_i=Integer.parseInt(student2_number_changdeok_garden.getText().toString());
        jongro2_number_changdeok_garden = (TextView)findViewById(R.id.jongro2_number_changdeok_garden); jongro2_number_changdeok_garden.setText("0");
        jongro2_number_changdeok_garden_i = Integer.parseInt(jongro2_number_changdeok_garden.getText().toString());


        adult_minus_changdeok_garden = (ImageView)findViewById(R.id.adult_minus_changdeok_garden);
        adult_plus_changdeok_garden = (ImageView)findViewById(R.id.adult_plus_changdeok_garden);
        jongro3_minus_changdeok_garden = (ImageView)findViewById(R.id.jongro3_minus_changdeok_garden);
        jongro3_plus_changdeok_garden = (ImageView)findViewById(R.id.jongro3_plus_changdeok_garden);

        adult_number_changdeok_garden = (TextView)findViewById(R.id.adult_number_changdeok_garden); adult_number_changdeok_garden.setText("0");
        adult_number_changdeok_garden_i = Integer.parseInt(adult_number_changdeok_garden.getText().toString());
        jongro3_number_changdeok_garden = (TextView)findViewById(R.id.jongro3_number_changdeok_garden); jongro3_number_changdeok_garden.setText("0");
        jongro3_number_changdeok_garden_i = Integer.parseInt(jongro3_number_changdeok_garden.getText().toString());


        old_minus_changdeok_garden = (ImageView)findViewById(R.id.old_minus_changdeok_garden);
        old_plus_changdeok_garden = (ImageView)findViewById(R.id.old_plus_changdeok_garden);
        jongro4_minus_changdeok_garden = (ImageView)findViewById(R.id.jongro4_minus_changdeok_garden);
        jongro4_plus_changdeok_garden = (ImageView)findViewById(R.id.jongro4_plus_changdeok_garden);

        old_number_changdeok_garden = (TextView)findViewById(R.id.old_number_changdeok_garden); old_number_changdeok_garden.setText("0");
        old_number_changdeok_garden_i = Integer.parseInt(old_number_changdeok_garden.getText().toString());
        jongro4_number_changdeok_garden = (TextView)findViewById(R.id.jongro4_number_changdeok_garden); jongro4_number_changdeok_garden.setText("0");
        jongro4_number_changdeok_garden_i = Integer.parseInt(jongro4_number_changdeok_garden.getText().toString());


        // 5. 결제하기 버튼
        payment_changdeok_normal = (RelativeLayout)findViewById(R.id.payment_changdeok_normal);

        // 1. 하단 메뉴 설정
        yesselect_palace_changdeok.bringToFront();

        noselect_garden_changdeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesselect_garden_changdeok.bringToFront();

                yesselect_garden_changdeok.setVisibility(View.VISIBLE);
                yesselect_palace_changdeok.setVisibility(View.GONE);
                noselect_palace_changdeok.setVisibility(View.VISIBLE);
            }
        });

        noselect_palace_changdeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesselect_palace_changdeok.bringToFront();

                yesselect_palace_changdeok.setVisibility(View.VISIBLE);
                yesselect_garden_changdeok.setVisibility(View.GONE);
                noselect_garden_changdeok.setVisibility(View.VISIBLE);
            }
        });

        // 2. 캘린더
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            calendar_changdeok.setFocusedMonthDateColor(getResources().getColor(R.color.changduk));
        }

        // +,- 버튼 동작

        // 1-1. 궁궐전각) 대인 (3000원)
        adult_minus_changdeok_palace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adult_number_changdeok_palace_i>0){
                    adult_number_changdeok_palace_i --;
                    adult_number_changdeok_palace.setText(String.valueOf(adult_number_changdeok_palace_i));
                }
            }
        });

       adult_plus_changdeok_palace.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               adult_number_changdeok_palace_i ++;
               adult_number_changdeok_palace.setText(String.valueOf(adult_number_changdeok_palace_i));
           }
       });


        // 1-2. 궁궐전각) 종로구민 50% 할인
        jongro_minus_changdeok_palace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jongro_number_changdeok_palace_i>0){
                    jongro_number_changdeok_palace_i --;
                    jongro_number_changdeok_palace.setText(String.valueOf(jongro_number_changdeok_palace_i));
                    ticket_jongro = 1;
                }
            }
        });
        jongro_plus_changdeok_palace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    jongro_number_changdeok_palace_i ++;
                    jongro_number_changdeok_palace.setText(String.valueOf(jongro_number_changdeok_palace_i));
                ticket_jongro = 1;
            }
        });


        // 2-1. 왕실정원) 청소년 (2500원)
        student1_minus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(student1_number_changdeok_garden_i>0){
                    student1_number_changdeok_garden_i --;
                    student1_number_changdeok_garden.setText(String.valueOf(student1_number_changdeok_garden_i));
                }
            }
        });

        student1_plus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    student1_number_changdeok_garden_i ++;
                    student1_number_changdeok_garden.setText(String.valueOf(student1_number_changdeok_garden_i));
                }
        });


        // 2-2. 왕실정원) 종로구민 50% 할인
        jongro1_minus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jongro1_number_changdeok_garden_i>0){
                    jongro1_number_changdeok_garden_i --;
                    jongro1_number_changdeok_garden.setText(String.valueOf(jongro1_number_changdeok_garden_i));
                    ticket_jongro = 1;
                }
            }
        });

        jongro1_plus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    jongro1_number_changdeok_garden_i ++;
                    jongro1_number_changdeok_garden.setText(String.valueOf(jongro1_number_changdeok_garden_i));
                ticket_jongro = 1;
            }
        });


        // 2-3. 왕실정원) 청년 (5000원)
        student2_minus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(student2_number_changdeok_garden_i>0){
                    student2_number_changdeok_garden_i --;
                    student2_number_changdeok_garden.setText(String.valueOf(student2_number_changdeok_garden_i));
                }
            }
        });
        student2_plus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    student2_number_changdeok_garden_i ++;
                    student2_number_changdeok_garden.setText(String.valueOf(student2_number_changdeok_garden_i));
            }
        });


        // 2-4. 왕실정원) 종로구민 50% 할인
        jongro2_minus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jongro2_number_changdeok_garden_i>0){
                    jongro2_number_changdeok_garden_i --;
                    jongro2_number_changdeok_garden.setText(String.valueOf(jongro2_number_changdeok_garden_i));
                    ticket_jongro = 1;
                }
            }
        });
        jongro2_plus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    jongro2_number_changdeok_garden_i ++;
                    jongro2_number_changdeok_garden.setText(String.valueOf(jongro2_number_changdeok_garden_i));
                ticket_jongro = 1;
            }
        });


        // 2-5. 왕실정원) 성인 (8000원)
        adult_minus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adult_number_changdeok_garden_i>0){
                    adult_number_changdeok_garden_i --;
                    adult_number_changdeok_garden.setText(String.valueOf(adult_number_changdeok_garden_i));
                }
            }
        });
        adult_plus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adult_number_changdeok_garden_i ++;
                adult_number_changdeok_garden.setText(String.valueOf(adult_number_changdeok_garden_i));
            }
        });


        // 2-6. 왕실정원) 종로구민 50% 할인
        jongro3_minus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jongro3_number_changdeok_garden_i>0){
                    jongro3_number_changdeok_garden_i --;
                    jongro3_number_changdeok_garden.setText(String.valueOf(jongro3_number_changdeok_garden_i));
                    ticket_jongro = 1;
                }
            }
        });
        jongro3_plus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jongro3_number_changdeok_garden_i ++;
                jongro3_number_changdeok_garden.setText(String.valueOf(jongro3_number_changdeok_garden_i));
                ticket_jongro = 1;
            }
        });


        // 2-7. 왕실정원) 경로 (5000원)
        old_minus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(old_number_changdeok_garden_i>0){
                    old_number_changdeok_garden_i --;
                    old_number_changdeok_garden.setText(String.valueOf(old_number_changdeok_garden_i));
                }
            }
        });
        old_plus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_number_changdeok_garden_i ++;
                old_number_changdeok_garden.setText(String.valueOf(old_number_changdeok_garden_i));
            }
        });


        // 2-8. 왕실정원) 종로구민 50% 할인
        jongro4_minus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jongro4_number_changdeok_garden_i>0){
                    jongro4_number_changdeok_garden_i --;
                    jongro4_number_changdeok_garden.setText(String.valueOf(jongro4_number_changdeok_garden_i));
                    ticket_jongro = 1;
                }
            }
        });
        jongro4_plus_changdeok_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jongro4_number_changdeok_garden_i ++;
                jongro4_number_changdeok_garden.setText(String.valueOf(jongro4_number_changdeok_garden_i));
                ticket_jongro = 1;
            }
        });



        /**************************** 예매 데이터 ****************************/

        calendar_changdeok.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.e("날짜", String.valueOf(year)+"년 "+String.valueOf(month)+"월 "+String.valueOf(dayOfMonth)+"일");
                r_year = year;
                r_month = month+1;
                r_day = dayOfMonth;
                ticket_start = ticket_end = r_year+"."+r_month+"."+r_day;
            }
        });


        // 결제하기 버튼 클릭
        payment_changdeok_normal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                palace_id = 1; // 창덕궁 아이디
                ticket_title = "창덕궁 일반권";
                ticket_special = 0; //특별권 구분

                Log.e("궁아이디) ",String.valueOf(palace_id));
                Log.e("티켓종류) ", ticket_title);
                Log.e("티켓 시작일) ", ticket_start);
                Log.e("티켓 종료일) ", ticket_end);

                /** 사람정보 **/
                if(adult_number_changdeok_palace_i!=0){
                    ticket_people+="궁궐전각 대인"+adult_number_changdeok_palace_i;
                }
                if(jongro_number_changdeok_palace_i!=0){
                    ticket_people+=" 궁궐전각 대인 (종로구민)"+adult_number_changdeok_palace_i;
                }
                if(student1_number_changdeok_garden_i!=0){
                    ticket_people+=" 왕실정원 청소년"+student1_number_changdeok_garden_i;
                }
                if(jongro1_number_changdeok_garden_i!=0){
                    ticket_people+=" 왕실정원 청소년 (종로구민)"+jongro1_number_changdeok_garden_i;
                }
                if(student2_number_changdeok_garden_i!=0){
                    ticket_people+=" 왕실정원 청년 "+student2_number_changdeok_garden_i;
                }
                if(jongro2_number_changdeok_garden_i!=0){
                    ticket_people+=" 왕실정원 청년 (종로구민)"+jongro2_number_changdeok_garden_i;
                }
                if(adult_number_changdeok_garden_i!=0){
                    ticket_people+=" 왕실정원 성인"+adult_number_changdeok_garden_i;
                }
                if(jongro3_number_changdeok_garden_i!=0){
                    ticket_people+=" 왕실정원 성인 (종로구민)"+jongro3_number_changdeok_garden_i;
                }
                if(old_number_changdeok_garden_i!=0){
                    ticket_people+=" 경로"+old_number_changdeok_garden_i;
                }
                if(jongro4_number_changdeok_garden_i!=0){
                    ticket_people+=" 경로 (종로구민)"+jongro4_number_changdeok_garden_i;
                }

                if(ticket_people.length()>=14){
                   ticket_people= ticket_people.substring(0,13)+"...";
                }

                Log.e("사람정보 )",ticket_people);
                Log.e("특별권 구분) ", String.valueOf(ticket_special));

                pay();  // 네트워킹

                Bootpay.init(getFragmentManager())
                        .setApplicationId("5baa746eb6d49c5a2452ee7f") // 해당 프로젝트(안드로이드)의 application id 값
                        .setPG(PG.KAKAO) // 결제할 PG 사
                        .setUserPhone("010-1234-5678") // 구매자 전화번호
                        .setMethod(Method.EASY) // 결제수단
                        .setName("창덕궁 일반 예매권") // 결제할 상품명
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
        });

    }
//    네트워킹
    public void pay() {
        Log.e("창덕궁 노멀 토큰: ",SharePreferenceController.getTokenHeader(getApplicationContext()));
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
