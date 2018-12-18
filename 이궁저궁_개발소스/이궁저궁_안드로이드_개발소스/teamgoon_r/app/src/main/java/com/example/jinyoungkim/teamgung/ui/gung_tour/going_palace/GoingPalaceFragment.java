package com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.MakeReservationFragment;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Changdeok;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Changgyong;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Duksu;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Gyeongbok;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Jongmyo;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.viewpager_going_items.GoingChangdeok;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.viewpager_going_items.GoingChanggyong;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.viewpager_going_items.GoingDuksu;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.viewpager_going_items.GoingGyeongbok;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.viewpager_going_items.GoingJongmyo;
import com.kakao.util.helper.log.Tag;
import com.pm10.library.CircleIndicator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

// 다녀보기 탭 프래그먼트

public class GoingPalaceFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator indicator;

    private SlidingUpPanelLayout mLayout;


    GoingGyeongbok gyeongbok = new GoingGyeongbok();//경복궁
    GoingChanggyong changgyong = new GoingChanggyong();
    GoingDuksu duksu = new GoingDuksu();
    GoingChangdeok changdeok = new GoingChangdeok();
    GoingJongmyo jongmyo = new GoingJongmyo();

    public GoingPalaceFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_going_palace, container, false);

        //        뷰 초기화
        viewPager = (ViewPager)view.findViewById(R.id.viewpager_going_palace);
        // 슬라이딩패널 초기화
        mLayout = (SlidingUpPanelLayout)view.findViewById(R.id.sliding_layout);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });

        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });


        viewPager.setAdapter(new pagerAdapter(getFragmentManager()));
        viewPager.setPageMargin(60);
        viewPager.setCurrentItem(2);

        indicator=(CircleIndicator)view.findViewById(R.id.indicator_going_palace);
        indicator.setupWithViewPager(viewPager);


        return view;
    }
    //      뷰페이저를 위한 어댑터
    private class pagerAdapter extends FragmentStatePagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return gyeongbok;
                case 1:
                    return changgyong;
                case 2:
                    return duksu;
                case 3:
                    return changdeok;
                case 4:
                    return jongmyo; //종묘

            }

            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }




}
