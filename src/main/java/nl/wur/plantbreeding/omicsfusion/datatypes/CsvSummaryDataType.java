/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.datatypes;

/**
 * Datatype containing the summary results.
 * @author Richard Finkers
 */
public class CsvSummaryDataType {

    /** response variable */
    private String responsVariable;
    /** Mean observatio */
    private Double mean;
    /** Standard deviation */
    private Double sd;
    /** rank */
    private Double rank;

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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.responsVariable != null ? this.responsVariable.hashCode() : 0);
        hash = 79 * hash + (this.mean != null ? this.mean.hashCode() : 0);
        hash = 79 * hash + (this.sd != null ? this.sd.hashCode() : 0);
        hash = 79 * hash + (this.rank != null ? this.rank.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CsvSummaryDataType{" + "responsVariable=" + responsVariable + "mean=" + mean + "sd=" + sd + "rank=" + rank + '}';
    }
}
