package com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.viewpager_column_items;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.jinyoungkim.teamgung.R;

public class ThirdColumn extends Fragment {
    RelativeLayout third;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vpitem_column3,container,false);
       third = (RelativeLayout)view.findViewById(R.id.third_column);
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.post.naver.com/viewer/postView.nhn?volumeNo=16647424&memberNo=11556787"));
                startActivity(intent);
            }
        });

        return view;
    }
}
