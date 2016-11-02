package sentiment.extraction;

/**
 * Created by azola.ndamase on 2016-11-01.
 */
public class Polarity {
    private String polarity;
    private double polarityValue;

    public Polarity(String polarity, double polarityValue) {
        this.polarity = polarity;
        this.polarityValue = polarityValue;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public double getPolarityValue() {
        return polarityValue;
    }

    public void setPolarityValue(double polarityValue) {
        this.polarityValue = polarityValue;
    }
}
