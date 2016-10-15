package com.services;

import com.aylien.textapi.responses.Sentiment;
import data.SentimentDB;
import model.ForeignCurrency;
import sentiment.extraction.ExchangeRateExtractor;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Created by azola.ndamase on 01-Sep-16.
 */
@Path("/exchangeRate")
public class ExchangeRateService {

    private SentimentDB sentimentDB;
    private ExchangeRateExtractor exchangeRateExtractor;

    @PostConstruct
    public void init() {
        sentimentDB = new SentimentDB();
    }

    @Path("/getBaseExchangeRates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ForeignCurrency> getBaseExchangeRates() {

        List<ForeignCurrency> toReturn;

        if(sentimentDB.hasRecentExchangeRates()) {
            toReturn = sentimentDB.getBaseExchangeRates();

        }
        else {
            exchangeRateExtractor = new ExchangeRateExtractor();
            exchangeRateExtractor.setExchangeRates();
            toReturn = exchangeRateExtractor.getBaseForeignCurrencies();
            sentimentDB.saveBaseExchangeRates(toReturn);

        }

        return toReturn;
    }
}
