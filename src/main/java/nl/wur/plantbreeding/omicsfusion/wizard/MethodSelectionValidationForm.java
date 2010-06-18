/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;

/**
 * Overview of selected methods.
 * @author Richard Finkers
 */
public class MethodSelectionValidationForm extends ActionSupport {

    private static final long serialVersionUID = 180610L;
    /** lasso regression */
    private boolean lasso;
    /** Elastic net 1 */
    private boolean elasticNet1;
    /** Elastic net 2 */
    private boolean elasticNet2;
    /** pcr */
    private boolean pcr;
    /** pls */
    private boolean pls;
    /** random forest */
    private boolean rf;
    /** ridge regression */
    private boolean ridge;
    /** svm */
    private boolean svm;
    /** spls */
    private boolean spls;
    /** univariate */
    private boolean univariate;

    public boolean isLasso() {
        return lasso;
    }

    public void setLasso(boolean lasso) {
        this.lasso = lasso;
    }

    public boolean isElasticNet1() {
        return elasticNet1;
    }

    public void setElasticNet1(boolean elasticNet1) {
        this.elasticNet1 = elasticNet1;
    }

    public boolean isElasticNet2() {
        return elasticNet2;
    }

    public void setElasticNet2(boolean elasticNet2) {
        this.elasticNet2 = elasticNet2;
    }

    public boolean isPcr() {
        return pcr;
    }

    public void setPcr(boolean pcr) {
        this.pcr = pcr;
    }

    public boolean isPls() {
        return pls;
    }

    public void setPls(boolean pls) {
        this.pls = pls;
    }

    public boolean isRf() {
        return rf;
    }

    public void setRf(boolean rf) {
        this.rf = rf;
    }

    public boolean isRidge() {
        return ridge;
    }

    public void setRidge(boolean ridge) {
        this.ridge = ridge;
    }

    public boolean isSpls() {
        return spls;
    }

    public void setSpls(boolean spls) {
        this.spls = spls;
    }

    public boolean isSvm() {
        return svm;
    }

    public void setSvm(boolean svm) {
        this.svm = svm;
    }

    public boolean isUnivariate() {
        return univariate;
    }

    public void setUnivariate(boolean univariate) {
        this.univariate = univariate;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        ActionSupport.LOG = LOG;
    }

    @Override
    public void validate() {
        /* atleast one method should be selected */
        if (isLasso() == false && isElasticNet1() == false && isElasticNet2() == false
                && isPcr() == false && isPls() == false && isRf() == false && isSpls() == false
                && isRidge() == false && isSvm() == false && isUnivariate() == false) {
            addActionError("The current selection is invalid. Select at least one method!");
        }
    }
}
