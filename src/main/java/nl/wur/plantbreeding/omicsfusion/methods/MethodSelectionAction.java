/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

/**
 *
 * @author Richard Finkers
 */
public class MethodSelectionAction extends UserMethodSelectionForm {

    @Override
    public String execute() throws Exception {
        return SUCCESS;

    }

    @Override
    public void validate() {
        if (isElasticNet1() == false && isElasticNet2() == false && isLasso() == false) {
            addActionError("No method selected");//Resource bundle?
        }
    }
}
