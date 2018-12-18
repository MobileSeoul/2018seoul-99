package com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.viewpager_going_items;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.GoingChangdeokDialog;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.GoingChanggyonDialog;

public class GoingChanggyong extends Fragment {
    public View view;
    ImageView changgyong;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vpitem_going_changgyong,container,false);
        DisplayMetrics dm = view.getContext().getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels; //디바이스 화면 높이

        changgyong = (ImageView)view.findViewById(R.id.img_changgyong_goingpalace);

        changgyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                GoingChanggyonDialog dialogFragment = new GoingChanggyonDialog();

                dialogFragment.show(fm,"dd");
            }
        });
        return view;
    }
}
