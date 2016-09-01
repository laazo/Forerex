package sentiment.extraction;

import model.NewsArticle;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by azola.ndamase on 12-Aug-16.
 */
public class NewsArticleExtractor {

    private static final String WEBHOSE_API_KEY = "e9ab4f02-6c24-4ea8-a25d-d5c68f51274f";
    private List<NewsArticle> newsArticles;

    public void retrieveNewsArticles() {
        Client webhoseClient = ClientBuilder.newClient();

        String searchQuery = "&format=json&q=the%20rand";
        String jsonResult =   webhoseClient.target("http://webhose.io/search?token="+ WEBHOSE_API_KEY + searchQuery)
                .request(MediaType.APPLICATION_JSON).get(String.class);
        JSONObject jsonObject = new JSONObject(jsonResult);
        JSONArray postArray = jsonObject.getJSONArray("thread");
        System.out.print(postArray.length()+ "length here");
        NewsArticle tempNewsArticle = new NewsArticle();
        newsArticles = new ArrayList<>();

        for(int i = 0; i < postArray.length(); i++) {

            JSONObject articles = postArray.getJSONObject(i);
            String source = articles.getString("site");
            String content = articles.getString("text");
            tempNewsArticle.setSource(source);
            tempNewsArticle.setContent(content);
            tempNewsArticle.setScore(0);
            newsArticles.add(tempNewsArticle);

        }

    }

    public List<NewsArticle> getNewsArticles() {
        return newsArticles;
    }

    public void setNewsArticles(List<NewsArticle> newsArticles) {
        this.newsArticles = newsArticles;
    }
}
