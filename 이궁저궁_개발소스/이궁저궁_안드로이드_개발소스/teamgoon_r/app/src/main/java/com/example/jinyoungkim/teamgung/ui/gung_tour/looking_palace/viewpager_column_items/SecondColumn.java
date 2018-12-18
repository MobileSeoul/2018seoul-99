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

public class SecondColumn extends android.support.v4.app.Fragment {
    RelativeLayout second;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vpitem_column2, container, false);
        second = (RelativeLayout)view.findViewById(R.id.second_column);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.post.naver.com/viewer/postView.nhn?volumeNo=16521829&memberNo=4328593"));
                startActivity(intent);
            }
        });

        return view;
    }
}
