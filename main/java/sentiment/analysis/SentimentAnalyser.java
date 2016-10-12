package sentiment.analysis;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.SentimentParams;
import com.aylien.textapi.responses.Sentiment;

/**
 * Created by azola.ndamase on 21-Jun-16.
 */
public class SentimentAnalyser {

    private static final String APP_ID = "b1daa57e";
    private static final String API_KEY = "add86d29917636df16184014775975a2";

    public static String classifySentiment(String sentimentText) {
        TextAPIClient client = new TextAPIClient(APP_ID, API_KEY);
        SentimentParams.Builder builder = SentimentParams.newBuilder();
        builder.setText(sentimentText);
        Sentiment sentiment = new Sentiment();
        try {
            sentiment = client.sentiment(builder.build());
        } catch (TextAPIException e) {
            e.printStackTrace();
        }
        return sentiment.toString();
    }

    public static String getSentimentValue(int sentimentScore) {
        String[] results = {"Very Negative", "Negative", "Neutral", "Positive", "Very Positive"};
        return results[sentimentScore];
    }
}
