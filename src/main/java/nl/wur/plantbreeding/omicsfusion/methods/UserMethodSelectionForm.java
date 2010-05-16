/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;

/**
 * Contains the selections for the wizzard.
 * @author Richard Finkers
 */
public class UserMethodSelectionForm extends ActionSupport {

    /** firstName */
    private String firstName;
    /** lastName */
    private String lastName;
    /** email */
    private String email;
    /** session token */
    private String token;
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
    /** spls */
    private boolean spls;
    /** random forrest */
    private boolean rf;
    /** ridge */
    private boolean ridge;
    /** svm */
    private boolean svm;
    /** univariate */
    private boolean univariate;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isUnivariate() {
        return univariate;
    }

    public void setUnivariate(boolean univariate) {
        this.univariate = univariate;
    }
    
    public boolean isLasso() {
        return lasso;
    }

    public void setLasso(boolean lasso) {
        this.lasso = lasso;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        ActionSupport.LOG = LOG;
    }
}
