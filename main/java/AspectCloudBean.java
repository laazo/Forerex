/**
 * Created by azola.ndamase on 04-Jun-16.
 */

import model.Tweet;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;
import sentiment.extraction.ExchangeRateExtractor;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class AspectCloudBean {

    private TagCloudModel model;

    @PostConstruct
    public void init() {
        model = new DefaultTagCloudModel();

        model.addTag(new DefaultTagCloudItem("USDZAR" + " - R" + new ExchangeRateExtractor().getRandDollar(), 3));
        model.addTag(new DefaultTagCloudItem("EURZAR" + " - R" + new ExchangeRateExtractor().getRandEuro(), 2));
        model.addTag(new DefaultTagCloudItem("GBPZAR" + " - R" + new ExchangeRateExtractor().getRandPound(), 1));

    }

    public TagCloudModel getModel() {
        return model;
    }
}
