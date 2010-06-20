/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.plantbreeding.omicsfusion.methods;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author finke002
 */
public class RunAnalysisAction extends ActionSupport {

    @Override
    public String execute() throws Exception {
        System.out.println("Running Analysis");
        Univariate uv = new Univariate();
        
        return SUCCESS;
    }

}
