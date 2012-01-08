/*
 * Copyright 2012 omicstools.
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

/**
 *
 * @author finke002
 */
public class SummaryResults {
    /** predictor variable. */
    private String predictorVariable;
        /** Method. */
    private String method;
    /** Mean observation. */
    private Double mean;
    /** Standard deviation. */
    private Double sd;
    /** rank. */
    private Double rank;

    public SummaryResults(){}

    public SummaryResults(String predictorVariable, String method, Double mean, Double sd, Double rank) {
        this.predictorVariable = predictorVariable;
        this.method = method;
        this.mean = mean;
        this.sd = sd;
        this.rank = rank;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPredictorVariable() {
        return predictorVariable;
    }

    public void setPredictorVariable(String predictorVariable) {
        this.predictorVariable = predictorVariable;
    }

    public Double getRank() {
        return rank;
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }

    public Double getSd() {
        return sd;
    }

    public void setSd(Double sd) {
        this.sd = sd;
    }



}
