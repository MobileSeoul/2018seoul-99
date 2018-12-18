package com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace;

// 알아보기 탭 프래그먼트

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.MakeReservationFragment;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Changdeok;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Changgyong;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Duksu;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Gyeongbok;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Jongmyo;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.adapter.ShowingReviewsAdapter;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.data.PalaceData;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.viewpager_column_items.FirstColumn;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.viewpager_column_items.SecondColumn;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.viewpager_column_items.ThirdColumn;
import com.pm10.library.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class LookingPalaceFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator indicator;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    public LookingPalaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_looking_palace, container, false);
        FragmentManager fm = getFragmentManager();

//        뷰 초기화
        viewPager = (ViewPager)v.findViewById(R.id.viewpager_column_tourlooking);
        viewPager.setAdapter(new pagerAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(1);

        indicator=(CircleIndicator)v.findViewById(R.id.indicator_column_tourlooking);
        indicator.setupWithViewPager(viewPager);

//      < 후기 > 리사이클러뷰 부분

        mRecyclerView = v.findViewById(R.id.rcv_reviews_lookingpalace);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<PalaceData> palaceDataArrayList = new ArrayList<>();

        palaceDataArrayList.add(new PalaceData(R.drawable.gyeongbokgung_review_big,0));
        palaceDataArrayList.add(new PalaceData(R.drawable.changdeokgung_review_white,1));
        palaceDataArrayList.add(new PalaceData(R.drawable.changyeonggung_review_white,2));
        palaceDataArrayList.add(new PalaceData(R.drawable.deoksugung_review_white,3));
        palaceDataArrayList.add(new PalaceData(R.drawable.jongmyo_review_white,4));

        ShowingReviewsAdapter showingReviewsAdapter = new ShowingReviewsAdapter(palaceDataArrayList,v,fm);
        mRecyclerView.setAdapter(showingReviewsAdapter);


        return v;
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
                    FirstColumn fc = new FirstColumn();//칼럼1
                    return fc;
                case 1:
                    SecondColumn sc = new SecondColumn();//칼럼2
                    return sc;
                case 2:
                    ThirdColumn tc = new ThirdColumn(); //칼럼3
                    return tc;

            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
