package com.example.jinyoungkim.teamgung.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePreferenceController {
    private static final String TOKEN_COMPARE = "token_compare"; // 이전 토큰값과 똑같은지 비교하기 위한 변수
    private static final String TOKEN_HEADER = "token_header"; // 서버로 부터 받은 토큰 값 (헤더에 넣어야 할)
    private static final String PROFILE = "profile"; // 사용자 프로필 이미지 url
    private static final String LOGIN = "login";
    private static final String PALACE_ID = "palace_id";
    private static final String TICKET_ID = "ticket_id";
    private static final String PHOTO_URL = "photo_url";
    private static final String DATA_FLAG = "data_flag";


    // 1. 토큰 값 비교
    public static void setTokenCompare(Context context, String tokenCompare){
        SharedPreferences pref = context.getSharedPreferences(TOKEN_COMPARE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_COMPARE,tokenCompare);
        editor.commit();
    }

    public static String getTokenCompare(Context context){
        SharedPreferences pref = context.getSharedPreferences(TOKEN_COMPARE,context.MODE_PRIVATE);
        String tokenCompare = pref.getString(TOKEN_COMPARE,"");
        return tokenCompare;
    }


    // 2. 서버로 부터 받은 토큰 값
    public static void setTokenHeader(Context context,String tokenHeader){
        SharedPreferences pref = context.getSharedPreferences(TOKEN_HEADER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_HEADER,tokenHeader);
        editor.commit();
    }

    public static String getTokenHeader(Context context){
        SharedPreferences pref = context.getSharedPreferences(TOKEN_HEADER,context.MODE_PRIVATE);
        String tokenHeader = pref.getString(TOKEN_HEADER,"");
        return tokenHeader;
    }

    public static void deleteTokenHeader (Context context){
        SharedPreferences pref = context.getSharedPreferences(TOKEN_HEADER,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(TOKEN_HEADER);
        Log.e("delete","delete");
    }


    // 3. 이미지 url
    public static void setProfile(Context context, String profile){
        SharedPreferences pref = context.getSharedPreferences(PROFILE,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PROFILE,profile);
        editor.commit();
    }

    public static String getProfile(Context context){
        SharedPreferences pref = context.getSharedPreferences(PROFILE,context.MODE_PRIVATE);
        String profile = pref.getString(PROFILE,"");
        return  profile;
    }

    // 4. 로그인 여부
    public static void setLogin(Context context, String login){
        SharedPreferences pref = context.getSharedPreferences(LOGIN,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LOGIN,login);
        editor.commit();
    }

    public static String getLogin(Context context){
        SharedPreferences pref = context.getSharedPreferences(LOGIN,context.MODE_PRIVATE);
        String login = pref.getString(LOGIN,"");
        return login;
    }

    //5.예매확인->후기등록하려할때 궁 Name 받아서 궁 id 로 변환
    public static void setPalaceID(Context context,String palace_name){
     SharedPreferences pref = context.getSharedPreferences(PALACE_ID,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PALACE_ID,palace_name);
        editor.commit();

    }

    public static int getPalaceId(Context context){
        SharedPreferences pref = context.getSharedPreferences(PALACE_ID,context.MODE_PRIVATE);
        String palace_name = pref.getString(PALACE_ID,"");
        int id = 0;
        switch (palace_name){
            case "경복궁":
                id=0;
                break;
            case "창덕궁":
                id=1;
                break;
            case "창경궁":
                id = 2;
                break;
            case "덕수궁":
                id = 3;
                break;
            case "종묘":
                id = 4;
                break;

        }
        return id;
    }

    //6.예매확인->후기등록 티켓아이디 저장
    public static void setTicketID(Context context,int ticketID){
        SharedPreferences pref = context.getSharedPreferences(TICKET_ID,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(TICKET_ID,ticketID);
        editor.commit();
    }
    public static int getTicketID(Context context){
        SharedPreferences pref = context.getSharedPreferences(TICKET_ID,context.MODE_PRIVATE);
        int ticket_id = pref.getInt(TICKET_ID,0);
        return ticket_id;
    }

    //7.배워보기-사진확대 사진 url넘기기
    public static void setPhotoURL(Context context,String url){
        SharedPreferences pref = context.getSharedPreferences(PHOTO_URL,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PHOTO_URL,url);
        editor.commit();
    }

    public static String getPhotoURL(Context context){
        SharedPreferences pref = context.getSharedPreferences(PHOTO_URL,context.MODE_PRIVATE);
        String photo_url = pref.getString(PHOTO_URL,"");
        return photo_url;

    }

    //8.예매확인 데이터 변경 플래그
    public static void setDataChange(Context context, int flag){
        SharedPreferences pref = context.getSharedPreferences(DATA_FLAG,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(DATA_FLAG,flag);
        editor.commit();
    }
    public static int getDataChange(Context context){
        SharedPreferences pref = context.getSharedPreferences(DATA_FLAG,context.MODE_PRIVATE);
        int data_flag = pref.getInt(DATA_FLAG,0);
        return data_flag;

    }


}
