package com.example.kapp.model;

public class AvgVote {
    private float avg;
    private int num;

    public AvgVote(float avg, int num) {
        this.avg = avg;
        this.num = num;
    }

    public AvgVote() {
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
