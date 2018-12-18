package com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.viewpager_column_items;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.jinyoungkim.teamgung.R;

public class FirstColumn extends android.support.v4.app.Fragment {
    RelativeLayout first;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vpitem_column1, container, false);
//        https://m.post.naver.com/viewer/postView.nhn?volumeNo=16554087&memberNo=1974376

        first = (RelativeLayout)view.findViewById(R.id.first_column);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.post.naver.com/viewer/postView.nhn?volumeNo=16554087&memberNo=1974376"));
                startActivity(intent);
            }
        });


        return view;
    }
}
