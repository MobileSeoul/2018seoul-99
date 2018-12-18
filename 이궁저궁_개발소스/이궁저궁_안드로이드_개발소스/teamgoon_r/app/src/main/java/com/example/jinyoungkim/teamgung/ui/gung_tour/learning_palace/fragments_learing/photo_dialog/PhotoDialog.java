package com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.fragments_learing.photo_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;

public class PhotoDialog extends Dialog {

    ImageView img;

    public PhotoDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.dialog_expand_photo);     //다이얼로그에서 사용할 레이아웃입니다.


        img = (ImageView)findViewById(R.id.expand_review_img);

        Glide.with(this.getContext())
                .load(SharePreferenceController.getPhotoURL(this.getContext()))
                .into(img);
    }


}
