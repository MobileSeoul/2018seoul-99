package com.example.jinyoungkim.teamgung.ui.gung_ticket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.app.AlertDialog;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.confirm_reservation.ConfirmReservationFragment;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.MakeReservationFragment;
import com.example.jinyoungkim.teamgung.ui.gung_tour.TourMainActivity;
import com.example.jinyoungkim.teamgung.ui.main.MainActivity;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.ankit.perfectdialog.EasyCustomDialog;
import org.ankit.perfectdialog.EasyCustomDialogListener;


// 예매하기, 예매 확인 메인 액티비티

public class TicketMainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView profile_ticket_main; // 프로필 사진

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Switch switch_ticket;
    private SlidingUpPanelLayout special_reservation_layout;
    private ImageView menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, menu9;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_main);

//        상태바 색상변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffb54141);
        }


//        뷰 초기화
        tabLayout = (TabLayout)findViewById(R.id.tab_ticket); // 예매확인/예매하기 탭
        viewPager = (ViewPager)findViewById(R.id.viewpager_ticket); // 뷰페이저
        switch_ticket = (Switch)findViewById(R.id.switch_ticket); // 화면전환 스위치
      //  special_reservation_layout= (LindearLayout)findViewById(R.id.dragView); // 특별관람 예매하기 하단 레이아웃
        //special_reservation_layout= (SlidingUpPanelLayout)findViewById(R.id.dragView); // 특별관람 예매하기 하단 레이아웃
        profile_ticket_main = (ImageView)findViewById(R.id.profile_ticket_main); // 프로필 사진

        //특별관람 메뉴
        menu1=(ImageView)findViewById(R.id.menu1);
        menu2=(ImageView)findViewById(R.id.menu2);
        menu3=(ImageView)findViewById(R.id.menu3);
        menu4=(ImageView)findViewById(R.id.menu4);
        menu5=(ImageView)findViewById(R.id.menu5);
        menu6=(ImageView)findViewById(R.id.menu6);
        menu7=(ImageView)findViewById(R.id.menu7);
        menu8=(ImageView)findViewById(R.id.menu8);
        menu9=(ImageView)findViewById(R.id.menu9);


        Log.e("프사: ",SharePreferenceController.getProfile(getApplicationContext()));
        // 프사
        if(SharePreferenceController.getProfile(getApplicationContext()).equals("")){
            Glide.with(this)
                    .load(R.drawable.kakao_default_profile_image)
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().circleCrop())
                    .into(profile_ticket_main);
        }else{
            Glide.with(this)
                    .load(SharePreferenceController.getProfile(getApplicationContext()))
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().circleCrop())
                    .into(profile_ticket_main);
        }


        // 로그아웃

        if(!SharePreferenceController.getLogin(getApplicationContext()).equals("")){
            profile_ticket_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new EasyCustomDialog.Builder(TicketMainActivity.this,"로그아웃")
                            .setHeader("로그아웃 하시겠습니까?")
                            .setNegativeBtnText("취소")
                            .setPositiveBtnText("확인")
                            .onConfirm(new EasyCustomDialogListener() {
                                @Override
                                public void execute() {
                                    logout();
                                    GlobalApplication.getGlobalApplicationContext().makeToast("로그아웃 되었습니다 :)");
                                    Glide.with(getApplicationContext())
                                            .load(R.drawable.kakao_default_profile_image)
                                            .apply(new RequestOptions().centerCrop())
                                            .apply(new RequestOptions().circleCrop())
                                            .into(profile_ticket_main);
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            }).setIcon(getResources().getDrawable(R.drawable.nasi))
                            .build();
                }
            });

        }

//        스위치
        switch_ticket.setChecked(false);
        switch_ticket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startActivity(new Intent(getApplicationContext(), TourMainActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                }
            }
        });


//        탭 레이아웃 초기화

        tabLayout.addTab(tabLayout.newTab().setText("예매하기"));
        tabLayout.addTab(tabLayout.newTab().setText("예매확인"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(R.color.background,Color.BLACK);

//        탭 어댑터
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if(SharePreferenceController.getDataChange(getApplicationContext())==0){
            viewPager.setCurrentItem(1);
        } else if (SharePreferenceController.getDataChange(getApplicationContext())== 1) {

            viewPager.setCurrentItem(0);
        }

//        탭 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /************* 특별관람 예매 *************/
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        menu4.setOnClickListener(this);
        menu5.setOnClickListener(this);
        menu6.setOnClickListener(this);
        menu7.setOnClickListener(this);
        menu8.setOnClickListener(this);

        // 경복궁 특별관람

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SpecialLookingActivity.class));
            }
        });


    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "서비스 준비중입니다 :)", Toast.LENGTH_SHORT).show();
    }

//  탭 리스너 추가 하기

    /************* 탭 레이아웃 어댑터 클래스 *************/
    public class TabPagerAdapter extends FragmentStatePagerAdapter {

        private int tabCount; //탭 개수

        public TabPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    //special_reservation_layout.setVisibility(View.VISIBLE);
                    MakeReservationFragment makeReservationFragment = new MakeReservationFragment();
                    return makeReservationFragment; //예매 하기 프래그먼트

                case 1:
                    //special_reservation_layout.setVisibility(View.GONE);
                    ConfirmReservationFragment confirmReservationFragment = new ConfirmReservationFragment();
                    return  confirmReservationFragment; //예매 확인하기 프래그먼트

                default:
                    return null;
            }

    }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

    private void logout(){
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.e("로그아웃","로그아웃");
                SharePreferenceController.deleteTokenHeader(getApplicationContext());
                SharePreferenceController.setLogin(getApplicationContext(),""); // 로그인 삭제
                SharePreferenceController.setProfile(getApplicationContext(),""); //프사 삭제
                SharePreferenceController.setTokenHeader(getApplicationContext(),""); //헤더 삭제
            }
        });
    }
}
