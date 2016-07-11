/**
 * Created by azola.ndamase on 04-Jun-16.
 */

import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

@ManagedBean
public class ForecastChart implements Serializable {

    private LineChartModel lineModel;

    @PostConstruct
    public void init() {
        createLineModel();
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    private void createLineModel() {
        lineModel = initForecastModel();

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);

        lineModel = initForecastModel();
        lineModel.setTitle("Rand Versus Dollar");
        lineModel.setLegendPosition("e");
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Date"));
        yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Value");
        yAxis.setMin(0);
        yAxis.setMax(25);
    }

    private LineChartModel initForecastModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries randDollarSeries = new LineChartSeries();
        randDollarSeries.setLabel("Rand to Dollar");

        randDollarSeries.set("05 Jun", getRandomNumber());
        randDollarSeries.set("06 Jun", getRandomNumber());
        randDollarSeries.set("07 Jun", getRandomNumber());
        randDollarSeries.set("08 Jun", getRandomNumber());
        randDollarSeries.set("09 Jun", getRandomNumber());

        model.addSeries(randDollarSeries);

        return model;
    }

    private double getRandomNumber() {
        double MIN = 14.0;
        double MAX = 20.0;
        return ThreadLocalRandom.current().nextDouble(MIN, MAX + 1);
    }

}
