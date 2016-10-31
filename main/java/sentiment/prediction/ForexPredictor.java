package sentiment.prediction;

/**
 * Created by azola.ndamase on 2016-10-28.
 */

import data.SentimentDB;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import sentiment.analysis.PolarityCount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ForexPredictor {

    private int NEGATIVE = 0;
    private int NEUTRAL = 1;
    private int POSITIVE = 2;

    private void createRegressionModel(){
        List<PolarityCount> polarityCountList = new ArrayList<>();
        SentimentDB sentimentDB = new SentimentDB();
        Statement statement;
        ResultSet resultSet;

        String selectPositives = "select count(*) as PositiveCount from TWEET"+
                                "where Score = 'Positive' group by cast(CreationDate as Date);";

        String selectNegatives = "select count(*) as NegativeCount from TWEET"+
                "where Score = 'Negative' group by cast(CreationDate as Date);";

        String selectNeutrals = "select count(*) as NeutralCount from TWEET"+
                "where Score = 'Neutral' group by cast(CreationDate as Date);";

        sentimentDB.connect();

        try {
            statement = sentimentDB.getConnection().createStatement();
            resultSet = statement.executeQuery(selectPositives);
            resultSet.next();
            polarityCountList.add(new PolarityCount(POSITIVE, resultSet.getInt("PositiveCount")));
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private int getPositiveSentiments(){
        return 0;
    }

}
