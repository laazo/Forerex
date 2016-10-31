package sentiment.analysis;

/**
 * Created by azola.ndamase on 2016-10-30.
 */
public class PolarityCount {

    private int polarityClass;
    private int counts;

    public PolarityCount(int polarityClass, int counts) {
        this.polarityClass = polarityClass;
        this.counts = counts;
    }
}
