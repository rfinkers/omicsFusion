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
package nl.wur.plantbreeding.omicsfusion.email;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Richard Finkers
 */
public class SubmissionCompleteEmail extends Email {

    private static final long serialVersionUID = 80111L;

    /**
     * Prepares the exception email which will be sended to the administrator.
     */
    //public static void SubmissionCompleteEmail(ServletContext context, HttpServletRequest req) {
    public static void SubmissionCompleteEmail() {



        LOG.info("start email");

        String emailBody;
        Email email = new Email();


        //Email body shoudl consist of:
        emailBody = "Dear " + email.getUser().getUserName() + ", \n"
                + "Thanks for using our omicsFusion service.\n"
                + "You can view your results by navigation to the following web address: "
                + "http://" + ServletActionContext.getRequest().getLocalName() + ":" + ServletActionContext.getRequest().getLocalPort()//TODO: http is now hardcoded
                + ServletActionContext.getServletContext().getContextPath()
                + "/results/summaryResults?sessionId=" + email.getSessionID() + "\n"
                + "Alternatively, you can enter the unique identifyer " + email.getSessionID() + " on the omicsFusion website\n\n"
                + "We are happy to recieve your feedback.\n\n"
                + "Regards,\n"
                + "Richard Finkers\n";

        try {
            Calendar now = Calendar.getInstance();

            email.sendEmailToUsers("omicsFusion run: "
                    + now.get(Calendar.DATE)
                    + "-"
                    + (now.get(Calendar.MONTH) + 1)
                    + "-"
                    + now.get(Calendar.YEAR)
                    + " / "
                    + now.get(Calendar.HOUR_OF_DAY)
                    + ":"
                    + now.get(Calendar.MINUTE)
                    + ":"
                    + now.get(Calendar.SECOND), emailBody);
            LOG.info("email send");
        } catch (AddressException ex) {
            Logger.getLogger(SubmissionCompleteEmail.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            Logger.getLogger(SubmissionCompleteEmail.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}