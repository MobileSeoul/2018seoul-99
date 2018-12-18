package com.example.jinyoungkim.teamgung.ui.gung_ticket.make_reservation.viewpager_items;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.model.UserData;
import com.example.jinyoungkim.teamgung.model.UserInfoPost;
import com.example.jinyoungkim.teamgung.network.NetworkService;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {

    private String token;
    private String profileUrl;
    private String userName;
    private NetworkService networkService;
    private UserInfoPost userInfoPost;
    private SessionCallback sessionCallback;
    private String fragment_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        networkService = GlobalApplication.getGlobalApplicationContext().getNetworkService();


        Intent i = getIntent();
        Log.e("#######호출","호출");
        fragment_type = i.getStringExtra("fragment_type");
        Log.e("fragment_type",fragment_type);
        if(!SharePreferenceController.getTokenHeader(getApplicationContext()).equals("")){
            Log.e("header",SharePreferenceController.getTokenHeader(getApplicationContext()));
            Intent i2 = new Intent(getApplicationContext(),Loading2Activity.class);
            i2.putExtra("fragment_type2",fragment_type);
            startActivity(i2);
            finish();


        } else {
            Log.e("else","else");
            kakaoLogin();
            SharePreferenceController.setLogin(getApplicationContext(),"yes");
        }

    }

    private void kakaoLogin(){
        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL,this);
        Log.e("kakaoLogin()","in");
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Log.e("TAG" , "세션 오픈됨");
            // 사용자 정보를 가져옴, 회원가입 미가입시 자동가입 시킴
            KakaorequestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Log.e("TAG" , exception.getMessage());
            }
        }
    }
    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void KakaorequestMe() {

        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult) {
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode) {
                    Toast.makeText(getApplicationContext(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG" , "오류로 카카오로그인 실패 ");
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("TAG" , "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {

                profileUrl = userProfile.getProfileImagePath();
                userName = userProfile.getNickname();
                token = Session.getCurrentSession().getAccessToken();

                if(profileUrl!=null){
                    SharePreferenceController.setProfile(getApplicationContext(),profileUrl);
                    Log.e("프로필 사진 )",profileUrl);
                }


                Log.e("from: ",fragment_type);
                Log.e("유저 이름 ) ",userName);
                Log.e("토큰 ) ",token);


                // 네트워킹
                userInfoPost = new UserInfoPost(token);
                Call<UserData> userDataCall = networkService.login(userInfoPost);
                userDataCall.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        if(response.isSuccessful()){
                            SharePreferenceController.setTokenHeader(getApplicationContext(),response.body().result.token);
                            Toast.makeText(getApplicationContext(),"카카오 로그인이 되었습니다 :)",Toast.LENGTH_SHORT).show();
                            SharePreferenceController.setProfile(getApplicationContext(),profileUrl);
                            SharePreferenceController.setLogin(getApplicationContext(),"yes");
                            Log.e("Server in, 프사: ",SharePreferenceController.getProfile(getApplicationContext()));
                            Log.e("header",response.body().result.token);
                            Intent i2 = new Intent(getApplicationContext(),Loading2Activity.class);
                            i2.putExtra("fragment_type2",fragment_type);
                            startActivity(i2);
                            finish();
                        }else{
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        GlobalApplication.getGlobalApplicationContext().makeToast("서버 상태를 확인해주세요 :)");
                        finish();
                    }
                });

            }

            @Override
            public void onNotSignedUp() {
                // 자동가입이 아닐경우 동의창
                Log.e("here","here");

            }
        });
    }

}
