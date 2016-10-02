package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by azola.ndamase on 04-Jun-16.
 */
@Entity
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String source;
    private String content;
    private int score;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
