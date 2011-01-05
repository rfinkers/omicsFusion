/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.email;

import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * @author Richard Finkers
 */
public class SMTPAuthenticator extends Authenticator {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(SMTPAuthenticator.class.getName());
    private String username;
    private String password;
    private boolean needAuth;

    public SMTPAuthenticator(String username, String password, boolean needAuth) {
        this.username = username;
        this.password = password;
        this.needAuth = needAuth;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        if (needAuth) {
            return new PasswordAuthentication(username, password);
        } else {
            return null;
        }
    }
}
