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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by azola.ndamase on 10-Jul-16.
 */
@Path("/sentiments")
public class SentimentService {

    private List<Tweet> tweets;
    private List<NewsArticle> newsArticles;
    private TweetExtractor tweetExtractor;
    private NewsArticleExtractor newsArticleExtractor;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @PostConstruct
    public void init() {
       /* SentimentAnalyser.initialisePipeline();*/
        createTweetSentimentModel();
        createNewsArticlesSentimentModel();
    }

    private void createTweetSentimentModel() {
        String topic = "The Rand";
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
        newsArticles = new ArrayList<>();
        newsArticleExtractor = new NewsArticleExtractor();
        newsArticleExtractor.retrieveNewsArticles();
        NewsArticle tempArticle;

        for(NewsArticle newsArticle : newsArticleExtractor.getNewsArticles()) {
            tempArticle = new NewsArticle(newsArticle.getSource(), newsArticle.getContent(), newsArticle.getScore());
            newsArticles.add(tempArticle);
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
