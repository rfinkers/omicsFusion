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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Richard Finkers
 */
public class Email extends ActionSupport {

    private static final long serialVersionUID = 80111L;
    /**
     * User object
     */
    private UserList user;

    public UserList getUser() {
        return (UserList) ServletActionContext.getContext().getSession().get(Constants.USER);
    }

    public static Logger getLOG() {
        return LOG;
    }

    /**
     * Obtain the administrators email address from the context parameters.
     *
     * @return The configured administrator email.
     */
    public String getAdminEmailAdress() {
        return ServletActionContext.getServletContext().getInitParameter("adminEmail");
    }

    /**
     * Obtain the smtp server from the context parameters.
     *
     * @return The configured smtp server.
     */
    public String getSmtpServer() {
        return ServletActionContext.getServletContext().getInitParameter("smtpServer");
    }

    public String getSessionID() {
        return ServletActionContext.getRequest().getSession().getId();
    }

    /**
     * Generic wrapper to send an email address from the BreeDB administrator to
     * the administrator.
     *
     * @param subject
     * @param emailBody
     * @throws AddressException
     * @throws MessagingException
     */
    protected void sendEmailToUsers(String subject, String emailBody)
            throws AddressException, MessagingException {
        Properties props = new Properties();

        props.put(getSmtpServer(), getSmtpServer());

        Session session1 = Session.getDefaultInstance(props, null);
        Message message = new MimeMessage(session1);
        message.setFrom(new InternetAddress(getAdminEmailAdress()));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(getUser().getEmail().trim(), false));
        message.setRecipients(Message.RecipientType.BCC,
                InternetAddress.parse(getAdminEmailAdress().trim(), false));
        message.setSubject(subject);

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText(emailBody);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        Transport.send(message);
        LOG.info("Email send");
    }

    /**
     * Send a email containing the licensefile as an attachment to the user.
     *
     * @param subject subject of the email
     * @param emailBody
     * @throws MessagingException Error creating or sending the email.
     */
    public void sendEmail(String subject, String emailBody)
            throws MessagingException {

        Properties props = new Properties();

        props.put(getSmtpServer(), getSmtpServer());

        Session session1 = Session.getDefaultInstance(props, null);

        Message message = new MimeMessage(session1);
        message.setFrom(new InternetAddress(getAdminEmailAdress()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
                getAdminEmailAdress(), false));
        message.setSubject(subject);

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText(emailBody);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Put parts in message
        message.setContent(multipart);

        Transport.send(message);
        LOG.info("Email send");
    }
}
