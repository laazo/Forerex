package sentiment.analysis;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.trees.Tree;

/**
 * Created by azola.ndamase on 21-Jun-16.
 */
public class SentimentAnalyser {

    private static StanfordCoreNLP pipeline;

    public static void initialisePipeline() {
        pipeline = new StanfordCoreNLP("Forerex.properties");
    }

    public static int classifySentiment(String sentimentText) {
        int sentimentScore = 0;

        if(sentimentText != null && sentimentText.length() > 0){
            int longest = 0;

            Annotation annotation = pipeline.process(sentimentText);

            for(CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partialText = sentence.toString();
                if(partialText.length() > longest) {
                    sentimentScore = sentiment;
                    longest = partialText.length();
                }
            }
        }
        // range is [0, 4]
        return sentimentScore;
    }
    /***************************************************************************************
     *    Title: Twitter Sentiment Analysis in less than 100 lines of code!
     *    Author: Rahular
     *    Date: 23 March 2014
     *    Code version: 1.0
     *    Availability: http://rahular.com/twitter-sentiment-analysis/
     *
     ***************************************************************************************/
    public static int aggregateNewsArticleScore(String[] sentences) {
        int aggregateScore = 0;
        for(int i = 0; i < sentences.length; i++) {
            aggregateScore  += classifySentiment(sentences[i]);
        }
        return aggregateScore / sentences.length;
    }

    public static String getSentimentValue(int sentimentScore) {
        String [] results = {"Very Negative", "Negative", "Neutral", "Positive", "Very Positive"};
        return results[sentimentScore];
    }
}
