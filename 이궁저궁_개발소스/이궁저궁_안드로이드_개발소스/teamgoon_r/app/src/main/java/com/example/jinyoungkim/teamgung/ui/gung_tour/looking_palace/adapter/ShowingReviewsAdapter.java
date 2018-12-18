package com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Changdeok;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Changgyong;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Duksu;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Gyeongbok;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items.Jongmyo;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.data.PalaceData;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.review_fragment.ReivewDuksu;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.review_fragment.ReviewChangdeok;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.review_fragment.ReviewChanggyong;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.review_fragment.ReviewGyeongbok;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.review_fragment.ReviewJongmyo;

import java.util.ArrayList;

public class ShowingReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class ShowingReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView palace;
        public ShowingReviewViewHolder(View itemView) {
            super(itemView);
            palace = itemView.findViewById(R.id.imgitem_looking_palace);
        }
    }
    private ArrayList<PalaceData> palaceArrayList;
    private View view;
    public android.support.v4.app.FragmentManager fm;
    public android.support.v4.app.FragmentTransaction transaction;



    public ShowingReviewsAdapter(ArrayList<PalaceData> palaceArrayList, View view, android.support.v4.app.FragmentManager fm){
        this.palaceArrayList = palaceArrayList;
        this.view = view;
        this.fm = fm;
        this.transaction = fm.beginTransaction();
        transaction.add(R.id.frame_review_lookingpalace,new ReviewGyeongbok());
        transaction.addToBackStack(null);

        transaction.commit();





    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_reviews_tourlooking,parent,false);

        return new ShowingReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {



        final ShowingReviewViewHolder showingReviewViewHolder = (ShowingReviewViewHolder) holder;

        showingReviewViewHolder.palace.setImageResource(palaceArrayList.get(position).drawableID);
        showingReviewViewHolder.palace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        switch (position){
                            case 0 :
                                palaceArrayList.set(0,new PalaceData(R.drawable.gyeongbokgung_review_big,1));

                                palaceArrayList.set(1,new PalaceData(R.drawable.changdeokgung_review_white,0));
                                palaceArrayList.set(2,new PalaceData(R.drawable.changyeonggung_review_white,0));
                                palaceArrayList.set(3,new PalaceData(R.drawable.deoksugung_review_white,0));
                                palaceArrayList.set(4,new PalaceData(R.drawable.jongmyo_review_white,0));
                                replaceFragment(new ReviewGyeongbok());


                                break;
                            case 1:
                                 palaceArrayList.set(1,new PalaceData(R.drawable.changdeokgung_review_big,1));

                                palaceArrayList.set(0,new PalaceData(R.drawable.gyeongbokgung_review_white,0));
                                palaceArrayList.set(2,new PalaceData(R.drawable.changyeonggung_review_white,0));
                                palaceArrayList.set(3,new PalaceData(R.drawable.deoksugung_review_white,0));
                                palaceArrayList.set(4,new PalaceData(R.drawable.jongmyo_review_white,0));
                                replaceFragment(new ReviewChangdeok());
                                 break;
                            case 2:
                                palaceArrayList.set(2,new PalaceData(R.drawable.changyeonggung_review_big,1));

                                palaceArrayList.set(0,new PalaceData(R.drawable.gyeongbokgung_review_white,0));
                                palaceArrayList.set(1,new PalaceData(R.drawable.changdeokgung_review_white,0));
                                palaceArrayList.set(3,new PalaceData(R.drawable.deoksugung_review_white,0));
                                palaceArrayList.set(4,new PalaceData(R.drawable.jongmyo_review_white,0));

                                replaceFragment(new ReviewChanggyong());


                                break;
                            case 3:
                                palaceArrayList.set(3,new PalaceData(R.drawable.deoksugung_review_big,1));

                                palaceArrayList.set(0,new PalaceData(R.drawable.gyeongbokgung_review_white,0));
                                palaceArrayList.set(1,new PalaceData(R.drawable.changdeokgung_review_white,0));
                                palaceArrayList.set(2,new PalaceData(R.drawable.changyeonggung_review_white,0));
                                palaceArrayList.set(4,new PalaceData(R.drawable.jongmyo_review_white,0));

                                replaceFragment(new ReivewDuksu());

                                break;
                            case 4:
                                palaceArrayList.set(4,new PalaceData(R.drawable.jongmyo_review_big,1));

                                palaceArrayList.set(0,new PalaceData(R.drawable.gyeongbokgung_review_white,0));
                                palaceArrayList.set(1,new PalaceData(R.drawable.changdeokgung_review_white,0));
                                palaceArrayList.set(2,new PalaceData(R.drawable.changyeonggung_review_white,0));
                                palaceArrayList.set(3,new PalaceData(R.drawable.deoksugung_review_white,0));

                                replaceFragment(new ReviewJongmyo());

                                break;

                }
                notifyDataSetChanged();

            }
        });


    }

    @Override
    public int getItemCount() {
        return palaceArrayList.size();
    }

    public void replaceFragment(Fragment fragment) {

        transaction = fm.beginTransaction();

        transaction.replace(R.id.frame_review_lookingpalace,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
