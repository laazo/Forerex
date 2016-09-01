package model;

/**
 * Created by azola.ndamase on 04-Jun-16.
 */
public class NewsArticle {

    private String source;
    private String content;
    private int score;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
