/**
 * Created by azola.ndamase on 04-Jun-16.
 */

import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class AspectCloudBean {

    private TagCloudModel model;

    @PostConstruct
    public void init() {
        model = new DefaultTagCloudModel();
        model.addTag(new DefaultTagCloudItem("CreditRating", 1));
        model.addTag(new DefaultTagCloudItem("Petrol", "#", 3));
        model.addTag(new DefaultTagCloudItem("Elections", 2));
        model.addTag(new DefaultTagCloudItem("Parliament", "#", 5));
        model.addTag(new DefaultTagCloudItem("Inflation", 4));
        model.addTag(new DefaultTagCloudItem("QnA", "#", 2));
    }

    public TagCloudModel getModel() {
        return model;
    }
}
