package sentiment.extraction;
/**
 * Created by azola.ndamase on 19-Jun-16.
 */
import java.util.*;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TweetExtractor {

    private String OAUTH_CONSUMER_KEY = "aEidxZ4aUTEAQZ26CgfhYbGFQ";
    private String OAUTH_CONSUMER_SECRET = "Gmp3jzdHgSyTckTn27t2PibRJ24pblYQ5vnrt586ZRqZQuhY5H";
    private String OAUTH_ACCESS_TOKEN = "114499137-s7lL2VhXrKj5Mr0vLEBp5wNHle56PfUjAY6I6xJO";
    private String OAUTH_ACCESS_TOKEN_SECRET = "2DwfFFcODH36F0FHGseLUGqE895aJLJB02TjSo28bMwsu";

    private List<Status> twitterSentiments;
    private ConfigurationBuilder configurationBuilder;
    private Query query;

    public void retrieveTwitterSentiments(String searchKeyword) {
        initialiseConfigurationBuilder();
        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = twitterFactory.getInstance();
        initialiseQuery(searchKeyword);
        try {
            QueryResult queryResult = twitter.search(query);
            twitterSentiments = queryResult.getTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private void initialiseConfigurationBuilder() {
        configurationBuilder = new ConfigurationBuilder()
        .setDebugEnabled(true)
        .setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
        .setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
        .setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
        .setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
    }

    private void initialiseQuery(String toSearch){
        /*
        Search for tweets based on a given keywordv @toSearch. Exclude retweets, links, replies and images.
        */
        query = new Query(toSearch + " -filter:links -filter:retweets -filter:replies -filter:images");
        query.setLocale("en");
        query.setLang("en");
        query.setCount(10);
    }
    /***************************************************************************************
     *    Title: Day 20: Stanford CoreNLP — Performing Sentiment Analysis of Twitter using Java
     *    Author: SHEKHAR GULATI
     *    Date: 17 November 2013
     *    Code version: 1.0
     *    Availability: https://blog.openshift.com/day-20-stanford-corenlp-performing-sentiment-analysis-of-twitter-using-java/
     *
     ***************************************************************************************/

    public List<Status> getTwitterSentiments() {
        return twitterSentiments;
    }
}
