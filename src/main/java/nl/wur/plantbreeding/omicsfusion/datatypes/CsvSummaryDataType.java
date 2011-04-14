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

    /** response variable */
    private String responsVariable;
    /** Mean observation */
    private Double mean;
    /** background color in table */
    private String htmlColor;
    /** Standard deviation */
    private Double sd;
    /** rank */
    private Double rank;
    /** The logger */
    private static final Logger LOG = Logger.getLogger(
            CsvSummaryDataType.class.getName());

    public CsvSummaryDataType() {
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public Double getRank() {
        return rank;
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }

    public String getResponsVariable() {
        return responsVariable;
    }

    public void setResponsVariable(String responsVariable) {
        this.responsVariable = responsVariable;
    }

    public Double getSd() {
        return sd;
    }

    public void setSd(Double sd) {
        this.sd = sd;
    }

    public String getHtmlColor() {
        return htmlColor;
    }

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
        hash = 79 * hash + (this.responsVariable != null
                ? this.responsVariable.hashCode() : 0);
        hash = 79 * hash + (this.mean != null ? this.mean.hashCode() : 0);
        hash = 79 * hash + (this.sd != null ? this.sd.hashCode() : 0);
        hash = 79 * hash + (this.rank != null ? this.rank.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CsvSummaryDataType{" + "responsVariable= " + responsVariable
                + " mean= " + mean + " htmlColor= " + htmlColor + " sd= " + sd
                + " rank= " + rank + '}';
    }
}
