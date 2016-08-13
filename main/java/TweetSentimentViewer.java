import model.Tweet;
import sentiment.analysis.SentimentAnalyser;
import sentiment.extraction.ExchangeRateExtractor;
import sentiment.extraction.TweetExtractor;
import twitter4j.Status;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.*;

/**
 * Created by azola.ndamase on 10-Jul-16.
 */
@ManagedBean
public class TweetSentimentViewer {

    private List<Tweet> tweets;

    private TweetExtractor tweetExtractor;
    @PostConstruct
    public void init() {
        createTweetSentimentModel();
    }

    private void createTweetSentimentModel() {
        String topic = "#TheRand";
        tweetExtractor = new TweetExtractor();
        tweets = new ArrayList<>();
        SentimentAnalyser.initialisePipeline();
        tweetExtractor.retrieveTwitterSentiments(topic);
        Tweet tempTweet;
        for(Status tweet : tweetExtractor.getTwitterSentiments()) {
            tempTweet = new Tweet(tweet.getText(), SentimentAnalyser.classifySentiment(tweet.getText()));
            tweets.add(tempTweet);
        }

        tweets.add(new Tweet("R" + new ExchangeRateExtractor().getRandDollar(), 5));
        tweets.add(new Tweet("R" + new ExchangeRateExtractor().getRandEuro(), 5));
        tweets.add(new Tweet("R" + new ExchangeRateExtractor().getRandPound(), 5));
    }

    public List<Tweet> getTweets() {
        return tweets;
    }
}
