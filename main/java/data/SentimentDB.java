package data;

import java.sql.*;

/**
 * Created by azola.ndamase on 2016-10-12.
 */
public class SentimentDB {
    private final String CONNECTION_STRING = "jdbc:jtds:sqlserver://localhost/Forerex";


    public void connect() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_STRING,"","");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTweets() {}

    public void saveNewsArticles() {}

    public void getBaseExchangeRates() {}

    public void getTweets() {}

    public void getNewsArticles() {}
}
