package com.example.jinyoungkim.teamgung.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

public class SessionCallback implements ISessionCallback {

    public String token; //서버로 넘겨줄 토큰
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

   public SessionCallback(Context context){
       this.context = context;
   }

    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {

        Log.e("로그인 성공","success");
        requestMe();
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe() {
        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.requestMe(new MeResponseCallback() {

            // 1. 세션 오픈 실패. 세션이 삭제된 경우
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
            }

            // 2. 회원이 아닌 경우,

            public void onNotSignedUp() {
                Log.e("SessionCallback :: ", "onNotSignedUp");
            }

            // 3. 사용자정보 요청에 성공한 경우
            @Override
            public void onSuccess(UserProfile userProfile) {


                pref = PreferenceManager.getDefaultSharedPreferences(context);
                editor = pref.edit();

                Log.e("SessionCallback :: ", "onSuccess");
                String nickname = userProfile.getNickname(); //사용자 닉네임
                String profileImagePath = userProfile.getProfileImagePath(); //사용자 프사
                String thumnailPath = userProfile.getThumbnailImagePath(); // 썸네일이미지
                String UUID = userProfile.getUUID(); //앱과 연동했을 때 발급되는 고유 id
                long id = userProfile.getId(); // 인증여부를 확인하는 사용자의 id

                Log.e("Profile : ", nickname + "");
                Log.e("Profile : ", profileImagePath  + "");
                Log.e("Profile : ", thumnailPath + "");
                Log.e("Profile : ", UUID + "");
                Log.e("Profile : ", id + "");
                Log.e("token",Session.getCurrentSession().getAccessToken());
                token = Session.getCurrentSession().getAccessToken();

                editor.putString("profile",profileImagePath);
                editor.commit();

            }

            // 4. 사용자 정보 요청
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
            }
        });


    }

}
