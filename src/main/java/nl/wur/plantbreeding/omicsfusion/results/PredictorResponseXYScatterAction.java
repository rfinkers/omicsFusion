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

import com.almworks.sqlite4java.SQLiteException;
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
import nl.wur.plantbreeding.logic.sqlite4java.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.excel.DataSheetValidationException;
import nl.wur.plantbreeding.omicsfusion.excel.ReadExcelSheet;
import nl.wur.plantbreeding.omicsfusion.utils.ReadFile;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
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
 *
 * @author Richard Finkers
 * @version 1.0.
 */
public class PredictorResponseXYScatterAction
        extends PredictorResponseXYScatterForm implements ServletRequestAware {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 100906L;
    /**
     * Chart object.
     */
    private JFreeChart chart;
    /**
     * the request.
     */
    private HttpServletRequest request;//TODO: use RequestAware (map version) instead!

    @Override
    public String execute() throws Exception {

        String predictor = null;
        try {
            predictor = request.getHeader("referer");
            String[] split = predictor.split("=");
            predictor = split[1].trim();
        }
        catch (Exception e) {
            //TODO: Check
            LOG.warning("Error in parsing header: referer");
            e.printStackTrace();
            addActionError(getText("errors.reading.predictor"));
            return ERROR;
        }
        String sessionName =
                (String) request.getSession().getAttribute("resultSession");
        String response =
                (String) request.getSession().getAttribute("responseName");
        //response vs continues or response vs discrete.

        if (sessionName == null || response == null) {

            if (sessionName == null) {
                addActionError(getText("errors.session.expired"));
                LOG.warning("PredictorResponseXYScatterAction: SessionName was "
                        + "null!");
            } else {
                addActionError(getText("errors.response.variable"));
                LOG.warning("PredictorResponseXYScatterAction: resposne "
                        + "variable was null!");
            }
            return ERROR;
        }

        //Should also include model summaries?
        LOG.info("get data");
        DefaultXYDataset xyDataset = null;
        try {
            xyDataset = getDataSetFromXLS(predictor, sessionName);
        }
        catch (InvalidFormatException invalidFormatException) {
            addActionError(getText("errors.xyscatter.data"));
            LOG.log(Level.INFO, "XYScatter, invalid format: {0}",
                    invalidFormatException.getMessage());
        }
        catch (FileNotFoundException e) {
            addActionError(getText("errors.file.not.found"));
        }
        catch (DataSheetValidationException e) {
            addActionError(getText("errors.resonse.not.found"));
        }
        catch (Exception e) {
            addActionError(getText("errors.general.exception"));
            //This one will be catched when no valid input format is available.
            //Due to the forward, the exception message gets lost?
            if (e.getMessage() != null) {
                LOG.log(Level.WARNING, "Exception: {0}", e.toString());
                e.printStackTrace();
            } else {
                LOG.warning("Exception occurred.");
                e.printStackTrace();
            }
        }

        LOG.info("got data");
        LOG.log(Level.INFO, "DataSet size: {0}", xyDataset.getSeriesCount());

        //X (predictor) and Y axis (response)
        ValueAxis yAxis = new NumberAxis(getText("response.text") + ": "
                + response);
        ValueAxis xAxis = new NumberAxis(getText("predictor.text") + ": "
                + predictor);
        xAxis.setAutoRange(true);

        //TODO: imagemap


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
     *
     * @return A random test dataset.
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
     *
     * @param predictor Name of the predictor variable.
     * @param sessionID SessionID of the original analysis run.
     * @return XYDataset.
     * @throws InvalidFormatException Not an excel compatible sheet!
     */
    private DefaultXYDataset getDataSetFromXLS(String predictor,
            String sessionID)
            throws InvalidFormatException, FileNotFoundException, IOException,
            DataSheetValidationException, Exception {
        //read the filenames from filenames.txt
        ReadFile rf = new ReadFile();
        //The filenames are stored in this string array
        String fileNames[] = new String[2];

        //FIXME: cleanup code
        //        try {
//            fileNames = rf.ReadSheetFileNames(ServletUtils.getResultsDir(request, sessionID)
//                    + "/filenames.txt");
//            //currently only valid for excel sheets as input. Otherwise throw error.
//        }
//        catch (IOException ex) {
//            Logger.getLogger(
//                    PredictorResponseXYScatterAction.class.getName()).
//                    log(Level.SEVERE, null, ex);
//        }

        //Read the names of the names of the predictor and response file.
        SqLiteQueries sql = new SqLiteQueries();
        fileNames[0] = sql.getResponseSheetName(
                ServletUtils.getResultsDir(request, sessionID));
        fileNames[1] = sql.getPredictorSheetName(
                ServletUtils.getResultsDir(request, sessionID));

        LOG.log(Level.INFO, "File: {0} and {1}",
                new Object[]{fileNames[0], fileNames[1]});

        //Read the predictor and response file.
        String responseFile = ServletUtils.getResultsDir(request, sessionID)
                + "/" + fileNames[0].trim();
        String predictorFile = ServletUtils.getResultsDir(request, sessionID)
                + "/" + fileNames[1].trim();

        File predFile = new File(predictorFile);
        File respFile = new File(responseFile);
        DefaultXYDataset readPredictorAndResponseValue = null;

        LOG.info("try");
        readPredictorAndResponseValue =
                ReadExcelSheet.readPredictorAndResponseValue(respFile,
                predFile, predictor);

        return readPredictorAndResponseValue;
    }

        /**
     * Read two data for the XY scatter.
     *
     * @param predictor Name of the predictor variable.
     * @param response Name of the response (trait) to show in the plot.
     * @param sessionID SessionID of the original analysis run.
     * @return XYDataset.
     */
    private DefaultXYDataset getDataSetFromDB(String predictor, String response,
            String sessionID) throws SQLiteException {

        //connect to the db.
        SqLiteQueries sql = new SqLiteQueries();

        //Read the data for the predictor (order by)

        //Read the data for the response (order by)

        //combine the two lists (check the name order)

        //We need a DefaultXYDataset.

        //We want to include an regression line (and plot the R2).



//        fileNames[0] = sql.getResponseSheetName(
//                ServletUtils.getResultsDir(request, sessionID));
//        fileNames[1] = sql.getPredictorSheetName(
//                ServletUtils.getResultsDir(request, sessionID));
//
//        LOG.log(Level.INFO, "File: {0} and {1}",
//                new Object[]{fileNames[0], fileNames[1]});
//
//        //Read the predictor and response file.
//        String responseFile = ServletUtils.getResultsDir(request, sessionID)
//                + "/" + fileNames[0].trim();
//        String predictorFile = ServletUtils.getResultsDir(request, sessionID)
//                + "/" + fileNames[1].trim();

//        File predFile = new File(predictorFile);
//        File respFile = new File(responseFile);
        DefaultXYDataset readPredictorAndResponseValue = null;

        LOG.info("try");
//        readPredictorAndResponseValue =
//                ReadExcelSheet.readPredictorAndResponseValue(respFile,
//                predFile, predictor);

        return readPredictorAndResponseValue;
    }

    public JFreeChart getChart() {
        return chart;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
