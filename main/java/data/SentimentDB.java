package data;

import model.ForeignCurrency;
import model.NewsArticle;
import model.Tweet;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by azola.ndamase on 2016-10-12.
 */
public class SentimentDB {
    private final String CONNECTION_STRING = "jdbc:jtds:sqlserver://localhost:1433/Forerex;";
    Calendar calendar = Calendar.getInstance();
    private Connection connection;

    public void connect() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTweets(List<Tweet> tweets) {

        PreparedStatement statement;
        String articleInsert = "insert into TWEET (Text,Score,CreationDate)  " +
                "values(?,?,?);";
        connect();

        try {

            statement = connection.prepareStatement(articleInsert);
            for(Tweet tweet: tweets) {
                statement.setString(1, tweet.getTweet());
                statement.setString(2, tweet.getScore());
                statement.setTimestamp(3, new Timestamp(calendar.getTimeInMillis()));
                statement.executeUpdate();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public void saveNewsArticles(List<NewsArticle> newsArticles) {
        PreparedStatement statement;

        String tweetInsert= "insert into NEWS_ARTICLE (Source, Content,Score,CreationDate)  " +
                            "values(?,?,?,?);";

        connect();

        try {
            statement = connection.prepareStatement(tweetInsert);
            for(NewsArticle newsArticle: newsArticles) {
                statement.setString(1,newsArticle.getSource());
                statement.setString(2,newsArticle.getContent());
                statement.setString(3,newsArticle.getScore());
                statement.setTimestamp(4, new Timestamp(calendar.getTimeInMillis()));

                statement.executeUpdate();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveBaseExchangeRates(List<ForeignCurrency> foreignCurrencies) {

    }

    public List<ForeignCurrency> getBaseExchangeRates() {
        return Collections.EMPTY_LIST;
    }

    public List<Tweet> getTweets() {
        Tweet tempTweet;
        Statement statement;
        ResultSet resultSet;
        List<Tweet> toReturn = new ArrayList<>();

        String selectTweets = "select * from TWEET where CreationDate > dateadd(hour, -1, getdate());";
        connect();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectTweets);
            if(resultSet.next()) {
                do {
                    tempTweet = new Tweet(resultSet.getString("Text"), resultSet.getString("Score"));
                    tempTweet.setCreationDate(resultSet.getDate("CreationDate"));
                    toReturn.add(tempTweet);

                } while (resultSet.next());
                connection.close();
            }
            else {

                toReturn = Collections.EMPTY_LIST;
            }

        }catch (SQLException se) {
            se.printStackTrace();
        }
        return toReturn;
    }

    public List<NewsArticle> getNewsArticles() {
        NewsArticle tempArticle;
        Statement statement;
        ResultSet resultSet;
        List<NewsArticle> toReturn = new ArrayList<>();

        String selectArticles = "select * from NEWS_ARTICLE where CreationDate > dateadd(day, -1, getdate());";
        connect();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectArticles);
            if(resultSet.next()) {
                do {
                    tempArticle = new NewsArticle(resultSet.getString("Source"), resultSet.getString("Content"), resultSet.getString("Score"));
                    tempArticle.setCreationDate(resultSet.getDate("CreationDate"));
                    toReturn.add(tempArticle);

                } while (resultSet.next());
                connection.close();
            }
            else {

                toReturn = Collections.EMPTY_LIST;
            }

        }catch (SQLException se) {
            se.printStackTrace();
        }
        return toReturn;
    }

    public boolean hasRecentTweets() {
        return (getTweets().size() > 0);
    }
    public boolean hasRecentNewsArticles() {
       return (getNewsArticles().size() > 0);
    }
    public boolean hasRecentExchangeRates() {
        return (getBaseExchangeRates().size() > 0);
    }
}
