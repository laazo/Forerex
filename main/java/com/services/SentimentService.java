package com.services;

import data.SentimentDB;
import model.NewsArticle;
import model.Tweet;
import sentiment.analysis.SentimentAnalyser;
import sentiment.extraction.NewsArticleExtractor;
import sentiment.extraction.Polarity;
import sentiment.extraction.TweetExtractor;
import sentiment.prediction.ForexPredictor;
import sentiment.prediction.HistoryEntry;
import sentiment.prediction.PredictionEntry;
import twitter4j.Status;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Polarity polarity;
            for (Status tweet : tweetExtractor.getTwitterSentiments()) {
                polarity = SentimentAnalyser.classifySentiment(tweet.getText(), "Tweet");
                tempTweet = new Tweet(tweet.getText(), polarity.getPolarity());
                tempTweet.setPolarityValue(polarity.getPolarityValue());
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
        String input="";
        Pattern hashtagPattern = Pattern.compile("#(\\S+)");
        Matcher matcher;
        List<String> toReturn=new ArrayList<String>();

        for(Tweet tweet: getTweets()) {
           input += tweet.getTweet();
        }

        matcher = hashtagPattern.matcher(input);
        while (matcher.find()) {
            toReturn.add(matcher.group(1));
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

    @Path("/getDollarPrediction")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public double getDollarPrediction(){
        ForexPredictor forexPredictor = new ForexPredictor();
        return forexPredictor.getNextHourDollarPrediction();

    }

    @Path("/getPoundPrediction")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public double getPoundPrediction(){
        ForexPredictor forexPredictor = new ForexPredictor();
        return forexPredictor.getNextHourPoundPrediction();

    }

    @Path("/getEuroPrediction")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public double getEuroPrediction(){
        ForexPredictor forexPredictor = new ForexPredictor();
        return forexPredictor.getNextHourEuroPrediction();

    }

    @Path("/getCurrencyPredictions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PredictionEntry> getCurrencyPredictions(@QueryParam("id") String symbol){
        ForexPredictor forexPredictor = new ForexPredictor();
        return forexPredictor.getCurrencyPredictions(symbol);

    }

    @Path("/getActualHistory")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HistoryEntry> getActualHistory(@QueryParam("id") String symbol){
        ForexPredictor forexPredictor = new ForexPredictor();
        return forexPredictor.getActualHistory(symbol);

    }

    @Path("/getPredictionHistory")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HistoryEntry> getPredictionHistory(@QueryParam("id") String symbol){
        ForexPredictor forexPredictor = new ForexPredictor();
        return forexPredictor.getPredictionHistory(symbol);

    }



}
