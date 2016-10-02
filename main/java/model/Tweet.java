package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by azola.ndamase on 04-Jun-16.
 */
@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String tweet;
    private int score;

    public Tweet() {}

    public Tweet(String tweet, int score) {
        this.tweet = tweet;
        this.score = score;
    }

    public int getId() {
        return id;
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
