package sentiment.prediction;

/**
 * Created by azola.ndamase on 2016-10-28.
 */

import data.SentimentDB;
import model.CurrencyType;
import model.ForeignCurrency;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import sentiment.analysis.PolarityCount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForexPredictor {

    private SentimentDB sentimentDB;
    private SimpleRegression simpleRegression;

    private void createRegressionModel(CurrencyType currencyType){
        sentimentDB = new SentimentDB();
        sentimentDB.connect();

        simpleRegression = new SimpleRegression(true);

        Double[] positivesArray = getPositiveSentiments(false);
        Double[] negativesArray = getNegativeSentiments(false);
        Double[] neutralsArray = getNeutralSentiments(false);
        Double[] currencyArray;

        switch (currencyType) {
            case DOLLAR:
                currencyArray = getDollarValues();
                break;
            case POUND:
                currencyArray = getPoundValues();
                break;
            default:
                currencyArray = getEuroValues();
                break;
        }

        for(int i = 0; i < positivesArray.length; i++) {
            simpleRegression.addData((positivesArray[i] + negativesArray[i] + neutralsArray[i])/3, currencyArray[i]);
        }
    }

    public double getNextHourDollarPrediction(){
        createRegressionModel(CurrencyType.DOLLAR);
        double dollarPrediction = 0;

        Double[] positivesArray = getPositiveSentiments(true);
        Double[] negativesArray = getNegativeSentiments(true);
        Double[] neutralsArray = getNeutralSentiments(true);

        double aggregatedSentiment = (positivesArray[0] + negativesArray[0] + neutralsArray[0])/3;

        if(!sentimentDB.hasRecentDollarPrediction()) {
            dollarPrediction = simpleRegression.predict(aggregatedSentiment);
            sentimentDB.saveDollarPrediction(new ForeignCurrency("USD", dollarPrediction));
        }
        else {
            dollarPrediction = sentimentDB.getCurrentDollarPrediction();
        }

        return dollarPrediction;
    }

    public double getNextHourPoundPrediction(){
        createRegressionModel(CurrencyType.POUND);
        double poundPrediction = 0;

        Double[] positivesArray = getPositiveSentiments(true);
        Double[] negativesArray = getNegativeSentiments(true);
        Double[] neutralsArray = getNeutralSentiments(true);

        double aggregatedSentiment = (positivesArray[0] + negativesArray[0] + neutralsArray[0])/3;

        if(!sentimentDB.hasRecentPoundPrediction()) {
            poundPrediction = simpleRegression.predict(aggregatedSentiment);
            sentimentDB.savePoundPrediction(new ForeignCurrency("GBP", poundPrediction));
        }
        else {
            poundPrediction = sentimentDB.getCurrentPoundPrediction();
        }

        return poundPrediction;
    }

    public double getNextHourEuroPrediction(){
        createRegressionModel(CurrencyType.EURO);
        double euroPrediction = 0;

        Double[] positivesArray = getPositiveSentiments(true);
        Double[] negativesArray = getNegativeSentiments(true);
        Double[] neutralsArray = getNeutralSentiments(true);

        double aggregatedSentiment = (positivesArray[0] + negativesArray[0] + neutralsArray[0])/3;

        if(!sentimentDB.hasRecentEuroPrediction()) {
            euroPrediction = simpleRegression.predict(aggregatedSentiment);
            sentimentDB.saveEuroPrediction(new ForeignCurrency("EUR", euroPrediction));
        }
        else {
            euroPrediction = sentimentDB.getCurrentEuroPrediction();
        }

        return euroPrediction;
    }

    private Double[] getPositiveSentiments(boolean isLastHour){
        List<Double> polarityValueList = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        String selectPositives = "select sum(NumericalScore) as PositiveSum from TWEET\n" +
                                "where Score = 'positive' and cast(CreationDate as Date) > '2016-10-15'\n" +
                                "group by cast(CreationDate as Date);";

        String selectPositivesFromLastHour = "select sum(NumericalScore) as PositiveSum from TWEET\n" +
                                             "where Score = 'positive' and CreationDate > dateadd(hour, -1, getdate());";
        try {
            statement = sentimentDB.getConnection().createStatement();
            resultSet = statement.executeQuery(isLastHour ? selectPositivesFromLastHour : selectPositives);
            if(resultSet.next()) {
                do {
                    polarityValueList.add(resultSet.getDouble("PositiveSum"));
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Double[] toReturn = new Double[polarityValueList.size()];
        toReturn = polarityValueList.toArray(toReturn);

        return toReturn;
    }
    private Double[] getNegativeSentiments(boolean isLastHour){
        List<Double> polarityValueList = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        String selectNegatives = "select sum(NumericalScore) as NegativeSum from TWEET\n" +
                "where Score = 'negative' and cast(CreationDate as Date) > '2016-10-15'\n" +
                "group by cast(CreationDate as Date);";

        String selectNegativesFromLastHour = "select sum(NumericalScore) as NegativeSum from TWEET\n" +
                "where Score = 'negative' and CreationDate > dateadd(hour, -1, getdate());";
        try {
            statement = sentimentDB.getConnection().createStatement();
            resultSet = statement.executeQuery(isLastHour ? selectNegativesFromLastHour : selectNegatives);
            if(resultSet.next()) {
                do {
                    polarityValueList.add(resultSet.getDouble("NegativeSum"));
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Double[] toReturn = new Double[polarityValueList.size()];
        toReturn = polarityValueList.toArray(toReturn);

        return toReturn;
    }

    private Double[] getNeutralSentiments(boolean isLastHour){
        List<Double> polarityValueList = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        String selectNeutrals = "select sum(NumericalScore) as NeutralSum from TWEET\n" +
                "where Score = 'neutral' and cast(CreationDate as Date) > '2016-10-15'\n" +
                "group by cast(CreationDate as Date);";

        String selectNeutralsFromLastHour = "select sum(NumericalScore) as NeutralSum from TWEET\n" +
                "where Score = 'neutral' and CreationDate > dateadd(hour, -1, getdate());";
        try {
            statement = sentimentDB.getConnection().createStatement();
            resultSet = statement.executeQuery(isLastHour ? selectNeutralsFromLastHour : selectNeutrals);
            if(resultSet.next()) {
                do {
                    polarityValueList.add(resultSet.getDouble("NeutralSum"));
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Double[] toReturn = new Double[polarityValueList.size()];
        toReturn = polarityValueList.toArray(toReturn);

        return toReturn;
    }

    private Double[] getDollarValues(){
        List<Double> dollarValues = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        String selectValues = "select sum(RandValue) as RandValue from FOREIGN_CURRENCY\n" +
                "where Symbol = 'USD' and cast(LastUpdatedDate as Date) between '2016-10-15' and GETDATE()\n" +
                "group by cast(LastUpdatedDate as Date);";
        try {
            statement = sentimentDB.getConnection().createStatement();
            resultSet = statement.executeQuery(selectValues);
            if(resultSet.next()) {
                do {
                    dollarValues.add(resultSet.getDouble("RandValue"));
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Double[] toReturn = new Double[dollarValues.size()];
        toReturn = dollarValues.toArray(toReturn);

        return toReturn;
    }

    private Double[] getPoundValues(){
        List<Double> poundValues = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        String selectValues = "select sum(RandValue) as RandValue from FOREIGN_CURRENCY\n" +
                "where Symbol = 'GBP' and cast(LastUpdatedDate as Date) between '2016-10-15' and GETDATE()\n" +
                "group by cast(LastUpdatedDate as Date);";
        try {
            statement = sentimentDB.getConnection().createStatement();
            resultSet = statement.executeQuery(selectValues);
            if(resultSet.next()) {
                do {
                    poundValues.add(resultSet.getDouble("RandValue"));
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Double[] toReturn = new Double[poundValues.size()];
        toReturn = poundValues.toArray(toReturn);

        return toReturn;
    }

    private Double[] getEuroValues(){
        List<Double> euroValues = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        String selectValues = "select sum(RandValue) as RandValue from FOREIGN_CURRENCY\n" +
                "where Symbol = 'EUR' and cast(LastUpdatedDate as Date) between '2016-10-15' and GETDATE()\n" +
                "group by cast(LastUpdatedDate as Date);";
        try {
            statement = sentimentDB.getConnection().createStatement();
            resultSet = statement.executeQuery(selectValues);
            if(resultSet.next()) {
                do {
                    euroValues.add(resultSet.getDouble("RandValue"));
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Double[] toReturn = new Double[euroValues.size()];
        toReturn = euroValues.toArray(toReturn);

        return toReturn;
    }

}
