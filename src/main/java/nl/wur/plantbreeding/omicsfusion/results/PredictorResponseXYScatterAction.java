/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYToolTipGenerator;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import nl.wur.plantbreeding.omicsfusion.excel.ReadExcelSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

        try {
            //Trim if not null.. otherwise throw exception 
            String predictor = request.getParameter("predictor");
            String response = (String) request.getAttribute("response");
            String session = request.getParameter("session");
            Enumeration<String> parameterNames = request.getParameterNames();
            
            while (parameterNames.hasMoreElements()) {
                System.out.println("Element: " + parameterNames.nextElement());
            }
            LOG.log(Level.INFO, "cur ses  : {0}", request.getRequestedSessionId());//This is the current ID, not the resultset ID
            LOG.log(Level.INFO, "Predictor: {0}", predictor);
            LOG.log(Level.INFO, "Response : {0}", response);
            LOG.log(Level.INFO, "Session  : {0}", session);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String session="d8933";
        String response="X.294_0182";
        String predictor="Brix_P";
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
        //FIXME: filenames currently hardcoded
       String predictorFile="/home/finke002/d8933/CE_Flesh.xls";
       String responseFile="/home/finke002/d8933/CE_Met.xls";
       
       File predFile = new File(predictorFile);
       File respFile = new File(responseFile);
       DefaultXYDataset readPredictorAndResponseValue;
        try {
            readPredictorAndResponseValue = ReadExcelSheet.readPredictorAndResponseValue(respFile, predFile, "X.294_0182");
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(PredictorResponseXYScatterAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InvalidFormatException ex) {
            Logger.getLogger(PredictorResponseXYScatterAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(PredictorResponseXYScatterAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        //DefaultXYDataset xy = new GenotypeXYDataset("Genotype", data, genotypeLabels, genotypeLabels)
        //return xy;
        return new GenotypeXYDataset("Genotype", null, null, null);
    }

    public JFreeChart getChart() {
        return chart;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
