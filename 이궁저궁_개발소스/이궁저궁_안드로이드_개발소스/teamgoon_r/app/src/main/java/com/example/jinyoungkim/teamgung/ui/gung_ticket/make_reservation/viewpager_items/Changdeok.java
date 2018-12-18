package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.booking.BookingChangdeokActivity;
import com.example.jinyoungkim.teamgung.util.SessionCallback;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

public class Changdeok extends Fragment {

    private FrameLayout goto_reservation;

    private String userName;
    private String profileUrl;
    private SessionCallback sessionCallback;

    public Changdeok() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vpitem_changdeok, container, false);
//        초기화
        goto_reservation = (FrameLayout)view.findViewById(R.id.changdeok);


//        여기 추후에 로그인 여부에 따라 넘어가는 화면 바꿔야댐
        goto_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                Intent i = new Intent(getContext(),LoadingActivity.class);
                i.putExtra("fragment_type","changdeok");
                startActivity(i);
                getActivity().finish();

            }
        });

        return view;
    }


}
