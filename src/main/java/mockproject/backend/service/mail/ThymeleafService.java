/*
 *@project : BackEnd
 *@Create by: TrienND
 *@Create time: 11/18/2023 - 8:29 PM
 */


package mockproject.backend.service.mail;

import mockproject.backend.domain.dto.user.UserForMail;
import mockproject.backend.domain.entity.other.UserTemporary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.time.LocalDateTime;

import static mockproject.backend.utils.constant.ApiUrl.LOCALHOST;
import static mockproject.backend.utils.constant.Constant.FORGOT_EXPIRATION_TIME;

@Service
public class ThymeleafService {
    private static final String MAIL_TEMPLATE_BASE_NAME = "mail/MailMessages";
    private static final String MAIL_TEMPLATE_PREFIX = "/templates/";
    private static final String MAIL_TEMPLATE_SUFFIX = ".html";
    private static final String UTF_8 = "UTF-8";
    private static final String TEMPLATE_NEW_USER = "mail-create-user";
    private static final String TEMPLATE_RESET_PASSWORD = "mail-reset-password";

    private static TemplateEngine templateEngine;

    static {
        templateEngine = emailTemplateEngine();
    }

    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }

    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    //    new
    public String getContent(UserForMail user) {
        final Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process(TEMPLATE_NEW_USER, context);
    }

    public String getContentResetPassword(UserTemporary user) {
        final Context context = new Context();
        context.setVariable("user", user);
//        "/v1/api/user/forgot/{email}/{code}
        String url = LOCALHOST + "/v1/api/user/forgot/" + user.getEmail() + "/" + user.getCode();
        context.setVariable("create_new_pass", url);
        context.setVariable("expire", LocalDateTime.now().plusSeconds(FORGOT_EXPIRATION_TIME).toString());
        return templateEngine.process(TEMPLATE_RESET_PASSWORD, context);
    }
}