package com.example.jinyoungkim.teamgung.network;

import com.example.jinyoungkim.teamgung.model.AvarageReview;
import com.example.jinyoungkim.teamgung.model.ConfirmTicket;
import com.example.jinyoungkim.teamgung.model.PayPost;
import com.example.jinyoungkim.teamgung.model.PayResult;
import com.example.jinyoungkim.teamgung.model.ReviewWrite;
import com.example.jinyoungkim.teamgung.model.ReviewWriteResult;
import com.example.jinyoungkim.teamgung.model.ShowPhotoGet;
import com.example.jinyoungkim.teamgung.model.UserData;
import com.example.jinyoungkim.teamgung.model.UserInfoPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkService {

    // 1. 예매 확인 (티켓)
    @GET("/ticket/confirm_ticket")
    Call<ConfirmTicket>confirmTicket(@Header("Authorization")String header);



    // 2. 유저 등록
    @POST("/user/user_info")
    Call<UserData>login(@Body UserInfoPost userInfoPost);


    // 3. 결제 하기
    @POST("/ticket/pay_ticket")
    Call<PayResult>pay(@Header("Authorization")String header, @Body PayPost payPost);


    // 4. 배워보기 사진
    @GET("/palace/palace_view/{palace_id}")
    Call<ShowPhotoGet>showPhoto(@Header("Authorization")String header, @Path("palace_id")int palace_id);


    // 5. 후기 등록
    @POST("/ticket/review_ticket")
    Call<ReviewWriteResult>review(@Header("Authorization")String header, @Body ReviewWrite reviewWrite);

    // 6.알아보기 - 궁별 후기 평점 보는 부분
    @GET("/ticket/review_ticket/{palace_id}")
    Call<AvarageReview>avarage(@Header("Authorization")String header,@Path("palace_id")int palace_id);


}
