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
     * Name of the trait.
     */
    private String traitName;
    /**
     * The observation.
     */
    private double observation;

    public DataPointDataType() {
    }

    public DataPointDataType(String genotypeName, String traitName, double observation) {
        this.genotypeName = genotypeName;
        this.traitName = traitName;
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

    public String getTraitName() {
        return traitName;
    }

    public void setTraitName(String traitName) {
        this.traitName = traitName;
    }

    @Override
    public String toString() {
        return "DataPointDataType{" + "genotypeName=" + genotypeName + ", traitName=" + traitName + ", observation=" + observation + '}';
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
        if ((this.traitName == null) ? (other.traitName != null) : !this.traitName.equals(other.traitName)) {
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
        hash = 83 * hash + (this.traitName != null ? this.traitName.hashCode() : 0);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.observation) ^ (Double.doubleToLongBits(this.observation) >>> 32));
        return hash;
    }
}
