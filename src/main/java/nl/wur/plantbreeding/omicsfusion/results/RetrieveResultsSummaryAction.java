/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.util.FileOrDirectoryExists;
import nl.wur.plantbreeding.omicsfusion.datatypes.CsvSummaryDataType;
import nl.wur.plantbreeding.omicsfusion.utils.CSV;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;

/**
 * Action class that summarizes the results of the pipeline. Although the
 * confirmation for each result is only sended at the end of the run, this
 * action can already be used to retrieve the results from the intermediate
 * result sets.
 * @author Richard Finkers
 * @version 1.0
 */
public class RetrieveResultsSummaryAction extends RetrieveResultsSummaryValidationForm {

    /** The Logger */
    private static final Logger LOG = Logger.getLogger(RetrieveResultsSummaryAction.class.getName());
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {
        //Empty result objects for all possible methods
        ArrayList<CsvSummaryDataType> lasso = null;
        ArrayList<CsvSummaryDataType> ridge = null;
        ArrayList<CsvSummaryDataType> rf = null;
        ArrayList<CsvSummaryDataType> en = null;
        ArrayList<CsvSummaryDataType> pcr = null;
        ArrayList<CsvSummaryDataType> pls = null;
        ArrayList<CsvSummaryDataType> spls = null;
        ArrayList<CsvSummaryDataType> svm = null;
        ArrayList<CsvSummaryDataType> univariate = null;

        //Check the availability of one or more respults file for this sessionID.
        //All potential summary files are scanned. Only methods with results are
        //added tot the HashMap.
        HashMap<String, ArrayList<CsvSummaryDataType>> methResults = getMethodsWithResultsSummaryFiles(getSessionId());
        if (methResults.isEmpty()) {
            addActionError("No results found");
            return ERROR;
        }

        int[] resultRows = new int[9];
        int resultRowsCounter = 0;

        // Make each results matrix available and add the HTML color code which
        // is used for highlighting the results.
        if (methResults.get("lasso") != null) {
            lasso = methResults.get("lasso");
            addTableBackgroundColors(lasso);
            resultRows[resultRowsCounter] = lasso.size();
            resultRowsCounter++;
        }
        if (methResults.get("ridge") != null) {
            ridge = methResults.get("ridge");
            addTableBackgroundColors(ridge);
            resultRows[resultRowsCounter] = ridge.size();
            resultRowsCounter++;
        }
        if (methResults.get("rf") != null) {
            rf = methResults.get("rf");
            addTableBackgroundColors(rf);
            resultRows[resultRowsCounter] = rf.size();
            resultRowsCounter++;
        }
        if (methResults.get("pcr") != null) {
            pcr = methResults.get("pcr");
            addTableBackgroundColors(pcr);
            resultRows[resultRowsCounter] = pcr.size();
            resultRowsCounter++;
        }
        if (methResults.get("pls") != null) {
            pls = methResults.get("pls");
            addTableBackgroundColors(pls);
            resultRows[resultRowsCounter] = pls.size();
            resultRowsCounter++;
        }
        if (methResults.get("spls") != null) {
            spls = methResults.get("spls");
            addTableBackgroundColors(spls);
            resultRows[resultRowsCounter] = spls.size();
            resultRowsCounter++;
        }
        if (methResults.get("svm") != null) {
            svm = methResults.get("svm");
            addTableBackgroundColors(svm);
            resultRows[resultRowsCounter] = svm.size();
            resultRowsCounter++;
        }
        if (methResults.get("en") != null) {
            en = methResults.get("en");
            addTableBackgroundColors(en);
            resultRows[resultRowsCounter] = en.size();
            resultRowsCounter++;
        }
        if (methResults.get("univariate") != null) {
            univariate = methResults.get("univariate");
            addTableBackgroundColors(univariate);
            resultRows[resultRowsCounter] = univariate.size();
            resultRowsCounter++;
        }

        //lenght of all resulst arrays should be the same!
        int oldResultRows = 0;
        for (int row : resultRows) {
            if (row != 0) {
                if (oldResultRows != 0) {
                    if (row != oldResultRows) {
                        addActionError("Lengt of the results does not match");
                        return ERROR;
                    }
                }
                oldResultRows = row;
            }
        }

        //TODO: add mean rank and use this for sorting (or instead of for loop?).
        int[][] rank = new int[oldResultRows][2];
        for (int i = 0; i < oldResultRows; i++) {
            int count = 0;
            int sumRank = 0;
            if (methResults.get("lasso") != null) {
                sumRank += lasso.get(i).getRank();
                count++;
            }
            if (methResults.get("ridge") != null) {
                sumRank += ridge.get(i).getRank();
                count++;
            }
            if (methResults.get("rf") != null) {
                sumRank += rf.get(i).getRank();
                count++;
            }
            if (methResults.get("pcr") != null) {
                sumRank += pcr.get(i).getRank();
                count++;
            }
            if (methResults.get("pls") != null) {
                sumRank += pls.get(i).getRank();
                count++;
            }
            if (methResults.get("spls") != null) {
                sumRank += spls.get(i).getRank();
                count++;
            }
            if (methResults.get("svm") != null) {
                sumRank += svm.get(i).getRank();
                count++;
            }
            if (methResults.get("en") != null) {
                sumRank += en.get(i).getRank();
                count++;
            }
            if (methResults.get("univariate") != null) {
                sumRank += univariate.get(i).getRank();
                count++;
            }
            rank[i][0] = i;
            rank[i][1] = sumRank / count;
        }


        for (int[] is : rank) {
            System.out.println("Nr: " + is[0] + " rank: " + is[1]);
        }
        //Sort the array according to the rank
        sortRankArray(rank);
        System.out.println("Sorted");
        for (int[] is : rank) {
            System.out.println("Nr: " + is[0] + " rank: " + is[1]);
        }

        DecimalFormat df = new DecimalFormat("#.###");

        //Concatenate the HTML table.
        String table = "<table class='boxpart'>";
        //Add the header row.
        //TODO: resource bundle
        table += "<tr><th>Predictor</th>";
        if (methResults.get("univariate") != null) {
            table += "<th style='background-color: #330099;'>Univariate</th>";
        }
        if (methResults.get("rf") != null) {
            table += "<th style='background-color: #FF0000;'>Random Forest</th>";
        }
        if (methResults.get("svm") != null) {
            table += "<th style='background-color: #FF0000;'>SVM</th>";
        }
        if (methResults.get("pcr") != null) {
            table += "<th style='background-color: #00CC66;'>PCR</th>";
        }
        if (methResults.get("pls") != null) {
            table += "<th style='background-color: #00CC66;'>PLS</th>";
        }
        if (methResults.get("ridge") != null) {
            table += "<th style='background-color: #00CC66;'>Ridge</th>";
        }
        if (methResults.get("lasso") != null) {
            table += "<th style='background-color: #FFCC00;'>Lasso</th>";
        }
        if (methResults.get("en") != null) {
            table += "<th style='background-color: #FFCC00;'>Elastic net</th>";
        }
        if (methResults.get("spls") != null) {
            table += "<th style='background-color: #FFCC00;'>SPLS</th>";
        }
        table += "</tr>\n";

        //Add data to the table
        for (int i = 0; i < lasso.size(); i++) {
            int element = rank[i][0];
            table += "<tr align='right'>";
            //Only add the rowname once (from the first available result set).
            if (methResults.get("univariate") != null) {
                table += "<td>" + univariate.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("rf") != null) {
                table += "<td>" + lasso.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("svm") != null) {
                table += "<td>" + svm.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("pcr") != null) {
                table += "<td>" + pcr.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("pls") != null) {
                table += "<td>" + pls.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("ridge") != null) {
                table += "<td>" + ridge.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("lasso") != null) {
                table += "<td>" + rf.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("en") != null) {
                table += "<td>" + en.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("univariate") != null) {
                table += "<td>" + univariate.get(element).getResponsVariable() + "</td>";
            } else if (methResults.get("spls") != null) {
                table += "<td>" + spls.get(element).getResponsVariable() + "</td>";
            }
            //Results
            if (methResults.get("univariate") != null) {
                table += "<td bgcolor=\"" + univariate.get(element).getHtmlColor() + "\">" + df.format(univariate.get(element).getMean()) + " (" + df.format(univariate.get(element).getSd()) + ")</td>";
            }
            if (methResults.get("rf") != null) {
                table += "<td bgcolor=\"" + rf.get(element).getHtmlColor() + "\">" + df.format(rf.get(element).getMean()) + " (" + df.format(rf.get(element).getSd()) + ")</td>";
            }
            if (methResults.get("svm") != null) {
                table += "<td bgcolor=\"" + svm.get(element).getHtmlColor() + "\">" + df.format(svm.get(element).getMean()) + " (" + df.format(svm.get(element).getSd()) + ")</td>";
            }
            if (methResults.get("pcr") != null) {
                table += "<td bgcolor=\"" + pcr.get(element).getHtmlColor() + "\">" + df.format(pcr.get(element).getMean()) + " (" + df.format(pcr.get(element).getSd()) + ")</td>";
            }
            if (methResults.get("pls") != null) {
                table += "<td bgcolor=\"" + pls.get(element).getHtmlColor() + "\">" + df.format(pls.get(element).getMean()) + " (" + df.format(pls.get(element).getSd()) + ")</td>";
            }
            if (methResults.get("ridge") != null) {
                table += "<td bgcolor=\"" + ridge.get(element).getHtmlColor() + "\">" + df.format(ridge.get(element).getMean()) + " (" + df.format(ridge.get(element).getSd()) + ")</td>";
            }
            if (methResults.get("lasso") != null) {
                if (lasso.get(element).getMean() != 0) {
                    table += "<td bgcolor=\"" + lasso.get(element).getHtmlColor() + "\">" + df.format(lasso.get(element).getMean()) + " (" + df.format(lasso.get(element).getSd()) + ")</td>";
                }
            }
            if (methResults.get("en") != null) {
                if (en.get(element).getMean() != 0) {
                    table += "<td bgcolor=\"" + en.get(element).getHtmlColor() + "\">" + df.format(en.get(element).getMean()) + " (" + df.format(en.get(element).getSd()) + ")</td>";
                }
            }
            if (methResults.get("spls") != null) {
                if (spls.get(element).getMean() != 0) {
                    table += "<td bgcolor=\"" + spls.get(element).getHtmlColor() + "\">" + df.format(spls.get(element).getMean()) + " (" + df.format(spls.get(element).getSd()) + ")</td>";
                }
            }
            table += "</tr>\n";
        }
        table += "</table>";
        //To allow tests without usage of request scope?
        if (ServletUtils.getServletRequest() != null) {
            HttpServletRequest servletRequest = ServletUtils.getServletRequest();
            servletRequest.setAttribute("table", table);
        }
        return SUCCESS;
    }

    /**
     * Sort the multidimensional results array.
     * @param rank
     */
    private void sortRankArray(int[][] rank) {
        int[] temp;
        boolean sort;
        do {
            sort = true;
            for (int i = 0; i < rank.length - 1; i++) {
                if (rank[i][1] < rank[i + 1][1]) {
                    temp = rank[i];
                    rank[i] = rank[i + 1];
                    rank[i + 1] = temp;
                    sort = false;
                }
            }
        } while (!sort);
    }

    /**
     *
     * @param results
     */
    private void addTableBackgroundColors(ArrayList<CsvSummaryDataType> results) {
        //Initialize to the extreme values.
        Double min = Double.POSITIVE_INFINITY;
        Double max = Double.NEGATIVE_INFINITY;
        //Find the absolute min and max for this dataset.
        for (CsvSummaryDataType csvSummaryDataType : results) {
            Double value = csvSummaryDataType.getMean();
            if (Math.abs(value) < min) {
                min = value;
            }
            if (Math.abs(value) > max) {
                max = value;
            }
        }
        //Create a table (with background colors).
        for (CsvSummaryDataType csvSummaryDataType : results) {
            csvSummaryDataType.setHtmlColor(getBackgroundColor(csvSummaryDataType.getMean(), min, max));
        }
    }

    private HashMap<String, ArrayList<CsvSummaryDataType>> getMethodsWithResultsSummaryFiles(String sessionID) throws FileNotFoundException, IOException {
        HashMap<String, ArrayList<CsvSummaryDataType>> results = new HashMap<String, ArrayList<CsvSummaryDataType>>();
        //FIXME: hardcoded
        String tempDir = "/home/finke002/Desktop/d89339e9c510a1e4e13ce46cc02b/";//Work
//        String tempDir = "/home/finke002/Desktop/e125586fcf9ba1b02a33093a2c17ex/";//CE Flesh
//        String tempDir = "/home/finke002/Desktop/81df58ab8635eaea6211020de5b5/";//BRIX


        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "LASSO_coef_Sum.csv") == true) {
            results.put("lasso", CSV.readSummaryCsv(tempDir + "LASSO_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "RIDGE_coef_Sum.csv") == true) {
            results.put("ridge", CSV.readSummaryCsv(tempDir + "RIDGE_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "RF_varImp_Summary.csv") == true) {
            results.put("rf", CSV.readSummaryCsv(tempDir + "RF_varImp_Summary.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "EN_coef_Sum.csv") == true) {
            results.put("en", CSV.readSummaryCsv(tempDir + "EN_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "PCR_coef_Sum.csv") == true) {
            results.put("pcr", CSV.readSummaryCsv(tempDir + "PCR_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "PLS_coef_Sum.csv") == true) {
            results.put("pls", CSV.readSummaryCsv(tempDir + "PLS_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "SPLS_coef_Sum.csv") == true) {
            results.put("spls", CSV.readSummaryCsv(tempDir + "SPLS_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "SVM_coef_Sum.csv") == true) {
            results.put("svm", CSV.readSummaryCsv(tempDir + "SVM_coef_Sum.csv"));
        } //Disable univariate for now
        //if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "UNIVARIATE_coef_Sum.csv") == true) {
        //    results.put("lasso", CSV.readSummaryCsv(tempDir + "UNIVARIATE_coef_Sum.csv"));
        //}
        return results;
    }

    /**
     * Get the background color for the table according to a gradient of 20
     * colors from white to blue.
     * @param result
     * @param min
     * @param max
     * @return String containing the color code.
     */
    protected String getBackgroundColor(Double result, Double min, Double max) {
        String background = "#ffffff"; //White is default color.

        Double range = Math.abs(max) - Math.abs(min);
        Double value = Math.abs(result) - Math.abs(min);
        range *= 1000;
        value *= 1000;
        int group = (int) Math.abs(value / ( range / 20 ));
        //Part of the largest observations resolves to a value > 20 using
        //this equation. Set them manually to the max value.
        if (group > 20) {
            group = 20;
        }
        System.out.println("Color Code: " + group);
        switch (group) {
            case 1:
                background = "#f3f4f8";
                break;
            case 2:
                background = "#e7eaf0";
                break;
            case 3:
                background = "#dbdfe9";
                break;
            case 4:
                background = "#ced4e1";
                break;
            case 5:
                background = "#c2cada";
                break;
            case 6:
                background = "#b6bfd2";
                break;
            case 7:
                background = "#aab4cb";
                break;
            case 8:
                background = "#9ea9c3";
                break;
            case 9:
                background = "#929fbc";
                break;
            case 10:
                background = "#8694b4";
                break;
            case 11:
                background = "#7989ad";
                break;
            case 12:
                background = "#6d7fa5";
                break;
            case 13:
                background = "#61749e";
                break;
            case 14:
                background = "#556996";
                break;
            case 15:
                background = "#495f8f";
                break;
            case 16:
                background = "#3d5487";
                break;
            case 17:
                background = "#304980";
                break;
            case 18:
                background = "#243e78";
                break;
            case 19:
                background = "#183471";
                break;
            case 20:
                background = "#183471";
                break;
            case 21:
                background = "#183471";
                break;
            default:
                //background = "#183471";
                background = "#ffffff";
                break;
        }
        return background;
    }
}
