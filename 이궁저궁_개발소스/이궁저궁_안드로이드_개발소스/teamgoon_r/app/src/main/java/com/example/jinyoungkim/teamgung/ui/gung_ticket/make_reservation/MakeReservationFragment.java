package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Changdeok;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Changgyong;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Duksu;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Gyeongbok;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Jongmyo;
import com.pm10.library.CircleIndicator;


// 예매 하기 탭 프래그먼트

public class MakeReservationFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator indicator;

    public MakeReservationFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_make_reservation, container, false);


//        뷰 초기화
        viewPager = (ViewPager)view.findViewById(R.id.viewpager_make_ticket);
        viewPager.setAdapter(new pagerAdapter(getFragmentManager()));
        viewPager.setPageMargin(60);
        viewPager.setCurrentItem(2);

        indicator=(CircleIndicator)view.findViewById(R.id.indicator_make_reservation);
        indicator.setupWithViewPager(viewPager);

        return view;
    }

//      뷰페이저를 위한 어댑터
    private class pagerAdapter extends FragmentStatePagerAdapter{

    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Gyeongbok gyeongbok = new Gyeongbok(); //경복궁
                return gyeongbok;
            case 1:
                Changgyong changgyong = new Changgyong(); //창경궁
                return changgyong;
            case 2:
                Duksu duksu = new Duksu();
                return duksu; //덕수궁
            case 3:
                Changdeok changdeok = new Changdeok(); //창덕궁
                return changdeok;
            case 4:
                Jongmyo jongmyo = new Jongmyo();
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
