package com.example.kapp.model;

public class Vote {
    private String account;
    private int creation;
    private int vote;
    private String vote_comment;
    private String updade_at;

    public Vote(String account, int creation, int vote, String vote_comment,String update_at) {
        this.account = account;
        this.creation = creation;
        this.vote = vote;
        this.vote_comment = vote_comment;
        this.updade_at = update_at;
    }

    public Vote() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCreation() {
        return creation;
    }

    public void setCreation(int creation) {
        this.creation = creation;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getVote_comment() {
        return vote_comment;
    }

    public void setVote_comment(String vote_comment) {
        this.vote_comment = vote_comment;
    }

    public String getUpdade_at() {
        return updade_at;
    }

    public void setUpdade_at(String updade_at) {
        this.updade_at = updade_at;
    }
}
