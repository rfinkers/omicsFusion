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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Logger;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.data.xy.XYDataset;

/**
 * A standard series label generator for plots that use data from an
 * {@link GenotypeXYDataset}.
 *
 * @author Richard Finkers
 * @version 0.1
 * @since 0.1
 */
public class GenotypeXYToolTipGenerator implements XYToolTipGenerator {

    /** The logger. */
    private static final Logger LOG = Logger.getLogger(
            GenotypeXYToolTipGenerator.class.getName());

    /**
     * Returns a Genotype XY values Tooltip.
     * @param dataset the dataset (<code>null</code> not permitted).
     * @param series  the series index (zero-based).
     * @param item the item index (zero-based).
     * @return A tooltip containing the genotype name and the x,y values.
     */
    @Override
    public final String generateToolTip(XYDataset dataset, int series, int item) {
        NumberFormat formatter = new DecimalFormat("#.###");
        return ((GenotypeXYDataset) dataset).getGenotypeLabel(series, item)
                + " - (" + formatter.format(dataset.getX(series, item))
                + ", " + formatter.format(dataset.getY(series, item)) + ")";
    }
}
