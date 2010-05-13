/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.pipeline;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;

/**
 * Contains the user selections for the dataset submission.
 * @author Richard Finkers
 */
public class PipelineSelectionsForm extends ActionSupport {

    /** Varialbe selection methods only. */
    private boolean variableSelection = false;
    /** Perform elastic net regression (1). */
    private boolean elasticNet1 = false;
    /** Perform elastic net regression (2). */
    private boolean elasticNet2 = false;
    /** perform lasso regression. */
    private boolean lasso = false;
    /** perform PCR. */
    private boolean pcr = false;
    /** perform pls regregression. */
    private boolean pls = false;
    /** Perform random forest analysis. */
    private boolean randomForest = false;
    /** perform ridge regression. */
    private boolean ridge = false;
    /** svm. */
    private boolean svm = false;
    /** perform sparse pls. */
    private boolean sparsePls = false;
    /** perform univariate analysis. */
    private boolean univariate = false;
    /** email address. */
    private String email = null;
    /** first name */
    private String firstName = null;
    /** last name */
    private String lastName = null;
    /** step1 completed, a check to allow form validation */
    private boolean step1 = false;
    /** step2 completed, a check to allow form validation */
    private boolean step2 = false;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isLasso() {
        return lasso;
    }

    public void setLasso(boolean lasso) {
        this.lasso = lasso;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean isRandomForest() {
        return randomForest;
    }

    public void setRandomForest(boolean randomForest) {
        this.randomForest = randomForest;
    }

    public boolean isRidge() {
        return ridge;
    }

    public void setRidge(boolean ridge) {
        this.ridge = ridge;
    }

    public boolean isSparsePls() {
        return sparsePls;
    }

    public void setSparsePls(boolean sparsePls) {
        this.sparsePls = sparsePls;
    }

    public boolean isStep1() {
        return step1;
    }

    public void setStep1(boolean step1) {
        this.step1 = step1;
    }

    public boolean isStep2() {
        return step2;
    }

    public void setStep2(boolean step2) {
        this.step2 = step2;
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

    public boolean isVariableSelection() {
        return variableSelection;
    }

    public void setVariableSelection(boolean variableSelection) {
        this.variableSelection = variableSelection;
    }

//    /**
//     *
//     */
//    public PipelineSelectionsForm() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//    /**
//     * This is the action called from the Struts framework.
//     * @param mapping The ActionMapping used to select this instance.
//     * @param request The HTTP Request we are processing.
//     * @return
//     */
//    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
//        ActionErrors errors = new ActionErrors();
//        if (this.step1 = true) {
//            //validate
//        }
//        if (this.step2 = true) {
//            //validate
//        }
////        if (getName() == null || getName().length() < 1) {
////            errors.add("name", new ActionMessage("error.name.required"));
////            // TODO: add 'error.name.required' key to your resources
////        }
//        return errors;
//    }
}
