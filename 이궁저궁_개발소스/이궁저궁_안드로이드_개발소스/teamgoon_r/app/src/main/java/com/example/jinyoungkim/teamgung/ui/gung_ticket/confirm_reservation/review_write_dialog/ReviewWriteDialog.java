package com.example.jinyoungkim.teamgung.ui.gung_ticket.confirm_reservation.review_write_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.model.ReviewWrite;
import com.example.jinyoungkim.teamgung.model.ReviewWriteResult;
import com.example.jinyoungkim.teamgung.network.NetworkService;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewWriteDialog extends Dialog {
        ImageView good_tr;
        ImageView good_con ;
        ImageView good_see ;

        ImageView soso_tr;
        ImageView soso_con ;
        ImageView soso_see  ;

        ImageView bad_tr ;
        ImageView bad_con;
        ImageView bad_see;

        float traffic_score;
        float congestion_score;
        float see_score;

        int traffic_flag;
        int congestion_flag;
        int see_flag;

        //네트워킹
    NetworkService networkService;
    ReviewWrite reviewWrite;

    public ReviewWriteDialog(@NonNull final Context context,final View btn,final View btn2) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.dialog_review_write);     //다이얼로그에서 사용할 레이아웃입니다.

        //네트워킹
        networkService = GlobalApplication.getGlobalApplicationContext().getNetworkService();

        TextView finish = (TextView)findViewById(R.id.btn_finish_review);


        good_tr = (ImageView)findViewById(R.id.face_good_traffic);
        good_con = (ImageView)findViewById(R.id.face_good_congestion);
        good_see = (ImageView)findViewById(R.id.face_good_see);

        soso_tr = (ImageView)findViewById( R.id.face_soso_traffic);
        soso_con = (ImageView)findViewById(R.id.face_soso_congestion);
        soso_see = (ImageView)findViewById(R.id.face_soso_see);

        bad_tr = (ImageView)findViewById(R.id.face_bad_traffic);
        bad_con = (ImageView)findViewById(R.id.face_bad_congestion);
        bad_see = (ImageView)findViewById(R.id.face_bad_see);


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(traffic_flag==0||congestion_flag==0||see_flag==0){
                    Toast.makeText(context.getApplicationContext(),"모든 항목을 평가해주세요 :)",Toast.LENGTH_SHORT).show();
                }else{
                    btn.setVisibility(View.INVISIBLE);
                    btn2.setVisibility(View.VISIBLE);

                    int palace_id = SharePreferenceController.getPalaceId(context);
                    int ticket_id = SharePreferenceController.getTicketID(context);
                    reviewWrite = new ReviewWrite(palace_id,traffic_score,congestion_score,see_score,ticket_id);



                    Call<ReviewWriteResult>reviewWriteResultCall = networkService.review(SharePreferenceController.getTokenHeader(getContext())
                            ,reviewWrite);
                    reviewWriteResultCall.enqueue(new Callback<ReviewWriteResult>() {
                        @Override
                        public void onResponse(Call<ReviewWriteResult> call, Response<ReviewWriteResult> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(context.getApplicationContext(),"후기를 등록했습니다 :)",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReviewWriteResult> call, Throwable t) {

                        }
                    });

                    dismiss();
                }

            }
        });


        good_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_traffic();
                if(good_tr.isSelected()==false){
                    traffic_score = 10;
                    traffic_flag += 1;
                    good_tr.setSelected(true);
                }else{
                    traffic_score = 0;
                    traffic_flag -= 1;
                    good_tr.setSelected(false);
                }

            }
        });
        good_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_congestion();
                if(good_con.isSelected()==false){
                    congestion_score = 10;
                    congestion_flag += 1;
                    good_con.setSelected(true);
                }else{
                    congestion_score = 0;
                    congestion_flag -= 1;
                    good_con.setSelected(false);
                }

            }
        });
        good_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_see();
                if(good_see.isSelected()==false){
                    see_score = 10;
                    see_flag += 1;
                    good_see.setSelected(true);
                }else{
                    see_score = 0;
                    see_flag -= 1;
                    good_see.setSelected(false);
                }

            }
        });
       soso_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_traffic();
                if(soso_tr.isSelected()==false){
                    traffic_score = 5;
                    traffic_flag += 1;

                    soso_tr.setSelected(true);
                }else{
                    traffic_score = 0;
                    traffic_flag -= 1;
                    soso_tr.setSelected(false);
                }

            }
        });
        soso_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_congestion();
                if(soso_con.isSelected()==false){
                    congestion_score = 5;
                    congestion_flag += 1;
                    soso_con.setSelected(true);
                }else{
                    congestion_score = 0;
                    congestion_flag -= 1;
                   soso_con.setSelected(false);
                }

            }
        });
        soso_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_see();
                if(soso_see.isSelected()==false){
                    see_score = 5;
                    see_flag += 1;
                    soso_see.setSelected(true);
                }else{
                    see_score = 0;
                    see_flag -= 1;
                   soso_see.setSelected(false);
                }

            }
        });
        bad_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_traffic();
                if(bad_tr.isSelected()==false){
                    traffic_score = 0;
                    traffic_flag += 1;
                   bad_tr.setSelected(true);
                }else{
                    traffic_score = 0;
                    traffic_flag -= 1;
                   bad_tr.setSelected(false);
                }

            }
        });
        bad_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_congestion();
                if(bad_con.isSelected()==false){
                    congestion_score = 0;
                    congestion_flag += 1;
                   bad_con.setSelected(true);
                }else{
                    congestion_score = 0;
                    congestion_flag -= 1;
                   bad_con.setSelected(false);
                }

            }
        });
        bad_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelected_see();
                if(bad_see.isSelected()==false){
                    see_score = 0;
                    see_flag += 1;
                    bad_see.setSelected(true);
                }else{
                    see_score = 0;
                    see_flag -= 1;
                    bad_see.setSelected(false);
                }

            }
        });



    }

    public void clearSelected_traffic(){
        traffic_score = 0;
        good_tr.setSelected(false);
       soso_tr.setSelected(false);
        bad_tr.setSelected(false);
    }
    public void clearSelected_congestion(){
       congestion_score = 0;
        good_con.setSelected(false);
        soso_con.setSelected(false);
        bad_con.setSelected(false);
    }
    public void clearSelected_see(){
        see_score = 0;
        good_see .setSelected(false);
        soso_see.setSelected(false);
        bad_see.setSelected(false);

    }
}