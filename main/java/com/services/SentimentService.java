package com.services;

import data.SentimentDB;
import model.NewsArticle;
import model.Tweet;
import sentiment.analysis.SentimentAnalyser;
import sentiment.extraction.NewsArticleExtractor;
import sentiment.extraction.TweetExtractor;
import twitter4j.Status;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by azola.ndamase on 10-Jul-16.
 */
@Path("/sentiments")
public class SentimentService {

    private List<Tweet> tweets;
    private List<NewsArticle> newsArticles;
    private TweetExtractor tweetExtractor;
    private NewsArticleExtractor newsArticleExtractor;
    private SentimentDB sentimentDB;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @PostConstruct
    public void init() {
        sentimentDB = new SentimentDB();
    }

    private void createTweetSentimentModel() {
        if(sentimentDB.hasRecentTweets()) {
            tweets = sentimentDB.getTweets();
        }
        else {
            String topic = "\"The Rand\"";
            tweetExtractor = new TweetExtractor();
            tweets = new ArrayList<>();
            tweetExtractor.retrieveTwitterSentiments(topic);
            Tweet tempTweet;
            for (Status tweet : tweetExtractor.getTwitterSentiments()) {
                tempTweet = new Tweet(tweet.getText(), SentimentAnalyser.classifySentiment(tweet.getText()));
                tweets.add(tempTweet);
            }
            sentimentDB.saveTweets(tweets);
        }
    }

    private void createNewsArticlesSentimentModel() {
        if(sentimentDB.hasRecentNewsArticles()) {
            newsArticles = sentimentDB.getNewsArticles();
        }
        else {
            newsArticles = new ArrayList<>();
            newsArticleExtractor = new NewsArticleExtractor();
            newsArticleExtractor.retrieveNewsArticles();
            NewsArticle tempArticle;

            for (NewsArticle newsArticle : newsArticleExtractor.getNewsArticles()) {
                tempArticle = new NewsArticle(newsArticle.getSource(), newsArticle.getContent(), newsArticle.getScore());
                newsArticles.add(tempArticle);
            }
            sentimentDB.saveNewsArticles(newsArticles);
        }
    }

    @Path("/getTweets")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tweet> getTweets() {
        createTweetSentimentModel();
        return tweets;
    }

    @Path("/getNewsArticles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<NewsArticle> getNewsArticles() {
        createNewsArticlesSentimentModel();
        return newsArticles;
    }

    @Path("/getTopics")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTopics() {
        List<String> toReturn = new ArrayList<>();

        for(Tweet tweet: getTweets()) {
            toReturn.addAll(SentimentAnalyser.getHashTags(tweet.getTweet()));
        }
        return toReturn;
    }

    @Path("/getNumberOfTweets")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int getNumberOfTweets() {
        int toReturn = 0;

        toReturn = sentimentDB.getNumberOfTweetsProcessed();

        return toReturn;
    }
}
