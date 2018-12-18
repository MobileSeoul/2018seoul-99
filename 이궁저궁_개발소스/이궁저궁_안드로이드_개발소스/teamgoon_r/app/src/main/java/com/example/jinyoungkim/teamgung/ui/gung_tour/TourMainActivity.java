package com.example.jinyoungkim.teamgung.ui.gung_tour;

import android.content.Intent;
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
import android.widget.Switch;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.TicketMainActivity;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.GoingPalaceFragment;
import com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.LearningPalaceFragment;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.LookingPalaceFragment;
import com.example.jinyoungkim.teamgung.ui.main.MainActivity;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.ankit.perfectdialog.EasyCustomDialog;
import org.ankit.perfectdialog.EasyCustomDialogListener;

// 궁궐 정보, 궁궐 투어 메인 액티비티

public class TourMainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Switch switch_tour;
    private ImageView profile_tour_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_main);

//        뷰 초기화                                                    
        tabLayout = (TabLayout)findViewById(R.id.tab_tour);
        viewPager = (ViewPager)findViewById(R.id.viewpager_tour);
        switch_tour = (Switch)findViewById(R.id.switch_tour);
        profile_tour_main = (ImageView)findViewById(R.id.profile_tour_main);

        Glide.with(this)
                .load(R.drawable.kakao_default_profile_image)
                .apply(new RequestOptions().centerCrop())
                .apply(new RequestOptions().circleCrop())
                .into(profile_tour_main);


        //로그아웃
        if(!SharePreferenceController.getLogin(getApplicationContext()).equals("")){
            profile_tour_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new EasyCustomDialog.Builder(TourMainActivity.this,"로그아웃")
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
                                            .into(profile_tour_main);
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();

                                }
                            }).setIcon(getResources().getDrawable(R.drawable.nasi))
                            .build();
                }
            });

        }


//        스위치
        switch_tour.setChecked(true);
        switch_tour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    startActivity(new Intent(getApplicationContext(), TicketMainActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                }
            }
        });

//        탭 레이아웃 초기화
        tabLayout.addTab(tabLayout.newTab().setText("알아보기"));
        tabLayout.addTab(tabLayout.newTab().setText("다녀보기"));
        tabLayout.addTab(tabLayout.newTab().setText("배워보기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        if(SharePreferenceController.getProfile(getApplicationContext()).equals("")){
            Glide.with(this)
                    .load(R.drawable.kakao_default_profile_image)
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().circleCrop())
                    .into(profile_tour_main);
        }else{
            Glide.with(this)
                    .load(SharePreferenceController.getProfile(getApplicationContext()))
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().circleCrop())
                    .into(profile_tour_main);
        }

//        탭 어댑터
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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


    }

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
                    LookingPalaceFragment lookingPalaceFragment = new LookingPalaceFragment();
                    return lookingPalaceFragment; //알아보기 탭
                case 1:
                    GoingPalaceFragment goingPalaceFragment = new GoingPalaceFragment();
                    return goingPalaceFragment; //다녀보기 탭

                case 2:
                    LearningPalaceFragment learningPalaceFragment = new LearningPalaceFragment();
                    return learningPalaceFragment; //배워보기 탭

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
