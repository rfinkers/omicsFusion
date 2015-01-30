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
 * @author Richard Finkers
 */
public class XYScatterDataType {

    /**
     * Name of the genotype.
     */
    private String genotypeName;
    /**
     * Name of the response variable.
     */
    private String responseVariable;
    /**
     * The response value.
     */
    private double responseValue;
    /**
     * Name of the predictor variable.
     */
    private String predictorVariable;
    /**
     * The predictor value.
     */
    private double predictorValue;

    public XYScatterDataType() {
    }

    public String getGenotypeName() {
        return genotypeName;
    }

    public void setGenotypeName(String genotypeName) {
        this.genotypeName = genotypeName;
    }

    public double getPredictorValue() {
        return predictorValue;
    }

    public void setPredictorValue(double predictorValue) {
        this.predictorValue = predictorValue;
    }

    public String getPredictorVariable() {
        return predictorVariable;
    }

    public void setPredictorVariable(String predictorVariable) {
        this.predictorVariable = predictorVariable;
    }

    public double getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(double responseValue) {
        this.responseValue = responseValue;
    }

    public String getResponseVariable() {
        return responseVariable;
    }

    public void setResponseVariable(String responseVariable) {
        this.responseVariable = responseVariable;
    }

    @Override
    public String toString() {
        return "XYScatterDataType{" + "genotypeName=" + genotypeName
                + ", responseVariable=" + responseVariable
                + ", responseValue=" + responseValue
                + ", predictorVariable=" + predictorVariable
                + ", predictorValue=" + predictorValue + '}';
    }
}
