import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class EmailUtil {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(MyConfig.getInstancia().getEmail(), MyConfig.getInstancia().getReplyName()));

            msg.setReplyTo(InternetAddress.parse(MyConfig.getInstancia().getEmail(), false));

            msg.setSubject(subject, "UTF-8");
            msg.setContent(body,"text/html");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            LOGGER.info("Message is ready");
            Transport.send(msg);
            LOGGER.info("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
    /**
     * Utility method to send email with attachment
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendAttachmentEmail(Session session, String toEmail, String subject, String body, List<File> files){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(MyConfig.getInstancia().getEmail(), MyConfig.getInstancia().getReplyName()));

            msg.setReplyTo(InternetAddress.parse(MyConfig.getInstancia().getEmail(), false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setContent(body,"text/html");

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            for(File file : files) {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file.getName());
                multipart.addBodyPart(messageBodyPart);
            }

            // Send the complete message parts
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            LOGGER.info("EMail Sent Successfully with attachment!!");
        }catch (MessagingException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
    /**
     * Utility method to send image in email body
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendImageEmail(Session session, String toEmail, String subject, String body){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(MyConfig.getInstancia().getEmail(), MyConfig.getInstancia().getReplyName()));

            msg.setReplyTo(InternetAddress.parse(MyConfig.getInstancia().getEmail(), false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is image attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "image.png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            //Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "<image_id>");
            multipart.addBodyPart(messageBodyPart);

            //third part for displaying image in the email body
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Attached Image</h1>" +
                    "<img src=\"cid:image_id\">"+
                    "<h2>FIN</h2>", "text/html");
            multipart.addBodyPart(messageBodyPart);

            //Set the multipart message to the email message
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with image!!");
        }catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}