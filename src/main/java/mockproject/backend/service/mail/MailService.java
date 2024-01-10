/*
 *@project : BackEnd
 *@Create by: TrienND
 *@Create time: 11/18/2023 - 8:27 PM
 */


package mockproject.backend.service.mail;

import mockproject.backend.domain.dto.user.UserForMail;
import mockproject.backend.domain.entity.other.UserTemporary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
    @Value("${config.mail.host}")
    private String host;
    @Value("${config.mail.port}")
    private String port;
    @Value("${config.mail.username}")
    private String email;
    @Value("${config.mail.password}")
    private String password;

    @Autowired
    private ThymeleafService thymeleafService;

    private Properties getProp() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);
        return props;
    }

    public void sendMailToUser(UserForMail user) {
        Session session = Session.getInstance(getProp(),
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(user.getEmail())});
            message.setFrom(new InternetAddress(email));
            message.setSubject("[FAMS] - Tài khoản được tạo thành công");
            message.setContent(thymeleafService.getContent(user), CONTENT_TYPE_TEXT_HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //send mail renew password
    public void sendMailRenewPassword(UserTemporary user) {
        Session session = Session.getInstance(getProp(),
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(user.getEmail())});
            message.setFrom(new InternetAddress(email));
            message.setSubject("[FAMS] - Yêu cầu cấp lại mật khẩu");
            message.setContent(thymeleafService.getContentResetPassword(user), CONTENT_TYPE_TEXT_HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}