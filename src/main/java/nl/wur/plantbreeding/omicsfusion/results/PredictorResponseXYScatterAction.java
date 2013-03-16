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
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import nl.wur.plantbreeding.logic.sqlite.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.datatypes.XYScatterDataType;
import nl.wur.plantbreeding.omicsfusion.excel.DataSheetValidationException;
import nl.wur.plantbreeding.omicsfusion.excel.ReadExcelSheet;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * Show a XY scatterplot for a predictor / response variable.
 *
 * @author Richard Finkers
 * @version 1.0.
 */
@Actions({
    @Action(value = "/charts", results = {
        @Result(location = "/results/predRespXYScatter.jsp", name = "success")
    }),
    @Action(value = "/jsonchartdata", results = {
        @Result(location = "/results/predRespXYScatter.jsp", name = "success")
    })
})
public class PredictorResponseXYScatterAction
        extends PredictorResponseXYScatterForm
        implements ServletRequestAware, ServletResponseAware {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 100906L;
    /**
     * List with points.
     */
    private List<Point> points;
    /**
     * Chart object.
     */
    private JFreeChart chart;
    /**
     * Output stream.
     */
    private HttpServletResponse response;
    /**
     * the request. TODO: use RequestAware (map version) instead!
     */
    private HttpServletRequest request;

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
        List<Point> xyDataset = null;
        try {
            xyDataset = getDataSetFromDB(predictor, response, sessionName);
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
        //TODO: null check.
        LOG.log(Level.INFO, "DataSet size: {0}", xyDataset.size());

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
        //renderer.setBaseOutlinePaint(Color.WHITE); TODO: removed by new API

        //Tooltip - GenotypeXYToolTipGenerator
        XYToolTipGenerator tooltipGen = new StandardXYToolTipGenerator();
        renderer.setSeriesToolTipGenerator(0, tooltipGen);

        //URL generator
//        XYURLGenerator urlGen = new GenotypeXYUrlGenerator();
//        renderer.setURLGenerator(urlGen);

//        Plot plot = new XYPlot(xyDataset, xAxis, yAxis, renderer);
//        plot.setNoDataMessage("NO DATA");
//
//        // set my chart variable
//        chart = new JFreeChart(
//                response + " vs " + predictor,
//                JFreeChart.DEFAULT_TITLE_FONT,
//                plot,
//                false);
//        chart.setBackgroundPaint(java.awt.Color.white);


        try {
            ChartRenderingInfo info = new ChartRenderingInfo(
                    new StandardEntityCollection());

//            PrintWriter w = new PrintWriter(
//                    getServletResponse().getWriter());
//
//            ChartUtilities.writeImageMap(w,
//                    "#imageMap", info, false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

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

        //The filenames are stored in this string array
        String fileNames[] = new String[2];

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
     * @deprecated Remove in favor of struts2-jquery chart.
     */
    private DefaultXYDataset getDataSetFromDB2(String predictor, String response,
            String sessionID) throws SQLException, ClassNotFoundException {

        //connect to the db.
        SqLiteQueries sql = new SqLiteQueries();
        //Read the data for the predictor & response (order by)
        ArrayList<XYScatterDataType> observationsForPredictorAndResponse =
                sql.getObservationsForPredictorAndResponse(
                ServletUtils.getResultsDir(request, sessionID),
                predictor, response);

        //We need a DefaultXYDataset.
        PredictorResponseXYScatterPlot xyp =
                new PredictorResponseXYScatterPlot();
        DefaultXYDataset predictResponseXYScatterPlotDataSet =
                xyp.predictResponseXYScatterPlotOld(
                observationsForPredictorAndResponse);

        return predictResponseXYScatterPlotDataSet;
    }

    /**
     * Read two data for the XY scatter.
     *
     * @param predictor Name of the predictor variable.
     * @param response Name of the response (trait) to show in the plot.
     * @param sessionID SessionID of the original analysis run.
     * @return XYDataset.
     */
    private List<Point> getDataSetFromDB(String predictor, String response,
            String sessionID) throws SQLException, ClassNotFoundException {

        //connect to the db.
        SqLiteQueries sql = new SqLiteQueries();
        //Read the data for the predictor & response (order by)
        ArrayList<XYScatterDataType> observationsForPredictorAndResponse =
                sql.getObservationsForPredictorAndResponse(
                ServletUtils.getResultsDir(request, sessionID),
                predictor, response);

        //We need a DefaultXYDataset.
        PredictorResponseXYScatterPlot xyp =
                new PredictorResponseXYScatterPlot();
        List<Point> predictResponseXYScatterPlotDataSet =
                xyp.predictResponseXYScatterPlot(
                observationsForPredictorAndResponse);

        return predictResponseXYScatterPlotDataSet;
    }

    public List<Point> getPoints() {
        return points;
    }

    public JFreeChart getChart() {
        return chart;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getServletResponse() {
        return response;
    }
}
