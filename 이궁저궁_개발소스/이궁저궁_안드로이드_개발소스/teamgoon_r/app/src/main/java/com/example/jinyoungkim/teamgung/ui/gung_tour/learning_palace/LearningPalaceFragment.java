package com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace;

// 배워보기 탭 프래그먼트

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Duksu;
import com.example.jinyoungkim.teamgung.ui.gung_tour.going_palace.viewpager_going_items.GoingChangdeok;
import com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.fragments_learing.LearningChangdeok;
import com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.fragments_learing.LearningChanggyong;
import com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.fragments_learing.LearningDuksu;
import com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.fragments_learing.LearningGyeongbok;
import com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.fragments_learing.LearningJongmyo;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearningPalaceFragment extends Fragment {


    public LearningPalaceFragment() {
        // Required empty public constructor
    }

    TextView gyeongbok_btn;
    TextView changdeok_btn;
    TextView changgyong_btn;
    TextView duksu_btn;
    TextView jongmyo_btn;

    TextView title;

    FrameLayout frame;

    FragmentManager fm;
    FragmentTransaction transaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learning_palace, container, false);

        gyeongbok_btn = (TextView) view.findViewById(R.id.btn_gyeongbok_learning);
        changdeok_btn = (TextView) view.findViewById(R.id.btn_changdeok_learning);
        changgyong_btn = (TextView) view.findViewById(R.id.btn_changgyong_learning);
        duksu_btn = (TextView) view.findViewById(R.id.btn_duksu_learning);
        jongmyo_btn = (TextView) view.findViewById(R.id.btn_jongmyo_learning);



        gyeongbok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                transaction = fm.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.frame_learning_palace,new LearningGyeongbok());
                transaction.commit();


            }
        });

        changdeok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.frame_learning_palace,new LearningChangdeok());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        changgyong_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.frame_learning_palace,new LearningChanggyong());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        duksu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.frame_learning_palace,new LearningDuksu());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        jongmyo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.frame_learning_palace,new LearningJongmyo());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });


        return view;
    }


}
