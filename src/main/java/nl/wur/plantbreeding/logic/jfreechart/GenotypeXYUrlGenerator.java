package nl.wur.plantbreeding.logic.jfreechart;

import java.util.logging.Logger;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.xy.XYDataset;

/**
 * A URL generator.
 * @author Richard Finkers
 * @version 0.1
 * @since 0.1
 */
public class GenotypeXYUrlGenerator implements XYURLGenerator {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(GenotypeXYUrlGenerator.class.getName());

    /**
     * Generates a URL for a particular item within a series. As a guideline,
     * the URL should be valid within the context of an XHTML 1.0 document.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return A string containing the generated URL (possibly
     *         <code>null</code>).
     */
    @Override
    public String generateURL(XYDataset dataset, int series, int item) {
        return "genotypeDetails.do?genoID=" + ((GenotypeXYDataset) dataset).getAccessionLabel(series, item);
    }
}
