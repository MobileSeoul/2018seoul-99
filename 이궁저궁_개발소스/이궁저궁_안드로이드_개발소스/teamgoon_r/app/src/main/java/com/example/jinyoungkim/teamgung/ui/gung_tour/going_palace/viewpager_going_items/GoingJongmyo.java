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
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.GoingChanggyonDialog;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.GoingGyeongbokDialog;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.going_dialog.GoingJongmyoDialog;

public class GoingJongmyo extends Fragment {
    public View view;
    ImageView jongmyo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vpitem_going_jongmyo,container,false);

        jongmyo = (ImageView)view.findViewById(R.id.img_jongmyo_goingpalace);

        jongmyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                GoingJongmyoDialog dialogFragment = new GoingJongmyoDialog();

                dialogFragment.show(fm,"dd");
            }
        });
        return view;
    }
}
