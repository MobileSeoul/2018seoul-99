package com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.vp_changdeok.ChangdeokKing;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.vp_changdeok.ChangdeokNasi;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.vp_changdeok.ChangdeokPrincess;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.vp_duksu.DuksuKing;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.vp_duksu.DuksuNasi;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.vp_duksu.DuksuPrincess;

public class GoingDuksuDialog extends DialogFragment {
    ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_going_duksu,container,false);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        ImageView close = (ImageView)view.findViewById(R.id.btn_close_dialog_duksu);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        viewPager = (ViewPager)view.findViewById(R.id.vp_duksu_swipe);
//
        viewPager.setAdapter(new pagerAdapter(fm));
        viewPager.setCurrentItem(0);
        return view;
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    //    뷰페이저를 위한 어댑터
    private class pagerAdapter extends FragmentStatePagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    DuksuKing cdn = new DuksuKing();
                    return cdn;
                case 1:
                    DuksuPrincess cdk = new DuksuPrincess();
                    return cdk;
                case 2:
                    DuksuNasi cdp = new DuksuNasi();
                    return cdp;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}