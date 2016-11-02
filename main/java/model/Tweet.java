package model;

import java.util.Date;

/**
 * Created by azola.ndamase on 04-Jun-16.
 */

public class Tweet {

    private int id;
    private String tweet;
    private String score;
    private double polarityValue;
    private Date creationDate;

    public Tweet() {}

    public Tweet(String tweet, String score) {
        this.tweet = tweet;
        this.score = score;
        setCreationDate(new Date());
    }

    public int getId() {
        return id;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTweet() {
        return tweet;
    }

    public String getScore() {
        return score;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getPolarityValue() {
        return polarityValue;
    }

    public void setPolarityValue(double polarityValue) {
        this.polarityValue = polarityValue;
    }
}
