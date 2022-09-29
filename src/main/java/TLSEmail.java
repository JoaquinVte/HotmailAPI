import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class TLSEmail {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Session session;

    public void send(String toEmail, String subject, String body){
        prepare();
        EmailUtil.sendEmail(session, toEmail,subject, body);
    }
    public void send(String toEmail, String subject, String body, List<File> files){
        prepare();
        EmailUtil.sendAttachmentEmail(session, toEmail,subject, body,files);
    }

    private void prepare(){
        final String fromEmail = MyConfig.getInstancia().getEmail();
        final String password = MyConfig.getInstancia().getPassword();

        LOGGER.info("TLSEmail Start");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        session = Session.getInstance(props, auth);
    }
}