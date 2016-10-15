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

        model.addTag(new DefaultTagCloudItem("USDZAR" + " - R14", 3));
        model.addTag(new DefaultTagCloudItem("EURZAR" + " - R17", 2));
        model.addTag(new DefaultTagCloudItem("GBPZAR" + " - R19", 1));

    }

    public TagCloudModel getModel() {
        return model;
    }
}
