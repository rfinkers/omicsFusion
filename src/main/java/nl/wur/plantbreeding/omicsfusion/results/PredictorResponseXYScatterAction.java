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

import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

/**
 * Show a XY scatterplot for a predictor / response variable.
 *
 * @author Richard Finkers
 * @version 2.0.
 */
public class PredictorResponseXYScatterAction
        extends PredictorResponseXYScatterForm {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 100906L;
    /**
     * The Logger
     */
    private static final Logger LOG = Logger.getLogger(
            PredictorResponseXYScatterAction.class.getName());
    /**
     * List with points.
     */
    private List<Point> points;
    /**
     * Output stream.
     */
    private HttpServletResponse response;
    /**
     * the request. TODO: use RequestAware (map version) instead!
     */
    private HttpServletRequest request;

//    @Actions({
//        @Action(value = "/charts", results = {
//            @Result(location = "/results/predRespXYScatter.jsp", name = "success")
//        }),
//        @Action(value = "/jsonchartdata", results = {
//            @Result(location = "/results/predRespXYScatter.jsp", name = "success")
//        })
//    })
    @Actions({
        @Action(value = "/results/predRespXYScatter", results = {
            @Result(location = "/results/predRespXYScatter.jsp", name = "success")
        })
    })
    @Override
    public String execute() throws Exception {
        LOG.info("Starting chart class");

        String predictor = null;
        try {
            predictor = request.getHeader("referer");
            String[] split = predictor.split("=");
            predictor = split[1].trim();
        }
        catch (Exception e) {
            //TODO: Check
            LOG.severe("Error in parsing header: referer");
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
                LOG.info("PredictorResponseXYScatterAction: SessionName was "
                        + "null!");
            } else {
                addActionError(getText("errors.response.variable"));
                LOG.info("PredictorResponseXYScatterAction: resposne "
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
                LOG.log(Level.SEVERE, "Exception: {0}", e.toString());
                e.printStackTrace();
            } else {
                LOG.severe("Exception occurred.");
                e.printStackTrace();
            }
        }

        LOG.info("got data");
        //TODO: null check.
        LOG.info("DataSet size: " + xyDataset.size());

        LOG.info("Chart created");

        return SUCCESS;
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

    public HttpServletResponse getServletResponse() {
        return response;
    }
}
