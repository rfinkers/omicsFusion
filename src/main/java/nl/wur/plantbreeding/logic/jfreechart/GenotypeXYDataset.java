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
package nl.wur.plantbreeding.logic.jfreechart;

import org.jfree.data.xy.DefaultXYDataset;

/**
 * A convenience class that provides an implementation of the GenotypeXYDataset
 * using the DefaultXYDataset interface. The standard constructor accepts data
 * in a two dimensional array where the first dimension is the series, and the
 * second dimension is the category ({@link org.jfree.data.xy.XYDataset}).
 * Two additional arrays are used to provide the genotype names and the
 * accession ID.
 *
 * @author Richard Finkers
 * @since 0.1
 * @version 0.1
 *
 */
public class GenotypeXYDataset extends DefaultXYDataset {

    /** serialVersionUID. */
    private static final long serialVersionUID = 070110L;
    /* A String array of genotype names. */
    private String[] genotypeNames;
    /* A String array of accession IDs. */
    private String[] accessionIDs;

    /**
     * Constructs a new genotype dataset, and populates it with the given data.
     * <P>
     * The dimensions of the data array are [series][item][x=0, y=1]. The
     * x-values should be Number or Date objects, the y-values should be Number
     * objects. Any other types are interpreted as zero. The data will be
     * sorted so that the x-values are ascending. The genotype name and
     * accessionID arrays should be in the same order than the data array.
     *

     * @param seriesKey the series key (null not permitted).
     * @param data the data (must be an array with length 2, containing two
     * arrays of equal length, the first containing the x-values and the second
     * containing the y-values).
     * @param genotypeNames an array containing the genotype names. This array
     * must be in the same order as the data.
     * @param accessionIDs an array containing the accession IDs. This array
     * must be in the same order as the data.
     */
    public GenotypeXYDataset(Comparable seriesKey, double[][] data,
            String[] genotypeNames, String[] accessionIDs) {
        super.addSeries(seriesKey, data);
        this.genotypeNames = genotypeNames;
        this.accessionIDs = accessionIDs;
    }

    /**
     * returns the genotype name for the specified index (zero-based indices).
     * @param series not used. maintained for compatibility with jFreechart.
     * @param index The index of the item (zero-based).
     * @return The genotype name.
     */
    public String getGenotypeLabel(int series, int index) {
        return genotypeNames[index];
    }

    /**
     * returns the accession ID for the specified index (zero-based indices).
     * @param series not used. maintained for compatibility with jFreechart.
     * @param index The index of the item (zero-based).
     * @return The accession ID.
     */
    public String getAccessionLabel(int series, int index) {
        return accessionIDs[index];
    }
}
