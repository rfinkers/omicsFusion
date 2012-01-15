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

import com.almworks.sqlite4java.SQLiteException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.sqlite4java.SqLiteQueries;
import nl.wur.plantbreeding.logic.util.FileOrDirectoryExists;
import nl.wur.plantbreeding.omicsfusion.datatypes.CsvSummaryDataType;
import nl.wur.plantbreeding.omicsfusion.datatypes.SummaryResults;
import nl.wur.plantbreeding.omicsfusion.utils.CSV;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import nl.wur.plantbreeding.omicsfusion.utils.ReadFile;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Action class that summarizes the results of the pipeline. Although the
 * confirmation for each result is only send at the end of the run, this action
 * can already be used to retrieve the results from the intermediate result
 * sets.
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class RetrieveResultsSummaryAction
        extends RetrieveResultsSummaryValidationForm
        implements ServletRequestAware {

    /**
     * The Logger
     */
    private static final Logger LOG = Logger.getLogger(
            RetrieveResultsSummaryAction.class.getName());
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * the request
     */
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

        //Location of the results directory
        String resultsDirectory =
                request.getSession().getServletContext().getInitParameter(
                "resultsDirectory");

        if (resultsDirectory == null) {
            addActionError(getText("errors.no.dir"));
            return ERROR;
        }

        //Check the availability of one or more respults file for this sessionID.
        //All potential summary files are scanned. Only methods with results are
        //added tot the HashMap.
//        HashMap<String, ArrayList<CsvSummaryDataType>> methResults =
//                getMethodsWithResultsSummaryFilesFromFile(getSessionId(),
//                resultsDirectory);
        HashMap<String, ArrayList<CsvSummaryDataType>> methResults =
                getMethodsWithResultsSummaryFilesFromDB(getSessionId(),
                resultsDirectory);

        System.out.println("Methods: " + methResults.size());

        if (methResults.isEmpty()) {
            addActionError(getText("errors.no.result"));
            return ERROR;
        }

        //Get the name of the response file.
        ReadFile rrn = new ReadFile();
        String responseName = null;
        try {
            responseName = rrn.ReadResponseName(resultsDirectory
                    + getSessionId() + "/analysis.txt");
        }
        catch (IOException iOException) {
            responseName = "Not found!";
        }

        //This counter should increase if additional methods are added!
        int[] resultRows = new int[9];
        int resultRowsCounter = 0;

        // Make each results matrix available and add the HTML color code which
        // is used for highlighting the results.
        if (methResults.get("lasso") != null) {
            lasso = methResults.get("lasso");
            addTableBackgroundColors(lasso, false);
            resultRows[resultRowsCounter] = lasso.size();
            resultRowsCounter++;
        }
        if (methResults.get("ridge") != null) {
            ridge = methResults.get("ridge");
            addTableBackgroundColors(ridge, false);
            resultRows[resultRowsCounter] = ridge.size();
            resultRowsCounter++;
        }
        if (methResults.get("rf") != null) {
            rf = methResults.get("rf");
            addTableBackgroundColors(rf, false);
            resultRows[resultRowsCounter] = rf.size();
            resultRowsCounter++;
        }
        if (methResults.get("pcr") != null) {
            pcr = methResults.get("pcr");
            addTableBackgroundColors(pcr, false);
            resultRows[resultRowsCounter] = pcr.size();
            resultRowsCounter++;
        }
        if (methResults.get("pls") != null) {
            pls = methResults.get("pls");
            addTableBackgroundColors(pls, false);
            resultRows[resultRowsCounter] = pls.size();
            resultRowsCounter++;
        }
        if (methResults.get("spls") != null) {
            spls = methResults.get("spls");
            addTableBackgroundColors(spls, false);
            resultRows[resultRowsCounter] = spls.size();
            resultRowsCounter++;
        }
        if (methResults.get("svm") != null) {
            svm = methResults.get("svm");
            addTableBackgroundColors(svm, false);
            resultRows[resultRowsCounter] = svm.size();
            resultRowsCounter++;
        }
        if (methResults.get("en") != null) {
            en = methResults.get("en");
            addTableBackgroundColors(en, false);
            resultRows[resultRowsCounter] = en.size();
            resultRowsCounter++;
        }
        if (methResults.get("univariate_p") != null) {
            univariate_p = methResults.get("univariate_p");
            addTableBackgroundColors(univariate_p, true);
            resultRows[resultRowsCounter] = univariate_p.size();
            resultRowsCounter++;
        }
        if (methResults.get("univariate_bh") != null) {
            univariate_bh = methResults.get("univariate_bh");
            addTableBackgroundColors(univariate_bh, true);
            resultRows[resultRowsCounter] = univariate_bh.size();
            resultRowsCounter++;
        }

        //lenght of all resulst arrays should be the same!
        int oldResultRows = 0;
        for (int row : resultRows) {
            if (row != 0) {
                if (oldResultRows != 0 && row != oldResultRows) {
                    addActionError(getText("errors.results.length"));
                    LOG.log(Level.WARNING, "Number of rows set A: {0}",
                            oldResultRows);
                    LOG.log(Level.WARNING, "Number of rows set B: {0}", row);
                    return ERROR;
                }
                oldResultRows = row;
            }
        }

        //Get the mean rank for each predictor
        int[][] rank = getMeanRank(oldResultRows, methResults, lasso, ridge,
                rf, pcr, pls, spls, svm, en, univariate_p);

        //Sort the array according to the rank
        sortRankArray(rank);

        //concatenate the HTML table.
        String table = getHtmlTableString(oldResultRows, responseName,
                methResults, rank, univariate_p, univariate_bh, lasso, svm,
                pcr, pls, ridge, rf, en, spls);

        //Set sessionID & responsevariable on the request scope. Only predictor via URL
        request.getSession().setAttribute("resultSession", getSessionId());
        request.getSession().setAttribute("responseName", responseName);

        //To allow tests without usage of request scope?
        if (ServletUtils.getServletRequest() != null) {
            HttpServletRequest servletRequest = ServletUtils.getServletRequest();
            servletRequest.setAttribute("table", table);
        }
        return SUCCESS;
    }

    private String getHtmlTableString(int oldResultRows, String responseName,
            HashMap<String, ArrayList<CsvSummaryDataType>> methResults,
            int[][] rank, ArrayList<CsvSummaryDataType> univariate_p,
            ArrayList<CsvSummaryDataType> univariate_bh,
            ArrayList<CsvSummaryDataType> lasso,
            ArrayList<CsvSummaryDataType> svm,
            ArrayList<CsvSummaryDataType> pcr,
            ArrayList<CsvSummaryDataType> pls,
            ArrayList<CsvSummaryDataType> ridge,
            ArrayList<CsvSummaryDataType> rf,
            ArrayList<CsvSummaryDataType> en,
            ArrayList<CsvSummaryDataType> spls) {
        String baseURL = request.getContextPath();
        DecimalFormat df = new DecimalFormat("#.###");
        //We want an counter for overall rank of the response variable.
        int negativeCounter = oldResultRows;
        boolean maxSummary = false;
        //Concatenate the HTML table.
        String table = "<table class='summaryTable'>\n";
        table += getHtmlTableHeaderString(responseName, methResults);
        //Add data to the table
        //Use a stringBuilder, as string concatenation of large objects is slow.
        StringBuilder sb = new StringBuilder(oldResultRows);
        for (int i = 0; i < oldResultRows; i++) {
            sb.append(getHtmlTableRowString(rank, i, methResults, univariate_p,
                    univariate_bh, lasso, svm, pcr, pls, ridge, rf, en, spls,
                    baseURL, negativeCounter, df));
            negativeCounter -= 1;
            if (i > Constants.MAX_SUMMARY_RESULTS) {
                maxSummary = true;
                break;
            }
        }
        table += "<tbody>\n";
        table += sb.toString();
        table += "</tbody>\n";
        table += "</table>";
        if (maxSummary == true) {
            table += "Note: more than " + Constants.MAX_SUMMARY_RESULTS
                    + " results retrieved. This table only shows the top "
                    + Constants.MAX_SUMMARY_RESULTS
                    + " ranking predictor variables!";
        }
        return table;
    }

    /**
     * Get information for each row of the table.
     *
     * @param rank
     * @param i
     * @param methResults
     * @param univariate_p
     * @param univariate_bh
     * @param lasso
     * @param svm
     * @param pcr
     * @param pls
     * @param ridge
     * @param rf
     * @param en
     * @param spls
     * @param baseURL
     * @param negativeCounter
     * @param df
     * @return
     */
    private String getHtmlTableRowString(int[][] rank, int i,
            HashMap<String, ArrayList<CsvSummaryDataType>> methResults,
            ArrayList<CsvSummaryDataType> univariate_p,
            ArrayList<CsvSummaryDataType> univariate_bh,
            ArrayList<CsvSummaryDataType> lasso,
            ArrayList<CsvSummaryDataType> svm,
            ArrayList<CsvSummaryDataType> pcr,
            ArrayList<CsvSummaryDataType> pls,
            ArrayList<CsvSummaryDataType> ridge,
            ArrayList<CsvSummaryDataType> rf,
            ArrayList<CsvSummaryDataType> en,
            ArrayList<CsvSummaryDataType> spls,
            String baseURL, int negativeCounter, DecimalFormat df) {
        int element = rank[i][0];
        String row = "<tr align='right'>";
        row += "<td>";
        String predictorVariable = "";
        //Only add the rowname once (from the first available result set).
        if (methResults.get("univariate_p") != null) {
            predictorVariable = univariate_p.get(element).getPredictorVariable();
        } else if (methResults.get("univariate_bh") != null) {
            predictorVariable = univariate_bh.get(element).getPredictorVariable();
        } else if (methResults.get("rf") != null) {
            predictorVariable = lasso.get(element).getPredictorVariable();
        } else if (methResults.get("svm") != null) {
            predictorVariable = svm.get(element).getPredictorVariable();
        } else if (methResults.get("pcr") != null) {
            predictorVariable = pcr.get(element).getPredictorVariable();
        } else if (methResults.get("pls") != null) {
            predictorVariable = pls.get(element).getPredictorVariable();
        } else if (methResults.get("ridge") != null) {
            predictorVariable = ridge.get(element).getPredictorVariable();
        } else if (methResults.get("lasso") != null) {
            predictorVariable = rf.get(element).getPredictorVariable();
        } else if (methResults.get("en") != null) {
            predictorVariable = en.get(element).getPredictorVariable();
        } else if (methResults.get("spls") != null) {
            predictorVariable = spls.get(element).getPredictorVariable();
        }

        //table row annotation & url
        //TODO: can we use <s:url> instead?
        row += "<a href='" + baseURL + "/results/predRespXYScatter?predictor="
                + predictorVariable
                //+ "&response=traitName&session=" + getSessionId()
                + "'>"
                + predictorVariable + " (" + negativeCounter + ")</a>";

        //+ "<s:url value = \"/results/predRespXYScatter\">
        //<s:param name =\"test\" value=\"" + predictorVariable + "\" />"
        //+ predictorVariable + "</s:url>";//TODO: add URL
        row += "</td>";
        //Results
        if (methResults.get("univariate_p") != null) {
            row += "<td class=\"" + univariate_p.get(element).getHtmlColor()
                    + "\"><a title=\"rank: "
                    + univariate_p.get(element).getRank() + "\"> "
                    + df.format(univariate_p.get(element).getMean()) + "</td>";
        }
        if (methResults.get("univariate_bh") != null) {
            row += "<td class=\"" + univariate_bh.get(element).getHtmlColor()
                    + "\"><a title=\"rank: "
                    + univariate_bh.get(element).getRank() + "\"> "
                    + df.format(univariate_bh.get(element).getMean()) + "</td>";
        }
        if (methResults.get("rf") != null) {
            row += "<td class=\"" + rf.get(element).getHtmlColor()
                    + "\"><a title=\"rank: " + rf.get(element).getRank()
                    + " / sd: " + df.format(rf.get(element).getSd()) + " \"> "
                    + df.format(rf.get(element).getMean()) + " </a></td>";
        }
        if (methResults.get("svm") != null) {
            row += "<td class=\"" + svm.get(element).getHtmlColor()
                    + "\"><a title=\"rank: " + svm.get(element).getRank()
                    + " / sd: " + df.format(svm.get(element).getSd()) + " \"> "
                    + df.format(svm.get(element).getMean()) + " </a></td>";
        }
        if (methResults.get("pcr") != null) {
            row += "<td class=\"" + pcr.get(element).getHtmlColor()
                    + "\"><a title=\"rank: " + pcr.get(element).getRank()
                    + " / sd: " + df.format(pcr.get(element).getSd()) + " \"> "
                    + df.format(pcr.get(element).getMean()) + " </a></td>";
        }
        if (methResults.get("pls") != null) {
            row += "<td class=\"" + pls.get(element).getHtmlColor()
                    + "\"><a title=\"rank: " + pls.get(element).getRank()
                    + " / sd: " + df.format(pls.get(element).getSd()) + " \"> "
                    + df.format(pls.get(element).getMean()) + " </a></td>";
        }
        if (methResults.get("ridge") != null) {
            row += "<td class=\"" + ridge.get(element).getHtmlColor()
                    + "\"><a title=\"rank: " + ridge.get(element).getRank()
                    + " / sd: " + df.format(ridge.get(element).getSd()) + " \"> "
                    + df.format(ridge.get(element).getMean()) + " </a></td>";
        }
        if (methResults.get("lasso") != null) {
            if (lasso.get(element).getMean() != 0) {
                row += "<td class=\"" + lasso.get(element).getHtmlColor()
                        + "\"><a title=\"rank: " + lasso.get(element).getRank()
                        + " / sd: " + df.format(lasso.get(element).getSd())
                        + " \"> " + df.format(lasso.get(element).getMean())
                        + " </a></td>";
            } else {
                row += "<td></td>";
            }
        }
        if (methResults.get("en") != null) {
            if (en.get(element).getMean() != 0) {
                row += "<td class=\"" + en.get(element).getHtmlColor()
                        + "\"><a title=\"rank: " + en.get(element).getRank()
                        + " / sd: " + df.format(en.get(element).getSd()) + " \"> "
                        + df.format(en.get(element).getMean()) + " </a></td>";
            } else {
                row += "<td></td>";
            }
        }
        if (methResults.get("spls") != null) {
            if (spls.get(element).getMean() != 0) {
                row += "<td class=\"" + spls.get(element).getHtmlColor()
                        + "\"><a title=\"rank: " + spls.get(element).getRank()
                        + " / sd: " + df.format(spls.get(element).getSd()) + " \"> "
                        + df.format(spls.get(element).getMean()) + " </a></td>";
            } else {
                row += "<td></td>";
            }
        }
        row += "</tr>\n";
        return row;
    }

    private String getHtmlTableHeaderString(String responseName,
            HashMap<String, ArrayList<CsvSummaryDataType>> methResults) {
        //TODO: resource bundle
        //TODO: title back to href! with implementation of specific pages
        String table = "<thead>\n";
        table += "<tr><th>Response:<br/>" + responseName + "</th>";
        if (methResults.get("univariate_p") != null) {
            table += "<th class='univariate'>"
                    + "<a title='univariate'>Univariate pval</a></th>";
        }
        if (methResults.get("univariate_bh") != null) {
            table += "<th class='univariate'>"
                    + "<a title='BH'>Univariate BH</a></th>";
        }
        if (methResults.get("rf") != null) {
            table += "<th class='machine'>"
                    + "<a title='RF'>Random Forest</a></th>";
        }
        if (methResults.get("svm") != null) {
            table += "<th class='machine'>"
                    + "<a title='SVM'>SVM</a></th>";
        }
        if (methResults.get("pcr") != null) {
            table += "<th class='regression'>"
                    + "<a title='PCR'>PCR</a></th>";
        }
        if (methResults.get("pls") != null) {
            table += "<th class='regression'>"
                    + "<a title='PLS'>PLS</a></th>";
        }
        if (methResults.get("ridge") != null) {
            table += "<th class='regression'>"
                    + "<a title='Ridge'>Ridge</a></th>";
        }
        if (methResults.get("lasso") != null) {
            table += "<th class='variableSelection'>"
                    + "<a title='Lasso'>Lasso</a></th>";
        }
        if (methResults.get("en") != null) {
            table += "<th class='variableSelection'>"
                    + "<a title='EN'>Elastic net</a></th>";
        }
        if (methResults.get("spls") != null) {
            table += "<th class='variableSelection'>"
                    + "<a title='SPLS'>SPLS</a></th>";
        }
        table += "</tr>\n";
        table += "</thead>\n";
        return table;
    }

    /**
     * Get the mean rank of a response variable from the individual result
     * files. univariate is not used to calculate the mean ranking.
     *
     * @param oldResultRows Number of response variables.
     * @param methResults
     * @param lasso
     * @param ridge
     * @param rf
     * @param pcr
     * @param pls
     * @param spls
     * @param svm
     * @param en
     * @param univariate_p
     * @param univariate_bh
     * @return The mean ranks of the response variables.
     */
    private int[][] getMeanRank(int oldResultRows,
            HashMap<String, ArrayList<CsvSummaryDataType>> methResults,
            ArrayList<CsvSummaryDataType> lasso,
            ArrayList<CsvSummaryDataType> ridge,
            ArrayList<CsvSummaryDataType> rf,
            ArrayList<CsvSummaryDataType> pcr,
            ArrayList<CsvSummaryDataType> pls,
            ArrayList<CsvSummaryDataType> spls,
            ArrayList<CsvSummaryDataType> svm,
            ArrayList<CsvSummaryDataType> en,
            ArrayList<CsvSummaryDataType> univariate_p) {
        //TODO: add mean rank and use this for sorting (or instead of for loop?).
        int[][] rank = new int[oldResultRows][2];
        for (int i = 0; i < oldResultRows; i++) {
            int count = 0;
            int sumRank = 0;
            if (methResults.get("lasso") != null) {
                //variable selection method.
                if (lasso.get(i).getMean() != 0) {
                    sumRank += lasso.get(i).getRank();
                    count++;
                }
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
                //variable selection method.
                if (spls.get(i).getMean() != 0) {
                    sumRank += spls.get(i).getRank();
                    count++;
                }
            }
            if (methResults.get("svm") != null) {
                sumRank += svm.get(i).getRank();
                count++;
            }
            if (methResults.get("en") != null) {
                //variable selection method.
                //TODO: check validity
                if (en.get(i).getMean() != 0) {
                    sumRank += en.get(i).getRank();
                    count++;
                }
            }
            //We only want to use the univariate for the overal rank.
            //The BH corrected rank looses part of the ranking as gives the same
            //rank to variables which have different ranks within univariate.
            if (methResults.get("univariate_p") != null) {
                sumRank += univariate_p.get(i).getRank();
                count++;
            }
//            if (methResults.get("univariate_bh") != null) {
//                sumRank += univariate_bh.get(i).getRank();
//                count++;
//            }
            rank[i][0] = i;
            rank[i][1] = sumRank / count;
        }
        return rank;
    }

    /**
     * Sort the multidimensional results array.
     *
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
     * Add background color to the cells.
     *
     * @param results
     */
    private void addTableBackgroundColors(ArrayList<CsvSummaryDataType> results,
            boolean inverse) {
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
            csvSummaryDataType.setHtmlColor(getBackgroundColor(
                    csvSummaryDataType.getMean(), min, max, inverse));
        }
    }

    /**
     *
     * @param sessionID
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private HashMap<String, ArrayList<CsvSummaryDataType>> getMethodsWithResultsSummaryFilesFromFile(String sessionID, String resultsDirectory)
            throws FileNotFoundException, IOException {
        HashMap<String, ArrayList<CsvSummaryDataType>> results =
                new HashMap<String, ArrayList<CsvSummaryDataType>>();

        if (sessionID == null || sessionID.isEmpty()) {
            addActionError(getText("errors.sessionID.required"));
        } else {
            resultsDirectory += sessionID + "/";
        }

        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "LASSO_coef_Sum.csv") == true) {
            results.put("lasso", CSV.readSummaryCsv(resultsDirectory
                    + "LASSO_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "RIDGE_coef_Sum.csv") == true) {
            results.put("ridge", CSV.readSummaryCsv(resultsDirectory
                    + "RIDGE_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "RF_varImp_Summary.csv") == true) {
            results.put("rf", CSV.readSummaryCsv(resultsDirectory
                    + "RF_varImp_Summary.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "EN_coef_Sum.csv") == true) {
            results.put("en", CSV.readSummaryCsv(resultsDirectory
                    + "EN_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "PCR_coef_Sum.csv") == true) {
            results.put("pcr", CSV.readSummaryCsv(resultsDirectory
                    + "PCR_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "PLS_coef_Sum.csv") == true) {
            results.put("pls", CSV.readSummaryCsv(resultsDirectory
                    + "PLS_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "SPLS_coef_Sum.csv") == true) {
            results.put("spls", CSV.readSummaryCsv(resultsDirectory
                    + "SPLS_coef_Sum.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "SVM_coef_Sum.csv") == true) {
            results.put("svm", CSV.readSummaryCsv(resultsDirectory
                    + "SVM_coef_Sum.csv"));
        } //Disable univariate for now
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "univariate_bh_coef.csv") == true) {
            results.put("univariate_bh", CSV.readSummaryCsv(resultsDirectory
                    + "univariate_bh_coef.csv"));
        }
        if (FileOrDirectoryExists.FileOrDirectoryExists(resultsDirectory
                + "univariate_p_coef.csv") == true) {
            results.put("univariate_p", CSV.readSummaryCsv(resultsDirectory
                    + "univariate_p_coef.csv"));
        }
        return results;
    }

    private HashMap<String, ArrayList<CsvSummaryDataType>> getMethodsWithResultsSummaryFilesFromDB(String sessionID, String resultsDirectory)
            throws SQLiteException {
        HashMap<String, ArrayList<CsvSummaryDataType>> results =
                new HashMap<String, ArrayList<CsvSummaryDataType>>();

        if (sessionID == null || sessionID.isEmpty()) {
            addActionError(getText("errors.sessionID.required"));
        } else {
            resultsDirectory += sessionID + "/";
        }

        //read data from the database
        //one or several queries?
        SqLiteQueries queries = new SqLiteQueries();
        ArrayList<SummaryResults> readSummaryResults =
                queries.readSummaryResults(resultsDirectory);

        LOG.log(Level.INFO, "Database Size: {0}", readSummaryResults.size());

        //add results to the different lists
        ArrayList<CsvSummaryDataType> lasso =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> ridge =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> rf =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> en =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> pcr =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> pls =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> spls =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> svm =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> univariate_p =
                new ArrayList<CsvSummaryDataType>();
        ArrayList<CsvSummaryDataType> univariate_bh =
                new ArrayList<CsvSummaryDataType>();

        CsvSummaryDataType dataPoint = null;
        for (SummaryResults summaryResults : readSummaryResults) {
            dataPoint = new CsvSummaryDataType(
                    summaryResults.getPredictorVariable(),
                    summaryResults.getMean(),
                    summaryResults.getSd(),
                    summaryResults.getRank());

            if (summaryResults.getMethod().equals("PCR")) {
                pcr.add(dataPoint);
            } else if (summaryResults.getMethod().equals("LASSO")) {
                lasso.add(dataPoint);
            } else if (summaryResults.getMethod().equals("PLS")) {
                pls.add(dataPoint);
            } else if (summaryResults.getMethod().equals("SPLS")) {
                spls.add(dataPoint);
            } else if (summaryResults.getMethod().equals("RF")) {
                rf.add(dataPoint);
            } else if (summaryResults.getMethod().equals("UNIVARIATE")) {
                univariate_p.add(dataPoint);
            } else if (summaryResults.getMethod().equals("BH")) {
                univariate_bh.add(dataPoint);
            } else if (summaryResults.getMethod().equals("RIDGE")) {
                ridge.add(dataPoint);
            } else if (summaryResults.getMethod().equals("SVM")) {
                svm.add(dataPoint);
            } else if (summaryResults.getMethod().equals("EN")) {
                en.add(dataPoint);
            }
        }

        //Only add lists with results.
        if (pcr != null && !pcr.isEmpty()) {
            results.put("pcr", pcr);
        }
        if (pls != null && !pls.isEmpty()) {
            results.put("pls", pls);
        }
        if (lasso != null && !lasso.isEmpty()) {
            results.put("lasso", lasso);
        }
        if (ridge != null && !ridge.isEmpty()) {
            results.put("ridge", ridge);
        }
        if (rf != null && !rf.isEmpty()) {
            results.put("rf", rf);
            LOG.info("adding RF");
        }
        if (en != null && !en.isEmpty()) {
            results.put("en", en);
        }
        if (spls != null && !spls.isEmpty()) {
            results.put("spls", spls);
        }
        if (svm != null && !svm.isEmpty()) {
            results.put("svm", svm);
        }
        if (univariate_p != null && !univariate_p.isEmpty()) {
            results.put("univariate_p", univariate_p);
        }
        if (univariate_bh != null && !univariate_bh.isEmpty()) {
            results.put("univariate_bh", univariate_bh);
        }

        return results;
    }

    /**
     * Get the background color for the table according to a gradient of 20
     * colors from white to blue.
     *
     * @param result
     * @param min
     * @param max
     * @param inverse
     * @return String containing the color code.
     */
    protected String getBackgroundColor(Double result, Double min, Double max,
            boolean inverse) {
        String background = "highlight"; //White is default background color.

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

        if (inverse == true) {
            int newgroup = 20 - group;
            group = newgroup;
        }
        switch (group) {
            case 1:
                background = "highlight1";
                break;
            case 2:
                background = "highlight2";
                break;
            case 3:
                background = "highlight3";
                break;
            case 4:
                background = "highlight4";
                break;
            case 5:
                background = "highlight5";
                break;
            case 6:
                background = "highlight6";
                break;
            case 7:
                background = "highlight7";
                break;
            case 8:
                background = "highlight8";
                break;
            case 9:
                background = "highlight9";
                break;
            case 10:
                background = "highlight10";
                break;
            case 11:
                background = "highlight11";
                break;
            case 12:
                background = "highlight12";
                break;
            case 13:
                background = "highlight13";
                break;
            case 14:
                background = "highlight14";
                break;
            case 15:
                background = "highlight15";
                break;
            case 16:
                background = "highlight16";
                break;
            case 17:
                background = "highlight17";
                break;
            case 18:
                background = "highlight18";
                break;
            case 19:
                background = "highlight19";
                break;
            case 20:
                background = "highlight20";
                break;
            case 21:
                background = "highlight21";
                break;
            default:
                background = "highlight";
                break;
        }
        return background;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
