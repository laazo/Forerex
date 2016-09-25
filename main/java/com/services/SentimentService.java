package com.services;

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

/**
 * Created by azola.ndamase on 10-Jul-16.
 */
@Path("/sentiments")
public class SentimentService {

    private List<Tweet> tweets;
    private List<NewsArticle> newsArticles;
    private TweetExtractor tweetExtractor;

    @PostConstruct
    public void init() {
        SentimentAnalyser.initialisePipeline();
        createTweetSentimentModel();
        createNewsArticlesSentimentModel();
    }

    private void createTweetSentimentModel() {
        String topic = "ZARUSD";
        tweetExtractor = new TweetExtractor();
        tweets = new ArrayList<>();
        tweetExtractor.retrieveTwitterSentiments(topic);
        Tweet tempTweet;
        for(Status tweet : tweetExtractor.getTwitterSentiments()) {
            tempTweet = new Tweet(tweet.getText(), SentimentAnalyser.classifySentiment(tweet.getText()));
            tweets.add(tempTweet);
        }
    }

    private void createNewsArticlesSentimentModel() {
        NewsArticleExtractor newsArticleExtractor = new NewsArticleExtractor();
        newsArticles = new ArrayList<>();
        newsArticleExtractor.retrieveNewsArticles();
        NewsArticle tempArticle;

        for(NewsArticle newsArticle : newsArticleExtractor.getNewsArticles()) {
            tempArticle = new NewsArticle();
            tempArticle.setSource(newsArticle.getSource());
            tempArticle.setContent(newsArticle.getContent());
            tempArticle.setScore(SentimentAnalyser.classifySentiment(newsArticle.getContent()));
            newsArticles.add(newsArticle);
        }
    }

    @Path("/getTweets")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tweet> getTweets() {
        return tweets;
    }

    @Path("/getNewsArticles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<NewsArticle> getNewsArticles() {
        return newsArticles;
    }
}