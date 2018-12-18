package com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.review_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.model.AvarageReview;
import com.example.jinyoungkim.teamgung.model.AvarageReviewData;
import com.example.jinyoungkim.teamgung.network.NetworkService;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewChangdeok extends Fragment {
    NetworkService networkService;

    TextView total;

    ImageView traffic;
    ImageView congestion;
    ImageView see;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_changdeok,container,false);
        networkService = GlobalApplication.getGlobalApplicationContext().getNetworkService();

        total=(TextView)view.findViewById(R.id.avgrate_review_changdeok);

        traffic = (ImageView)view.findViewById(R.id.rateface_traffic_review_changdeok);
        congestion = (ImageView)view.findViewById(R.id.rateface_congestion_review_changdeok);
        see = (ImageView)view.findViewById(R.id.rateface_see_review_changdeok);


        Call<AvarageReview>avarageReviewCall = networkService.avarage(SharePreferenceController.getTokenHeader(getContext()),
                1);

        avarageReviewCall.enqueue(new Callback<AvarageReview>() {
            @Override
            public void onResponse(Call<AvarageReview> call, Response<AvarageReview> response) {
                if(response.isSuccessful()){
                    AvarageReviewData avarageReviewData = response.body().result;

                    total.setText(avarageReviewData.total);

                    float traffic_n = avarageReviewData.traffic;
                    float congestion_n = avarageReviewData.crowd;
                    float see_n = avarageReviewData.attraction;

                    if(traffic_n == 0){
                        traffic.setImageResource(R.drawable.confirm_reservation_review_bad_select);
                    }else if(traffic_n == 5){
                        traffic.setImageResource(R.drawable.confirm_reservation_review_soso_select);
                    }else if(traffic_n == 10){
                        traffic.setImageResource(R.drawable.confirm_reservation_review_good_select);
                    }

                    if(congestion_n == 0){
                        congestion.setImageResource(R.drawable.confirm_reservation_review_bad_select);
                    }else if(traffic_n == 5){
                        congestion.setImageResource(R.drawable.confirm_reservation_review_soso_select);
                    }else if(traffic_n == 10){
                        congestion.setImageResource(R.drawable.confirm_reservation_review_good_select);
                    }

                    if(see_n == 0){
                        see.setImageResource(R.drawable.confirm_reservation_review_bad_select);
                    }else if(see_n == 5){
                        see.setImageResource(R.drawable.confirm_reservation_review_soso_select);
                    }else if(see_n == 10){
                        see.setImageResource(R.drawable.confirm_reservation_review_good_select);
                    }

                }
            }

            @Override
            public void onFailure(Call<AvarageReview> call, Throwable t) {

            }
        });

        return view;
    }
}
