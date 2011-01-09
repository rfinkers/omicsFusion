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
package nl.wur.plantbreeding.omicsfusion.results;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.util.FileOrDirectoryExists;
import nl.wur.plantbreeding.omicsfusion.datatypes.CsvSummaryDataType;
import nl.wur.plantbreeding.omicsfusion.utils.CSV;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Action class that summarizes the results of the pipeline. Although the
 * confirmation for each result is only sended at the end of the run, this
 * action can already be used to retrieve the results from the intermediate
 * result sets.
 * @author Richard Finkers
 * @version 1.0
 */
public class RetrieveResultsSummaryAction extends RetrieveResultsSummaryValidationForm implements ServletRequestAware {

    /** The Logger */
    private static final Logger LOG = Logger.getLogger(RetrieveResultsSummaryAction.class.getName());
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;
    /** the request */
    private HttpServletRequest request;

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
        ArrayList<CsvSummaryDataType> univariate_p = null;
        ArrayList<CsvSummaryDataType> univariate_bh = null;

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
        if (methResults.get("univariate_p") != null) {
            univariate_p = methResults.get("univariate_p");
            addTableBackgroundColors(univariate_p);//FIXME: add different background colors (on pValues?) to Univariate
            resultRows[resultRowsCounter] = univariate_p.size();
            resultRowsCounter++;
        }
        if (methResults.get("univariate_bh") != null) {
            univariate_bh = methResults.get("univariate_bh");
            addTableBackgroundColors(univariate_bh);//FIXME: add different background colors (on pValues?) to Univariate
            resultRows[resultRowsCounter] = univariate_bh.size();
            resultRowsCounter++;
        }

        //lenght of all resulst arrays should be the same!
        int oldResultRows = 0;
        for (int row : resultRows) {
            if (row != 0) {
                if (oldResultRows != 0 && row != oldResultRows) {
                    addActionError("Lengt of the results does not match");//TODO: resource bundle
                    LOG.log(Level.WARNING, "Number of rows set A: {0}", oldResultRows);
                    LOG.log(Level.WARNING, "Number of rows set B: {0}", row);
                    return ERROR;
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
            if (methResults.get("univariate_p") != null) {
                sumRank += univariate_p.get(i).getRank();
                count++;
            }
            if (methResults.get("univariate_bh") != null) {
                sumRank += univariate_bh.get(i).getRank();
                count++;
            }
            rank[i][0] = i;
            rank[i][1] = sumRank / count;
        }


        //Sort the array according to the rank
        sortRankArray(rank);

        String baseURL = request.getContextPath();
        DecimalFormat df = new DecimalFormat("#.###");
        //Concatenate the HTML table.
        String table = "<table class='boxpart'>";
        //TODO: resource bundle
        //FIXME: get response from the filesystem
        table += "<tr><th>set repsonse name</th>";
        if (methResults.get("univariate_p") != null) {
            table += "<th style='background-color: #330099;'><a href=''>Univariate pval</a></th>";
        }
        if (methResults.get("univariate_bh") != null) {
            table += "<th style='background-color: #330099;'><a href=''>Univariate BH</a></th>";
        }
        if (methResults.get("rf") != null) {
            table += "<th style='background-color: #FF0000;'><a href=''>Random Forest</a></th>";
        }
        if (methResults.get("svm") != null) {
            table += "<th style='background-color: #FF0000;'><a href=''>SVM</a></th>";
        }
        if (methResults.get("pcr") != null) {
            table += "<th style='background-color: #00CC66;'><a href=''>PCR</a></th>";
        }
        if (methResults.get("pls") != null) {
            table += "<th style='background-color: #00CC66;'><a href=''>PLS</a></th>";
        }
        if (methResults.get("ridge") != null) {
            table += "<th style='background-color: #00CC66;'><a href='/results/ridge.jsp'>Ridge</a></th>";
        }
        if (methResults.get("lasso") != null) {
            table += "<th style='background-color: #FFCC00;'><a href=''>Lasso</a></th>";
        }
        if (methResults.get("en") != null) {
            table += "<th style='background-color: #FFCC00;'><a href=''>Elastic net</a></th>";
        }
        if (methResults.get("spls") != null) {
            table += "<th style='background-color: #FFCC00;'><a href=''>SPLS</a></th>";
        }
        table += "</tr>\n";
        //Add data to the table
        for (int i = 0; i < oldResultRows; i++) {
            int element = rank[i][0];
            table += "<tr align='right'>";
            table += "<td>";
            String predictorVariable = "";
            //Only add the rowname once (from the first available result set).
            //FIXME: rename responseVariable to predictor variable?
            if (methResults.get("univariate_p") != null) {
                predictorVariable = univariate_p.get(element).getResponsVariable();
            } else if (methResults.get("univariate_bh") != null) {
                predictorVariable = univariate_bh.get(element).getResponsVariable();
            } else if (methResults.get("rf") != null) {
                predictorVariable = lasso.get(element).getResponsVariable();
            } else if (methResults.get("svm") != null) {
                predictorVariable = svm.get(element).getResponsVariable();
            } else if (methResults.get("pcr") != null) {
                predictorVariable = pcr.get(element).getResponsVariable();
            } else if (methResults.get("pls") != null) {
                predictorVariable = pls.get(element).getResponsVariable();
            } else if (methResults.get("ridge") != null) {
                predictorVariable = ridge.get(element).getResponsVariable();
            } else if (methResults.get("lasso") != null) {
                predictorVariable = rf.get(element).getResponsVariable();
            } else if (methResults.get("en") != null) {
                predictorVariable = en.get(element).getResponsVariable();
            } else if (methResults.get("spls") != null) {
                predictorVariable = spls.get(element).getResponsVariable();
            }
            //table row annotation & url
            //Set sessionID & responsevariable on the request scope? Only predictor via URL
            table += "<a href='" + baseURL + "/results/predRespXYScatter?predictor=" + predictorVariable
                    + "&response=traitName&session=" + getSessionId() + "'>" + predictorVariable + "</a>";//TODO: add URL
            table += "</td>";
            //Results
            if (methResults.get("univariate_p") != null) {
                table += "<td bgcolor=\"" + univariate_p.get(element).getHtmlColor() + "\">" + df.format(univariate_p.get(element).getMean()) + "</td>";
            }
            if (methResults.get("univariate_bh") != null) {
                table += "<td bgcolor=\"" + univariate_bh.get(element).getHtmlColor() + "\">" + df.format(univariate_bh.get(element).getMean()) + "</td>";
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

    /**
     *
     * @param sessionID
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private HashMap<String, ArrayList<CsvSummaryDataType>> getMethodsWithResultsSummaryFiles(String sessionID) throws FileNotFoundException, IOException {
        HashMap<String, ArrayList<CsvSummaryDataType>> results = new HashMap<String, ArrayList<CsvSummaryDataType>>();
        //FIXME: hardcoded
        String resultsDirectory = request.getSession().getServletContext().getInitParameter("resultsDirectory");
        if (sessionID == null || sessionID.isEmpty()) {
            resultsDirectory = "/home/finke002/Desktop/d89339e9c510a1e4e13ce46cc02b/";//Work
//        String tempDir = "/home/finke002/Desktop/e125586fcf9ba1b02a33093a2c17ex/";//CE Flesh
//        String tempDir = "/home/finke002/Desktop/81df58ab8635eaea6211020de5b5/";//BRIX
//        String tempDir = "/tmp/d2159ab79390aae6f5aea5e08254/";
        } else {
            resultsDirectory += "/" + sessionID + "/";
        }

        LOG.log(Level.INFO, "Tempdir: {0}", resultsDirectory);


        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "LASSO_coef_Sum.csv") == true) {
            results.put("lasso", CSV.readSummaryCsv(resultsDirectory + "LASSO_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "RIDGE_coef_Sum.csv") == true) {
            results.put("ridge", CSV.readSummaryCsv(resultsDirectory + "RIDGE_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "RF_varImp_Summary.csv") == true) {
            results.put("rf", CSV.readSummaryCsv(resultsDirectory + "RF_varImp_Summary.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "EN_coef_Sum.csv") == true) {
            results.put("en", CSV.readSummaryCsv(resultsDirectory + "EN_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "PCR_coef_Sum.csv") == true) {
            results.put("pcr", CSV.readSummaryCsv(resultsDirectory + "PCR_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "PLS_coef_Sum.csv") == true) {
            results.put("pls", CSV.readSummaryCsv(resultsDirectory + "PLS_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "SPLS_coef_Sum.csv") == true) {
            results.put("spls", CSV.readSummaryCsv(resultsDirectory + "SPLS_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "SVM_coef_Sum.csv") == true) {
            results.put("svm", CSV.readSummaryCsv(resultsDirectory + "SVM_coef_Sum.csv"));
        } //Disable univariate for now
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "univariate_bh_coef.csv") == true) {
            results.put("univariate_bh", CSV.readSummaryCsv(resultsDirectory + "univariate_bh_coef.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory + "univariate_p_coef.csv") == true) {
            results.put("univariate_p", CSV.readSummaryCsv(resultsDirectory + "univariate_p_coef.csv"));
        }
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
        int group = (int) Math.abs(value / (range / 20));
        //Part of the largest observations resolves to a value > 20 using
        //this equation. Set them manually to the max value.
        if (group > 20) {
            group = 20;
        }
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

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
