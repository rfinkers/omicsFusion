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

import java.sql.SQLException;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wur.plantbreeding.logic.sqlite.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.datatypes.XYScatterDataType;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Show a XY scatterplot for a predictor / response variable.
 *
 * @author Richard Finkers
 * @version 2.0.
 */
@Results({
    @Result(location = "/results/predRespXYScatter.jsp", name = "success", type = "json"),
    @Result(location = "/results/showResultsSummary.jsp", name = "error")
})
public class PredictorResponseXYScatterAction
        extends PredictorResponseXYScatterForm implements ServletRequestAware {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 100906L;
    /**
     * The Logger.
     */
    private static final Logger LOG = Logger.getLogger(
            PredictorResponseXYScatterAction.class.getName());
    /**
     * List with points.
     */
    private Map<Double, Double> points;
    /**
     * List with regression line.
     */
    private Map<Double, Double> regression;
    /**
     * Output stream.
     */
    private HttpServletResponse response;
    /**
     * the request. TODO: use RequestAware (map version) instead!
     */
    private HttpServletRequest request;

    @Actions({
        @Action(value = "/results/predRespXYScatter", results = {
            @Result(location = "/results/predRespXYScatter.jsp", name = "success")
        })
    })
    @Override
    public String execute() throws Exception {
        LOG.info("Starting PredRespXYScatter Action class");

        String sessionName
                = (String) request.getSession().getAttribute("resultSession");
        String responseVariable
                = (String) request.getSession().getAttribute("responseName");
        LOG.log(Level.INFO, "Response: {0}", responseVariable);
        //response vs continues or response vs discrete.

        if (sessionName == null || responseVariable == null) {
            if (sessionName == null) {
                addActionError(getText("errors.session.expired"));
                LOG.info("PredictorResponseXYScatterAction: SessionName was "
                        + "null!");
            } else {
                addActionError(getText("errors.response.variable"));
                LOG.info("PredictorResponseXYScatterAction: resposne "
                        + "variable was null!");
            }
            return ERROR;
        }

        List<XYScatterDataType> dataSet = null;
        String predictorName = null;
        try {
            dataSet = getDataSetFromDB(getPredictor(),
                    responseVariable, sessionName);
            predictorName = getPredictorNameFromDB(getPredictor(), sessionName);
        } catch (Exception e) {
            addActionError(getText("errors.general.exception"));
            //This one will be catched when no valid input format is available.
            //Due to the forward, the exception message gets lost?
            if (e.getMessage() != null) {
                LOG.log(Level.SEVERE, "Exception: {0}", e.toString());
            } else {
                LOG.log(Level.SEVERE, "Exception occurred: {0}", e.getMessage());
            }
            return ERROR;
        }

        if (dataSet == null || dataSet.isEmpty()) {
            addActionError("No datapoints obtained from database");
            return ERROR;
        }

        if (predictorName == null) {
            addActionError("No response name read from database");
            return ERROR;
        }

        request.getSession().setAttribute("predictorName", predictorName);

        PredictorResponseXYScatterPlot pl = new PredictorResponseXYScatterPlot();
        regression = pl.getRegressionLine(dataSet);

        //Finally, parse to the map (removes redunant response varables :(
        points = new IdentityHashMap<>(dataSet.size());
        for (XYScatterDataType data : dataSet) {
            points.put(data.getPredictorValue(), data.getResponseValue());
        }

        return SUCCESS;
    }

    /**
     * Read two data for the XY scatter.
     *
     * @param predictor Name of the predictor variable.
     * @param response Name of the response (trait) to show in the plot.
     * @param sessionID SessionID of the original analysis run.
     */
    private List<XYScatterDataType> getDataSetFromDB(String predictor,
            String response, String sessionID)
            throws SQLException, ClassNotFoundException {

        //connect to the db.
        SqLiteQueries sql = new SqLiteQueries();
        //Read the data for the predictor & response (order by
        List<XYScatterDataType> predictResponseXYScatterPlotDataSet
                = sql.getObservationsForPredictorAndResponse(
                        ServletUtils.getResultsDir(request, sessionID),
                        predictor, response);

        return predictResponseXYScatterPlotDataSet;
    }

    String getPredictorNameFromDB(String predictor, String sessionID)
            throws ClassNotFoundException, SQLException {

        //connect to the db.
        SqLiteQueries sql = new SqLiteQueries();
        //Read the name from the DB.
        return sql.getPredictorNameFromDB(
                ServletUtils.getResultsDir(request, sessionID), predictor);
    }

    /**
     * The points for the graph.
     *
     * @return data points.
     */
    public Map<Double, Double> getPoints() {
        return points;
    }

    /**
     * The regression line.
     *
     * @return regression line.
     */
    public Map<Double, Double> getRegression() {
        return regression;
    }

    /**
     * The HTTP response.
     *
     * @return response.
     */
    public HttpServletResponse getServletResponse() {
        return response;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
