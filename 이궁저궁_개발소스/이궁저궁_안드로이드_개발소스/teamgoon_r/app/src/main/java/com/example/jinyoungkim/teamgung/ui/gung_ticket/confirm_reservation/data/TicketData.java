package com.example.jinyoungkim.teamgung.ui.gung_ticket.confirm_reservation.data;

public class TicketData {
    public int ticket_flag;
    public int finish_flag;
    public int review_flag;
    public TicketData(int ticket_flag,int finish_flag,int review_flag){
        this.ticket_flag = ticket_flag;
        this.finish_flag = finish_flag;
        this.review_flag = review_flag;

    }

}
