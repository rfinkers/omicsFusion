/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

/**
 *
 * @author Richard Finkers
 */
public class MethodSelectionAction extends MethodSelectionValidationForm {

    private static final long serialVersionUID = 180610L;

    @Override
    public String execute() throws Exception {
        //TODO: store the selections on the session scope?
        System.out.println("Action: method selection completed. Lasso: " + isLasso());
        return SUCCESS;
    }
}
