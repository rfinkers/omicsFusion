/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYToolTipGenerator;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * Show a XY scatterplot for a predictor / response variable.
 * @author Richard Finkers
 * @version 1.0.
 */
public class PredictorResponseXYScatterAction extends PredictorResponseXYScatterForm implements ServletRequestAware {

     /** Serial Version UID */
    private static final long serialVersionUID = 100906L;
    /** Chart object */
    private JFreeChart chart;
    /** the request */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {

        String variable = request.getParameter("variable");

        LOG.info("Request: " + request.getRequestedSessionId());

        LOG.info("Variable: {0}", variable);

        //response vs continues or response vs discrete.
        
        //Should also include model summaries?

        DefaultXYDataset xyDataset = getDataSet();

        ValueAxis xAxis = new NumberAxis("Predictor");
        ValueAxis yAxis = new NumberAxis("Response");

        //XYURLGenerator urlGen = new GenotypeXYUrlGenerator();
        XYToolTipGenerator tooltipGen = new GenotypeXYToolTipGenerator();

        DefaultXYItemRenderer renderer = new DefaultXYItemRenderer();

        renderer.setSeriesToolTipGenerator(0, tooltipGen);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setBaseOutlinePaint(Color.WHITE);

        Plot plot = new XYPlot(xyDataset, xAxis, yAxis, renderer);
        plot.setNoDataMessage("NO DATA");
        
        // set my chart variable
        chart = new JFreeChart(
                "Predictor vs Response",//TODO: add custom title
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                false);
        chart.setBackgroundPaint(java.awt.Color.white);
        
        LOG.info("Chart created");

        return SUCCESS;
    }

    private DefaultXYDataset getDataSet() {
        // chart creation logic...
        //Read excel sheets

        //Get the relevant colomns
        //Get the column with genotype names

        //Parse the excel column in the data object. Deal with missig data (Omit from object?)

        //x: predictor and y: response

        double[][] data = new double[2][100];//TODO: 100 -> length results
        String[] genotypeLabels = new String[100];//TODO: 100 -> length results

        for (int i = 0; i < 100; i++) {
            data[0][i] = i;//predictor
            data[1][i] = Math.random();//response
            genotypeLabels[i] = "test";
        }
        DefaultXYDataset xy = new GenotypeXYDataset("Genotype", data, genotypeLabels, genotypeLabels);
        return xy;
    }

    private DefaultXYDataset getDataSet2() {

        //DefaultXYDataset xy = new GenotypeXYDataset("Genotype", data, genotypeLabels, genotypeLabels)
        //return xy;
        return  new GenotypeXYDataset(SUCCESS, null, null, null);
    }

    public JFreeChart getChart() {
        return chart;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
