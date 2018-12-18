package com.example.jinyoungkim.teamgung.model;

public class ConfirmTicketData {
    public int ticket_id;
    public String palace_name;
    public String ticket_people;
    public String ticket_title;
    public int ticket_flag;
    public String ticket_start;
    public String ticket_end;
    public int ticket_review;
    public int end_flag;

    public ConfirmTicketData(int ticket_id,String palace_name,String ticket_people, String ticket_title, int ticket_flag,
                             String ticket_start, String ticket_end , int ticket_review, int end_flag){
        this.ticket_id =ticket_id;
        this.palace_name = palace_name;
        this.ticket_people = ticket_people;
        this.ticket_title = ticket_title;
        this.ticket_flag = ticket_flag;
        this.ticket_start = ticket_start;
        this.ticket_end = ticket_end;
        this.ticket_review = ticket_review;
        this.end_flag = end_flag;

    }


}
