package com.example.jinyoungkim.teamgung.ui.gung_ticket.confirm_reservation.adapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.model.ConfirmTicketData;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.confirm_reservation.data.TicketData;
import com.example.jinyoungkim.teamgung.ui.gung_ticket.confirm_reservation.review_write_dialog.ReviewWriteDialog;
import com.example.jinyoungkim.teamgung.ui.gung_tour.looking_palace.data.PalaceData;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.ChangdeokDialog;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.ChanggyeongDialog;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.DuksuDialog;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.GyeongbokDialog;
import com.example.jinyoungkim.teamgung.ui.main.foreign_dialog.JongmyoDialog;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;

import java.util.ArrayList;

public class ReservationConfirmAdapter extends RecyclerView.Adapter {

    public static class ReservationConfirmViewHolder extends RecyclerView.ViewHolder{

        LinearLayout ticket_bgr;
        TextView review_btn;

        TextView ticket_title;
        TextView ticket_people;
        TextView ticket_date;



        ImageView review_fin;//후기 작성 끝냈을 때
        public ReservationConfirmViewHolder(View itemView) {
                super(itemView);
                ticket_bgr = itemView.findViewById(R.id.img_ticket_confirm_reservation);
                review_btn = itemView.findViewById(R.id.btn_review_confirm_reservation);
                review_fin = itemView.findViewById(R.id.ticket_fin);

                ticket_title = itemView.findViewById(R.id.ticket_title);
                ticket_people = itemView.findViewById(R.id.ticket_people);
                ticket_date = itemView.findViewById(R.id.ticket_date);
        }
    }
    private ArrayList<ConfirmTicketData> confirmTicketData;
    public View view;
    public ReservationConfirmAdapter(ArrayList<ConfirmTicketData> confirmTicketData, View view){
        this.confirmTicketData = confirmTicketData;
        this.view = view;
    }
    public int update_flag=0;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_confirm_ticket,parent,false);

        return new ReservationConfirmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

      if(SharePreferenceController.getDataChange(view.getContext())==1){
//          notifyDataSetChanged();
          SharePreferenceController.setDataChange(view.getContext(),0);
      }


        final ReservationConfirmViewHolder reservationConfirmViewHolder = (ReservationConfirmViewHolder) holder;

        int ticket_flag = confirmTicketData.get(position).ticket_flag; //0:일반 1:선착 2:특별
        int review_flag = confirmTicketData.get(position).ticket_review;//1이면 리뷰작성함
        int finish_flag = confirmTicketData.get(position).end_flag;// 1이면끝난예약

        String date = confirmTicketData.get(position).ticket_start+" - "+confirmTicketData.get(position).ticket_end;

        reservationConfirmViewHolder.ticket_title.setText(confirmTicketData.get(position).ticket_title);
        reservationConfirmViewHolder.ticket_people.setText(confirmTicketData.get(position).ticket_people);
        reservationConfirmViewHolder.ticket_date.setText(date);

        switch (ticket_flag){
            case 0://일반 - yellow
                reservationConfirmViewHolder.ticket_bgr.setBackgroundResource(R.drawable.ticket_normal_yellow);
                if(finish_flag==1){ //끝난 예약
                    if(review_flag==1) {//리뷰작성을 했음
                        reservationConfirmViewHolder.review_fin.setVisibility(View.VISIBLE); //작성완료버튼 활성화

                        reservationConfirmViewHolder.review_btn.setVisibility(View.INVISIBLE); //작성버튼 비활성화
                    }
                    else { //작성안함
                        reservationConfirmViewHolder.review_fin.setVisibility(View.INVISIBLE); //작성완료버튼 비활성화

                        reservationConfirmViewHolder.review_btn.setVisibility(View.VISIBLE); //작성버튼 활성화
                        reservationConfirmViewHolder.review_btn.setBackgroundResource(R.drawable.ticket_yellow_review_button); //작성버튼 셋팅
                    }
                }else{ //다가오는 예약
                    reservationConfirmViewHolder.review_btn.setVisibility(View.INVISIBLE);
                    reservationConfirmViewHolder.review_fin.setVisibility(View.INVISIBLE);
                }
                break;


            case 1://선착 - blue
                reservationConfirmViewHolder.ticket_bgr.setBackgroundResource(R.drawable.ticket_first_blue);
                if(finish_flag==1){ //끝난 예약
                    if(review_flag==1) {//리뷰작성을 했음
                        reservationConfirmViewHolder.review_fin.setVisibility(View.VISIBLE); //작성완료버튼 활성화

                        reservationConfirmViewHolder.review_btn.setVisibility(View.INVISIBLE); //작성버튼 비활성화
                    }
                    else { //작성안함
                        reservationConfirmViewHolder.review_fin.setVisibility(View.INVISIBLE); //작성완료버튼 비활성화

                        reservationConfirmViewHolder.review_btn.setVisibility(View.VISIBLE); //작성버튼 활성화
                        reservationConfirmViewHolder.review_btn.setBackgroundResource(R.drawable.ticket_blue_review_button); //작성버튼 셋팅
                    }
                }else{ //다가오는 예약
                    reservationConfirmViewHolder.review_btn.setVisibility(View.INVISIBLE);
                    reservationConfirmViewHolder.review_fin.setVisibility(View.INVISIBLE);
                }
                break;
            case 2://특별 - light green
                reservationConfirmViewHolder.ticket_bgr.setBackgroundResource(R.drawable.ticket_special_lightgreen);
                if(finish_flag==1){ //끝난 예약
                    if(review_flag==1) {//리뷰작성을 했음
                        reservationConfirmViewHolder.review_fin.setVisibility(View.VISIBLE); //작성완료버튼 활성화

                        reservationConfirmViewHolder.review_btn.setVisibility(View.INVISIBLE); //작성버튼 비활성화
                    }
                    else { //작성안함
                        reservationConfirmViewHolder.review_fin.setVisibility(View.INVISIBLE); //작성완료버튼 비활성화

                        reservationConfirmViewHolder.review_btn.setVisibility(View.VISIBLE); //작성버튼 활성화
                        reservationConfirmViewHolder.review_btn.setBackgroundResource(R.drawable.ticket_lightgreen_review_button); //작성버튼 셋팅
                    }
                }else{ //다가오는 예약
                    reservationConfirmViewHolder.review_btn.setVisibility(View.INVISIBLE);
                    reservationConfirmViewHolder.review_fin.setVisibility(View.INVISIBLE);
                }
                break;


        }
        final ReviewWriteDialog rwd = new ReviewWriteDialog(view.getContext(),reservationConfirmViewHolder.review_btn, reservationConfirmViewHolder.review_fin);

        WindowManager.LayoutParams wm = rwd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(rwd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미

        if(review_flag==0){ //후기작성 버튼 활성화 시 눌렀을때 -> 얼굴모양 선택 다이얼로그 띄우기
            reservationConfirmViewHolder.review_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharePreferenceController.setPalaceID(view.getContext(),confirmTicketData.get(position).palace_name);
                    SharePreferenceController.setTicketID(view.getContext(),confirmTicketData.get(position).ticket_id);
                    rwd.show();

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return confirmTicketData.size();
    }
}
