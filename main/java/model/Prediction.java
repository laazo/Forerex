package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by azola.ndamase on 2016-10-02.
 */
@Entity
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
