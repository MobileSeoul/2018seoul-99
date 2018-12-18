package com.example.jinyoungkim.teamgung.model;

public class ReviewWrite {
    public int palace_id;
    public float review_traffic;
    public float review_crowd;
    public float review_attraction;
    public  int ticket_id;


    public ReviewWrite(int palace_id, float review_traffic, float review_crowd, float review_attraction, int ticket_id) {
        this.palace_id = palace_id;
        this.review_traffic = review_traffic;
        this.review_crowd = review_crowd;
        this.review_attraction = review_attraction;
        this.ticket_id = ticket_id;
    }
}
