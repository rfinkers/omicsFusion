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
package nl.wur.plantbreeding.omicsfusion.datatypes;

import java.util.logging.Logger;

/**
 * Datatype containing the summary results.
 *
 * @author Richard Finkers
 */
public class SummaryDataType {

    /**
     * predictor variable.
     */
    private String predictorVariable;
    /**
     * predictor variable ID.
     */
    private String predictorVariableID;
    /**
     * Mean observation.
     */
    private Double mean;
    /**
     * background color in table.
     */
    private String htmlColor;
    /**
     * Standard deviation.
     */
    private Double sd;
    /**
     * rank.
     */
    private Double rank;
    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(
            SummaryDataType.class.getName());

    /**
     * default constructor.
     */
    public SummaryDataType() {
    }

    /**
     * Load the summary datatype with all information.
     *
     * @param predictorVariable Name of the predictor variable.
     * @param predictorVariableID ID of the predictor variable.
     * @param mean Mean value.
     * @param sd Standard deviation.
     * @param rank mean rank.
     */
    public SummaryDataType(String predictorVariable, String predictorVariableID,
            Double mean, Double sd, Double rank) {
        this.predictorVariable = predictorVariable;
        this.predictorVariableID = predictorVariableID;
        this.mean = mean;
        this.sd = sd;
        this.rank = rank;
    }

    /**
     * Constructor to fill the initial object.
     */
    /**
     * get the mean value.
     *
     * @return Mean value.
     */
    public Double getMean() {
        return mean;
    }

    /**
     * set the mean value.
     *
     * @param mean Mean value.
     */
    public void setMean(Double mean) {
        this.mean = mean;
    }

    /**
     * Get the rank.
     *
     * @return the rank.
     */
    public Double getRank() {
        return rank;
    }

    /**
     * set the rank.
     *
     * @param rank the rank.
     */
    public void setRank(Double rank) {
        this.rank = rank;
    }

    /**
     * Get the predictor variable.
     *
     * @return predictor variable.
     */
    public String getPredictorVariable() {
        return predictorVariable;
    }

    /**
     * Set the predictor variable.
     *
     * @param predictorVariable the predictor variable.
     */
    public void setPredictorVariable(String predictorVariable) {
        if (predictorVariable.startsWith("X.")) {
            this.predictorVariable = predictorVariable.substring(2);
            //TODO: not a perfect solution!!
        } else if (predictorVariable.startsWith("X")) {
            this.predictorVariable = predictorVariable.substring(1);
            //handle Col replacement here as well?
            //or not, as Col is used later to directly access the column.
        } else if (predictorVariable.startsWith("`")
                && predictorVariable.endsWith("`")) {
            this.predictorVariable = predictorVariable.replace("`", "").trim();
        } else {
            this.predictorVariable = predictorVariable;
        }
    }

    /**
     * Get the standard deviation.
     *
     * @return standard deviation.
     */
    public Double getSd() {
        return sd;
    }

    /**
     * Set the standard deviation.
     *
     * @param sd standard deviation.
     */
    public void setSd(Double sd) {
        this.sd = sd;
    }

    /**
     * Get the HTML color code.
     *
     * @return HTML color code.
     */
    public String getHtmlColor() {
        return htmlColor;
    }

    /**
     * Set the HTML color code.
     *
     * @param htmlColor HTML color code.
     */
    public void setHtmlColor(String htmlColor) {
        this.htmlColor = htmlColor;
    }

    /**
     * Obtain the ID of the predictor variable.
     *
     * @return predictor variable.
     */
    public String getPredictorVariableID() {
        return predictorVariableID;
    }

    /**
     * Set the ID of the predictor variable.
     *
     * @param predictorVariableID predictor variable.
     */
    public void setPredictorVariableID(String predictorVariableID) {
        this.predictorVariableID = predictorVariableID;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.predictorVariable != null
                ? this.predictorVariable.hashCode() : 0);
        hash = 79 * hash + (this.mean != null ? this.mean.hashCode() : 0);
        hash = 79 * hash + (this.sd != null ? this.sd.hashCode() : 0);
        hash = 79 * hash + (this.rank != null ? this.rank.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CsvSummaryDataType{" + "predictorVariable= " + predictorVariable
                + " mean= " + mean + " htmlColor= " + htmlColor + " sd= " + sd
                + " rank= " + rank + '}';
    }
}
