package sentiment.prediction;

/**
 * Created by azola.ndamase on 2016-11-02.
 */
public class PredictionEntry {
    private double randValue;
    private int hour;

    public PredictionEntry(double randValue, int hour) {
        this.randValue = randValue;
        this.hour = hour;
    }

    public double getRandValue() {
        return randValue;
    }

    public void setRandValue(double randValue) {
        this.randValue = randValue;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
