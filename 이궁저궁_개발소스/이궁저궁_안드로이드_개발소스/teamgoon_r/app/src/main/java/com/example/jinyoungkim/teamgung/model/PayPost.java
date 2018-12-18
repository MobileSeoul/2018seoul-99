package com.example.jinyoungkim.teamgung.model;

public class PayPost {
    public int palace_id;
    public String ticket_title;
    public String ticket_start;
    public String ticket_end;
    public String ticket_people;
    public int ticket_special;
    public int ticket_jongro;

    public PayPost(int palace_id, String ticket_title, String ticket_start, String ticket_end, String ticket_people, int ticket_special, int ticket_jongro) {
        this.palace_id = palace_id;
        this.ticket_title = ticket_title;
        this.ticket_start = ticket_start;
        this.ticket_end = ticket_end;
        this.ticket_people = ticket_people;
        this.ticket_special = ticket_special;
        this.ticket_jongro = ticket_jongro;
    }
}
