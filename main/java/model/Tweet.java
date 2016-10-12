package model;

/**
 * Created by azola.ndamase on 04-Jun-16.
 */

public class Tweet {

    private int id;
    private String tweet;
    private String score;

    public Tweet() {}

    public Tweet(String tweet, String score) {
        this.tweet = tweet;
        this.score = score;
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
}
