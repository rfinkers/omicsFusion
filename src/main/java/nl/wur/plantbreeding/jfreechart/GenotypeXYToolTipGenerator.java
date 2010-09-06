package nl.wur.plantbreeding.jfreechart;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Logger;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.data.xy.XYDataset;

/**
 * A standard series label generator for plots that use data from
 * an {@link nl.wur.plantbreeding.logic.jFreeChart.GenotypeXYDataset}.
 *
 * @author Richard Finkers
 * @version 0.1
 * @since 0.1
 */
public class GenotypeXYToolTipGenerator implements XYToolTipGenerator {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(GenotypeXYToolTipGenerator.class.getName());

    /**
     * Returns a Genotype XY values Tooltip
     * @param dataset the dataset (<code>null</code> not permitted).
     * @param series  the series index (zero-based).
     * @param item the item index (zero-based).
     * @return A tooltip containing the genotype name and the x,y values
     */
    @Override
    public String generateToolTip(XYDataset dataset, int series, int item) {
        NumberFormat formatter = new DecimalFormat("#.###");
        return ((GenotypeXYDataset) dataset).getGenotypeLabel(series, item)
                + " - (" + formatter.format(dataset.getX(series, item))
                + ", " + formatter.format(dataset.getY(series, item)) + ")";
    }
}
