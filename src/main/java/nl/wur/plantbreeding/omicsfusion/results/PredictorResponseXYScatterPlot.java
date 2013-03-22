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
package nl.wur.plantbreeding.omicsfusion.results;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math.stat.regression.SimpleRegression;

/**
 *
 * @author finke002
 *
 */
public class PredictorResponseXYScatterPlot {

    /**
     * The logger.
     */
    private static final Logger LOG =
            Logger.getLogger(PredictorResponseXYScatterPlot.class.getName());

    /**
     * Create a XY dataset, and add the regression line.
     *
     * @param observationsForPredictorAndResponse scatter data.
     * @return Struts2-jquery compatible regression line dataset..
     */
    protected Map<Double, Double> getRegressionLine(
            Map<Double, Double> points) {

        double[][] data =
                new double[2][points.size()];
        String[] genotypeLabels =
                new String[points.size()];

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        //add the object for regression
        SimpleRegression slr = new SimpleRegression();

        //initialize z which holds the correct row number.
        int z = 0;

        Iterator<Map.Entry<Double, Double>> observations;
        observations = points.entrySet().iterator();

        int i = 0;
        while (observations.hasNext()) {
            Map.Entry<Double, Double> obs = observations.next();
            //FIXME: chech how empty / null values are obtained from the DB.
            LOG.log(Level.INFO, i + " response: {0} predictor: {1}",
                    new Object[]{obs.getKey(), obs.getValue()});
            i++;
            data[0][z] = obs.getKey(); // response -> Y
            data[1][z] = obs.getValue(); // predictor -> X
//            if (obs.getGenotypeName() != null) {
//                genotypeLabels[z] = obs.getGenotypeName();
//            } else {
//                genotypeLabels[z] = "No label";
//            }
            //Get the min and max response variable for regression.
            if (data[0][z] > maxX) {
                maxX = data[0][z];
            }
            if (data[0][z] < minX) {
                minX = data[0][z];
            }
            //Add data to regression dataset (x,y) = pred, resp
            slr.addData(data[0][z], data[1][z]);
            z++;
//            }
        }

        //Predict a new y values for the extreme X?
        double[][] regLine = new double[2][2];

        LOG.log(Level.INFO,
                "preY: {0} predY: {1}",
                new Object[]{slr.predict(minX), slr.predict(maxX)
        });
        regLine[0][0] = minX;
        regLine[1][0] = slr.predict(minX);
        regLine[0][1] = maxX;
        regLine[1][1] = slr.predict(maxX);
        /**
         * The dataset
         */
        Map<Double, Double> regDataSet = new HashMap<Double, Double>();

        //add the regression series to the dataSet.
        regDataSet.put(minX, slr.predict(minX));
        regDataSet.put(maxX, slr.predict(maxX));

        return regDataSet;
    }
}
