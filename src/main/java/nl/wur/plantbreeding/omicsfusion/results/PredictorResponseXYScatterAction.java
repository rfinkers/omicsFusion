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

import com.opensymphony.xwork2.ognl.OgnlValueStack;
import java.awt.Color;
import java.awt.print.Pageable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYToolTipGenerator;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import nl.wur.plantbreeding.omicsfusion.excel.ReadExcelSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.views.util.UrlHelper;
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
public class PredictorResponseXYScatterAction extends PredictorResponseXYScatterForm implements ParameterAware, SessionAware, ServletRequestAware {

    /** Serial Version UID */
    private static final long serialVersionUID = 100906L;
    /** Chart object */
    private JFreeChart chart;
    /** the paramter map */
    private Map<String, String[]> parameters;
    /** the session map */
    private Map<String, Object> sessions;
    HttpServletRequest request;

    @Override
    public String execute() throws Exception {

        try {
            //Trim if not null.. otherwise throw exception
            
//            String predictor = request.getParameter("predictor");
//            String response = (String) request.getAttribute("response");
//            String session2 = getServleRequest().getParameter("session");
            System.out.println("URI: " + request.getAttribute("javax.servlet.forward.request_uri"));

        } catch (Exception e) {
            //TODO: Check
            e.printStackTrace();
        }

        //FIXME: hardcoded
        //String session = "d8933";
        //String response = "X.294_0182";
        //String predictor = "Brix_P";
        //response vs continues or response vs discrete.

        //Should also include model summaries?
        LOG.info("get data");
        DefaultXYDataset xyDataset = getDataSet();
        LOG.info("got data");
        ValueAxis xAxis = new NumberAxis("Predictor");
        ValueAxis yAxis = new NumberAxis("Response");

        //XYURLGenerator urlGen = new GenotypeXYUrlGenerator();
        //urlGen.generateURL(xyDataset, 1, 1);
        XYToolTipGenerator tooltipGen = new GenotypeXYToolTipGenerator();
        //dtooltipGen.generateToolTip(xyDataset, 25, 25);


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

    private DefaultXYDataset getTestDataSet() {
        // chart creation logic...
        //Read excel sheets

        //Get the relevant colomns
        //Get the column with genotype names

        //Parse the excel column in the data object. Deal with missig data (Omit from object?)

        //x: predictor and y: response

        int nrOfDataPoints = 100;

        double[][] data = new double[2][nrOfDataPoints];
        String[] genotypeLabels = new String[nrOfDataPoints];

        for (int i = 0; i < nrOfDataPoints; i++) {
            data[0][i] = i;//predictor
            data[1][i] = Math.random();//response
            genotypeLabels[i] = "test";
        }
        DefaultXYDataset xy = new GenotypeXYDataset("Genotype", data, genotypeLabels, genotypeLabels);
        return xy;
    }

    private DefaultXYDataset getDataSet() {
        //FIXME: filenames currently hardcoded
        String responseFile = "/home/finke002/omicsFusion/d8933/CE_Flesh.xls";
        String predictorFile = "/home/finke002/omicsFusion/d8933/CE_Met.xls";

        File predFile = new File(predictorFile);
        File respFile = new File(responseFile);
        DefaultXYDataset readPredictorAndResponseValue = null;
        try {
            LOG.info("try");
            readPredictorAndResponseValue = ReadExcelSheet.readPredictorAndResponseValue(respFile, predFile, "294_0182");//TODO: trim X.?
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PredictorResponseXYScatterAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(PredictorResponseXYScatterAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PredictorResponseXYScatterAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        //DefaultXYDataset xy = new GenotypeXYDataset("Genotype", data, genotypeLabels, genotypeLabels)
        //return xy;
        return readPredictorAndResponseValue;
    }

    public JFreeChart getChart() {
        return chart;
    }

//    @Override
//    public void setServletRequest(HttpServletRequest request) {
//        this.request = request;
//    }
//
//    public HttpServletRequest getServleRequest() {
//        return request;
//    }
    @Override
    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.sessions = session;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}