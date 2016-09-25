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

    public static int classifySentiment(String tweet) {
        int sentimentScore = 0;

        if(tweet != null && tweet.length() > 0){
            int longest = 0;

           Annotation annotation = pipeline.process(tweet);
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
}
