package sentiment.analysis;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.HashTagsParams;
import com.aylien.textapi.parameters.SentimentParams;
import com.aylien.textapi.responses.HashTags;
import com.aylien.textapi.responses.Sentiment;
import sentiment.extraction.Polarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by azola.ndamase on 21-Jun-16.
 */
public class SentimentAnalyser {

    private static final String APP_ID = "b1daa57e";
    private static final String API_KEY = "add86d29917636df16184014775975a2";

    public static Polarity classifySentiment(String sentimentText, String type) {
        TextAPIClient client = new TextAPIClient(APP_ID, API_KEY);
        SentimentParams.Builder builder = SentimentParams.newBuilder();
        builder.setText(sentimentText);
        builder.setMode(type);
        Sentiment sentiment = new Sentiment();
        try {
            sentiment = client.sentiment(builder.build());
        } catch (TextAPIException e) {
            e.printStackTrace();
        }
        return new Polarity(sentiment.getPolarity(), sentiment.getPolarityConfidence());
    }

    public static List<String> getHashTags(String sentimentText) {
        List<String> toReturn = new ArrayList<>();

        TextAPIClient client = new TextAPIClient(APP_ID, API_KEY);
        HashTagsParams.Builder builder = HashTagsParams.newBuilder();
        builder.setText(sentimentText);
        try {
            HashTags hashTags = client.hashtags(builder.build());
            toReturn = Arrays.asList(hashTags.getHashtags());
        } catch (TextAPIException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public static String getSentimentValue(int sentimentScore) {
        String[] results = {"Very Negative", "Negative", "Neutral", "Positive", "Very Positive"};
        return results[sentimentScore];
    }
}
