/**
 * Created by azola.ndamase on 04-Jun-16.
 */

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

@ManagedBean
public class ForecastChart implements Serializable {

    private double getRandomNumber() {
        double MIN = 14.0;
        double MAX = 20.0;
        return ThreadLocalRandom.current().nextDouble(MIN, MAX + 1);
    }

}
