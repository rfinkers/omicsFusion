package nl.wur.plantbreeding.omicsfusion.email;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Sends notification emails to users.
 * @author Richard Finkers
 * @version 1.0
 */
public class EmailSender {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(EmailSender.class.getName());
    private String smtpServer;
    private String port;
    private String user;
    private String password;
    private String auth;
    private String from;

    /**
     * Initializes the email wiht all requied data.
     * @param smtpServer
     * @param port
     * @param user
     * @param password
     * @param auth
     * @param from
     */
    protected EmailSender(String smtpServer, String port, String user, String password, String auth, String from) {
        this.smtpServer = smtpServer;
        this.port = port;
        this.user = user;
        this.password = password;
        this.auth = auth;
        this.from = from;
    }

    /**
     * Set the properties.
     * @return An object wiht relevant system properties.
     */
    private Properties prepareProperties() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", smtpServer);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.user", user);
        props.setProperty("mail.smtp.password", password);
        props.setProperty("mail.smtp.auth", auth);
        return props;
    }

    /**
     * Prepare the email.
     * @param mailSession
     * @param charset
     * @param from
     * @param subject
     * @param HtmlMessage
     * @param recipient
     * @return
     */
    private MimeMessage prepareMessage(Session mailSession, String charset,
            String from, String subject,
            String HtmlMessage, String[] recipient) {
        //Multipurpose Internet Mail Extensions
        MimeMessage message = null;
        try {
            message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            for (int i = 0; i < recipient.length; i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient[i]));
            }
            message.setContent(HtmlMessage, "text/html; charset=\"" + charset + "\"");
        } catch (Exception ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    /**
     * Send the email.
     * @param subject
     * @param HtmlMessage
     * @param to
     */
    public void sendEmail(String subject, String HtmlMessage, String[] to) {
        Transport transport = null;
        try {
            Properties props = prepareProperties();
            Session mailSession = Session.getDefaultInstance(
                    props, new SMTPAuthenticator(from, password, true));
            transport = mailSession.getTransport("smtp");
            MimeMessage message = prepareMessage(mailSession, "ISO-8859-2",
                    from, subject, HtmlMessage, to);
            transport.connect();
            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                transport.close();
            } catch (MessagingException ex) {
                Logger.getLogger(EmailSender.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
    }
}
