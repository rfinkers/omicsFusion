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
    private boolean elasticNet;
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

    public boolean isElasticNet() {
        return elasticNet;
    }

    public void setElasticNet(boolean elasticNet1) {
        this.elasticNet = elasticNet1;
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
        if (isLasso() == false && isElasticNet() == false && isPcr() == false
                && isPls() == false && isRf() == false && isSpls() == false
                && isRidge() == false && isSvm() == false && isUnivariate() == false) {
            addActionError("The current selection is invalid. Select at least one method!");
        }
    }
}
