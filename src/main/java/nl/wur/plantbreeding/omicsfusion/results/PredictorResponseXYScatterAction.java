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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYToolTipGenerator;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYUrlGenerator;
import nl.wur.plantbreeding.omicsfusion.excel.DataSheetValidationException;
import nl.wur.plantbreeding.omicsfusion.excel.ReadExcelSheet;
import nl.wur.plantbreeding.omicsfusion.utils.ReadFile;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * Show a XY scatterplot for a predictor / response variable.
 * @author Richard Finkers
 * @version 1.0.
 */
public class PredictorResponseXYScatterAction
        extends PredictorResponseXYScatterForm implements ServletRequestAware {

    /** Serial Version UID. */
    private static final long serialVersionUID = 100906L;
    /** Chart object. */
    private JFreeChart chart;
    /** the request. */
    private HttpServletRequest request;//TODO: use RequestAware (map version) instead!

    @Override
    public String execute() throws Exception {

        String predictor = null;
        try {
            predictor = request.getHeader("referer");
            String[] split = predictor.split("=");
            predictor = split[1].trim();
            if (predictor.startsWith("X.")) {
                predictor = predictor.substring(2);
            } else if (predictor.startsWith("X")) {//TODO: not a perfect solution!!
                predictor = predictor.substring(1);
            }
        }
        catch (Exception e) {
            //TODO: Check
            LOG.warning("Error in parsing header: referer");
            e.printStackTrace();
        }
        String sessionName =
                (String) request.getSession().getAttribute("resultSession");
        String response =
                (String) request.getSession().getAttribute("responseName");
        //response vs continues or response vs discrete.

        if (sessionName == null || response == null) {

            if (sessionName == null) {
                addActionError("Your session has been expired. Please re-enter "
                        + "your analysis ID");
                LOG.warning("PredictorResponseXYScatterAction: SessionName was "
                        + "null!");
            } else {
                addActionError("An error was detected in your input data. "
                        + "Please re-enter your analysis ID");
                LOG.warning("PredictorResponseXYScatterAction: resposne "
                        + "variable was null!");
            }
            return ERROR;
        }

        //Should also include model summaries?
        LOG.info("get data");
        DefaultXYDataset xyDataset = null;
        try {
            xyDataset = getDataSet(predictor, sessionName);
        }
        catch (InvalidFormatException invalidFormatException) {
            addActionError("You have used an undocumented input format for "
                    + "your omicsFusion run (likely a cvs file). We currently "
                    + "do not support viewing the XY scatter for this format!");
            LOG.log(Level.INFO, "XYScatter, invalid format: {0}",
                    invalidFormatException.getMessage());
        }
        catch (FileNotFoundException e) {
            addActionError("File not found");
        }
        catch (DataSheetValidationException e) {
            addActionError("response variable not found in the "
                    + "orgiginal sheet");
        }
        catch (Exception e) {
            addActionError("Exception occured.");
            //This one will be catched when no valid input format is available.
            //Due to the forward, the exception message gets lost?
            if (e.getMessage() != null) {
                LOG.log(Level.WARNING, "Exception: {0}", e.toString());
                e.printStackTrace();
            }else{
                LOG.warning("Exception occured.");
                e.printStackTrace();
            }
        }

        LOG.info("got data");

        //X (predictor) and Y axis (response)
        ValueAxis yAxis = new NumberAxis("Response: " + response);
        ValueAxis xAxis = new NumberAxis("Predictor: " + predictor);
        xAxis.setAutoRange(true);

        //TODO: imagemap
        //TODO: regression line?

        //Renderer
        DefaultXYItemRenderer renderer = new DefaultXYItemRenderer();
        //Options for the XY scatter (line 0)
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesPaint(1, Color.BLUE);
        //Otions for the regression line (line 1)
        renderer.setSeriesShapesVisible(1, Boolean.FALSE);
        renderer.setSeriesPaint(1, Color.BLACK);
        renderer.setBaseOutlinePaint(Color.WHITE);

        //Tooltip
        XYToolTipGenerator tooltipGen = new GenotypeXYToolTipGenerator();
        renderer.setSeriesToolTipGenerator(0, tooltipGen);

        //URL generator
        XYURLGenerator urlGen = new GenotypeXYUrlGenerator();
        renderer.setURLGenerator(urlGen);

        Plot plot = new XYPlot(xyDataset, xAxis, yAxis, renderer);
        plot.setNoDataMessage("NO DATA");

        // set my chart variable
        chart = new JFreeChart(
                response + " vs " + predictor,
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                false);
        chart.setBackgroundPaint(java.awt.Color.white);

        //ImageMapUtilities.writeImageMap(new PrintWriter(request.get  .response.getWriter()), NONE, null);

        LOG.info("Chart created");

        return SUCCESS;
    }

    /**
     * Create a random test dataset of 100 xy points.
     * @return  A random test dataset.
     */
    private DefaultXYDataset getTestDataSet() {
        // chart creation logic...
        int nrOfDataPoints = 100;

        double[][] data = new double[2][nrOfDataPoints];
        String[] genotypeLabels = new String[nrOfDataPoints];

        for (int i = 0; i < nrOfDataPoints; i++) {
            data[0][i] = i;//predictor
            data[1][i] = Math.random();//response
            genotypeLabels[i] = "test";
        }
        DefaultXYDataset xy = new GenotypeXYDataset("Genotype", data,
                genotypeLabels, genotypeLabels);
        return xy;
    }

    /**
     * Read two data sheets and parse the right columns for the XY scatter.
     * @param predictor Name of the predictor variable.
     * @param sessionID SessionID of the original analysis run.
     * @return XYDataset.
     * @throws InvalidFormatException Not an excel compatible sheet!
     */
    private DefaultXYDataset getDataSet(String predictor, String sessionID)
            throws InvalidFormatException, FileNotFoundException, IOException,
            DataSheetValidationException, Exception {
        //read the filenames from filenames.txt
        ReadFile rf = new ReadFile();
        //The filenames are stored in this string array
        String fileNames[] = null;

        try {
            fileNames = rf.ReadSheetFileNames(getResultsDir() + sessionID
                    + "/filenames.txt");
            //currently only valid for excel sheets as input. Otherwise throw error.
        }
        catch (IOException ex) {
            Logger.getLogger(
                    PredictorResponseXYScatterAction.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        //Read the names of the predictor and response file
        String responseFile = getResultsDir() + sessionID + "/"
                + fileNames[0].trim();
        String predictorFile = getResultsDir() + sessionID + "/"
                + fileNames[1].trim();

        File predFile = new File(predictorFile);
        File respFile = new File(responseFile);
        DefaultXYDataset readPredictorAndResponseValue = null;

        LOG.info("try");
        readPredictorAndResponseValue =
                ReadExcelSheet.readPredictorAndResponseValue(respFile,
                predFile, predictor);

        return readPredictorAndResponseValue;
    }

    public JFreeChart getChart() {
        return chart;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Get the temp directory from the system.
     * @return the location of the temp directory (including slash or backslash).
     */
    private String getResultsDir() {
        //String resultsDirectory = System.getProperty("java.io.tmpdir");
        String resultsDirectory =
                request.getSession().getServletContext().
                getInitParameter("resultsDirectory");
        if (!( resultsDirectory.endsWith("/")
                || resultsDirectory.endsWith("\\") )) {
            resultsDirectory += System.getProperty("file.separator");
        }
        return resultsDirectory;
    }
}
