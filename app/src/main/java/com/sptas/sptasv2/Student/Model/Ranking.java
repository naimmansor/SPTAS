package com.sptas.sptasv2.Student.Model;

/**
 * Created by Na'im Mansor on 22-Feb-18.
 */

public class Ranking {
    private String userName;
    private long score;

    public Ranking() {
    }

    public Ranking(String userName, long score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}