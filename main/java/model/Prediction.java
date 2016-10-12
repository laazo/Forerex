package model;

import java.util.Date;

/**
 * Created by azola.ndamase on 2016-10-02.
 */

public class Prediction {

    private int id;
    private Date timeOfDay;
    private ForeignCurrency foreignCurrency;

    public int getId() {
        return id;
    }

    public Date getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(Date timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public ForeignCurrency getForeignCurrency() {
        return foreignCurrency;
    }

    public void setForeignCurrency(ForeignCurrency foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }
}
