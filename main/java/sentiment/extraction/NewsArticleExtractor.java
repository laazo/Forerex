package sentiment.extraction;

import model.NewsArticle;
import org.json.JSONArray;
import org.json.JSONObject;
import sentiment.analysis.SentimentAnalyser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by azola.ndamase on 12-Aug-16.
 */
public class NewsArticleExtractor {

    private static final String WEBHOSE_API_KEY = "e9ab4f02-6c24-4ea8-a25d-d5c68f51274f";
    private static final String SEARCH_QUERY = "&format=json&q=thread.title%3A(rand)%20language%3A(english)%20thread.country%3AZA%20(site_type%3Anews)";
    private static final String WEBHOSE_URI = "http://webhose.io/search?token=";
    private static final int NUMBER_OF_RESULTS = 10;

    private List<NewsArticle> newsArticles;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public void retrieveNewsArticles() {
        Client webhoseClient = ClientBuilder.newClient();

        String jsonResult =   webhoseClient.target(WEBHOSE_URI + WEBHOSE_API_KEY + SEARCH_QUERY)
                .request(MediaType.APPLICATION_JSON).get(String.class);

        JSONObject jsonObject = new JSONObject(jsonResult);
        JSONArray postArray = jsonObject.getJSONArray("posts");

        NewsArticle tempNewsArticle;
        Polarity polarity;
        newsArticles = new ArrayList<>();

        for(int i = 0; i < NUMBER_OF_RESULTS; i++) {

            JSONObject threads = postArray.getJSONObject(i).getJSONObject("thread");
            JSONObject articles = postArray.getJSONObject(i);
            String source = threads.getString("site");
            String content = articles.getString("text");
            polarity = SentimentAnalyser.classifySentiment(content, "Document");
            tempNewsArticle = new NewsArticle(source, content, polarity.getPolarity());
            tempNewsArticle.setPolarityValue(polarity.getPolarityValue());

            newsArticles.add(tempNewsArticle);
        }
    }

    public List<NewsArticle> getNewsArticles() {
        return newsArticles;
    }

}
