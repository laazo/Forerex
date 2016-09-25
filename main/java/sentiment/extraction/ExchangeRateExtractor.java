package sentiment.extraction;

import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Created by azola.ndamase on 13-Aug-16.
 */
public class ExchangeRateExtractor {

    private double randDollar;
    private double randEuro;
    private double randPound;

    public ExchangeRateExtractor() {
        setExchangeRates();
    }

    private void setExchangeRates() {
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
    }

    public double getRandDollar() {
        return Double.parseDouble(String.format("%.2f", randDollar));
    }

    public void setRandDollar(double randDollar) {
        this.randDollar = randDollar;
    }

    public double getRandEuro() {
        return Double.parseDouble(String.format("%.2f", randEuro));
    }

    public void setRandEuro(double randEuro) {
        this.randEuro = randEuro;
    }

    public double getRandPound() {
        return Double.parseDouble(String.format("%.2f", randPound));
    }

    public void setRandPound(double randPound) {
        this.randPound = randPound;
    }
}
