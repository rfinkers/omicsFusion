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
 * @author Richard Finkers
 */
public class CsvSummaryDataType {

    /** predictor variable. */
    private String predictorVariable;
    /** Mean observation. */
    private Double mean;
    /** background color in table. */
    private String htmlColor;
    /** Standard deviation. */
    private Double sd;
    /** rank. */
    private Double rank;
    /** The logger. */
    private static final Logger LOG = Logger.getLogger(
            CsvSummaryDataType.class.getName());

    /** default constructor. */
    public CsvSummaryDataType() {
    }

    /** get the mean value.
     * @return Mean value.
     */
    public Double getMean() {
        return mean;
    }

    /** set the mean value.
     * @param mean Mean value.
     */
    public void setMean(Double mean) {
        this.mean = mean;
    }

    /**
     * Get the rank.
     * @return the rank.
     */
    public Double getRank() {
        return rank;
    }

    /**
     * set the rank.
     * @param rank the rank.
     */
    public void setRank(Double rank) {
        this.rank = rank;
    }

    /**
     * Get the predictor variable.
     * @return predictor variable.
     */
    public String getPredictorVariable() {
        return predictorVariable;
    }

    /**
     * Set the predictor variable.
     * @param predictorVariable the predictor variable.
     */
    public void setPredictorVariable(String predictorVariable) {
        if (predictorVariable.startsWith("X.")) {
            this.predictorVariable = predictorVariable.substring(2);
            //TODO: not a perfect solution!!
        } else if (predictorVariable.startsWith("X")) {
            this.predictorVariable = predictorVariable.substring(1);
        }
    }

    /**
     * Get the standard deviation.
     * @return standard deviation.
     */
    public Double getSd() {
        return sd;
    }

    /**
     * Set the standard deviation.
     * @param sd standard deviation.
     */
    public void setSd(Double sd) {
        this.sd = sd;
    }

    /**
     * Get the HTML color code.
     * @return HTML color code.
     */
    public String getHtmlColor() {
        return htmlColor;
    }

    /**
     * Set the HTML color code.
     * @param htmlColor HTML color code.
     */
    public void setHtmlColor(String htmlColor) {
        this.htmlColor = htmlColor;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + ( this.predictorVariable != null
                ? this.predictorVariable.hashCode() : 0 );
        hash = 79 * hash + ( this.mean != null ? this.mean.hashCode() : 0 );
        hash = 79 * hash + ( this.sd != null ? this.sd.hashCode() : 0 );
        hash = 79 * hash + ( this.rank != null ? this.rank.hashCode() : 0 );
        return hash;
    }

    @Override
    public String toString() {
        return "CsvSummaryDataType{" + "responsVariable= " + predictorVariable
                + " mean= " + mean + " htmlColor= " + htmlColor + " sd= " + sd
                + " rank= " + rank + '}';
    }
}
