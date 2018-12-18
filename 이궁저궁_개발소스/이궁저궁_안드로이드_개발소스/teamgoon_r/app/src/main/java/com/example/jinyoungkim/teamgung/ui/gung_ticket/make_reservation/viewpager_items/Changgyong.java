package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.model.UserInfoPost;
import com.example.jinyoungkim.teamgung.network.NetworkService;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking.BookingChangdeokActivity;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking.BookingChanggyeongActivity;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SessionCallback;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

public class Changgyong extends Fragment {

    private FrameLayout goto_reservation;


    public Changgyong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.vpitem_changgyong, container, false);

        goto_reservation = (FrameLayout)v.findViewById(R.id.changgyeong);

        //        여기 추후에 로그인 여부에 따라 넘어가는 화면 바꿔야댐
        goto_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(),LoadingActivity.class);
                i.putExtra("fragment_type","changgyeong");
                startActivity(i);
                getActivity().finish();

            }
        });

        return v;
    }


}


