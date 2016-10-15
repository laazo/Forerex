package model;

import java.util.Date;

/**
 * Created by azola.ndamase on 01-Sep-16.
 */

public class ForeignCurrency {


    private int id;

    private String symbol;
    private Double valueInRands;
    private Date lastUpdatedDate;

    public ForeignCurrency() {}

    public ForeignCurrency(String symbol, Double valueInRands) {
        this.symbol = symbol;
        this.valueInRands = valueInRands;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getValueInRands() {
        return valueInRands;
    }

    public void setValueInRands(Double valueInRands) {
        this.valueInRands = valueInRands;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}


