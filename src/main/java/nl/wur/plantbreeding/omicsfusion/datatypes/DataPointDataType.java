/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package nl.wur.plantbreeding.omicsfusion.datatypes;

/**
 *
 * @author finke002
 */
public class DataPointDataType {

    /**
     * Name of the genotype.
     */
    private String genotypeName;
    /**
     * Number of the genotype.
     */
    private Integer genotypeID;
    /**
     * Name of the observation.
     */
    private String observationName;
    /**
     * Number of the observation.
     */
    private String observationID;
    /**
     * The observation.
     */
    private double observation;

    public DataPointDataType() {
    }

    /**
     * Constructor to fill in the complete object.
     *
     * @param genotypeName Name of the genotype.
     * @param genotypeID ID of the genotype.
     * @param observationName Name of the predictor or response variable.
     * @param observationID ID of the predictor of the response variable.
     * @param observation The associated observation.
     */
    public DataPointDataType(String genotypeName, Integer genotypeID,
            String observationName, String observationID, double observation) {
        this.genotypeName = genotypeName;
        this.genotypeID = genotypeID;
        this.observationName = observationName;
        this.observationID = observationID;
        this.observation = observation;
    }

    public String getGenotypeName() {
        return genotypeName;
    }

    public void setGenotypeName(String genotypeName) {
        this.genotypeName = genotypeName;
    }

    public double getObservation() {
        return observation;
    }

    public void setObservation(double observation) {
        this.observation = observation;
    }

    public Integer getGenotypeID() {
        return genotypeID;
    }

    public void setGenotypeID(Integer genotypeID) {
        this.genotypeID = genotypeID;
    }

    public String getObservationName() {
        return observationName;
    }

    public void setObservationName(String observationName) {
        this.observationName = observationName;
    }

    public String getObservationID() {
        return observationID;
    }

    public void setObservationID(String observationID) {
        this.observationID = observationID;
    }

    @Override
    public String toString() {
        return "DataPointDataType{" + "genotypeName=" + genotypeName + ", traitName=" + observationName + ", observation=" + observation + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataPointDataType other = (DataPointDataType) obj;
        if ((this.genotypeName == null) ? (other.genotypeName != null) : !this.genotypeName.equals(other.genotypeName)) {
            return false;
        }
        if ((this.observationName == null) ? (other.observationName != null) : !this.observationName.equals(other.observationName)) {
            return false;
        }
        if (Double.doubleToLongBits(this.observation) != Double.doubleToLongBits(other.observation)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.genotypeName != null ? this.genotypeName.hashCode() : 0);
        hash = 83 * hash + (this.observationName != null ? this.observationName.hashCode() : 0);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.observation) ^ (Double.doubleToLongBits(this.observation) >>> 32));
        return hash;
    }
}
