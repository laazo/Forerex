package com.services;

import model.ForeignCurrency;
import sentiment.extraction.ExchangeRateExtractor;

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

    @Path("/getBaseExchangeRates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBaseExchangeRates() {

        List<ForeignCurrency> tempList = Arrays.asList(new ForeignCurrency("USD", new ExchangeRateExtractor().getRandDollar()),
                                                     new ForeignCurrency("EUR", new ExchangeRateExtractor().getRandEuro()),
                                                    new ForeignCurrency("GBP", new ExchangeRateExtractor().getRandPound()));
        GenericEntity<List<ForeignCurrency>> entity;

        entity = new GenericEntity<List<ForeignCurrency>>(tempList) {};

        return Response.ok(entity).build();
    }
}
