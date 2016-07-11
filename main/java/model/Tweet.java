package model;

/**
 * Created by azola.ndamase on 04-Jun-16.
 */
public class Tweet {

    private String tweet;
    private int score;

    public Tweet() {}

    public Tweet(String tweet, int score) {
        this.tweet = tweet;
        this.score = score;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTweet() {
        return tweet;
    }

    public int getScore() {
        return score;
    }
}
