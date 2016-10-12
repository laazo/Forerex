package model;

/**
 * Created by azola.ndamase on 04-Jun-16.
 */

public class NewsArticle {

    private int id;
    private String source;
    private String content;
    private String score;

    public NewsArticle(String source, String content, String score) {
        this.source = source;
        this.content = content;
        this.score = score;
    }

    public int getId() {
        return id;
    }

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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
