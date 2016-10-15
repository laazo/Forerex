package sentiment.extraction;

import model.ForeignCurrency;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by azola.ndamase on 13-Aug-16.
 */
public class ExchangeRateExtractor {

    private double randDollar;
    private double randEuro;
    private double randPound;
    private List<ForeignCurrency> foreignCurrencyList;

    public ExchangeRateExtractor() {

    }

    public void setExchangeRates() {
        foreignCurrencyList = new ArrayList<>();

        Client client = ClientBuilder.newClient();
        String jsonResult =  client.target("http://api.fixer.io/latest?base=USD").request(MediaType.APPLICATION_JSON).get(String.class);

        JSONObject jsonObject = new JSONObject(jsonResult);
        randDollar = jsonObject.getJSONObject("rates").getDouble("ZAR");

        jsonResult = client.target("http://api.fixer.io/latest?base=EUR").request(MediaType.APPLICATION_JSON).get(String.class);
        jsonObject = new JSONObject(jsonResult);
        randEuro = jsonObject.getJSONObject("rates").getDouble("ZAR");

        jsonResult = client.target("http://api.fixer.io/latest?base=GBP").request(MediaType.APPLICATION_JSON).get(String.class);
        jsonObject = new JSONObject(jsonResult);
        randPound = jsonObject.getJSONObject("rates").getDouble("ZAR");

        client.close();

        foreignCurrencyList.add(new ForeignCurrency("USD", randDollar));
        foreignCurrencyList.add(new ForeignCurrency("EUR", randEuro));
        foreignCurrencyList.add(new ForeignCurrency("GBP", randPound));

    }

    public List<ForeignCurrency> getBaseForeignCurrencies() {
        return foreignCurrencyList;
    }

}
