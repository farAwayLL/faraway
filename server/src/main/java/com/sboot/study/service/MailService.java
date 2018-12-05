package com.sboot.study.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @Author : faraway
 * @Date : create in 2018/11/13 16:16
 * @Description :
 */

@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

//    方式一
    @Autowired
    private Environment environment;
//    方式二
//    @Value("${mail.send.from}")
//    private String messageFrom;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送简单邮件
     * @param valueMap
     * @throws Exception
     */
    public void sendEmail(ModelMap valueMap) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("mail.send.from"));
        message.setTo(valueMap.get("to").toString().split(","));//可群发，“ , ”分割
        message.setSubject(valueMap.get("subject").toString());
        message.setText(valueMap.get("text").toString());
        mailSender.send(message);
        //log.debug("发送简单邮件成功！");
    }

    /**
     * 发送带附件邮件
     * @param valueMap
     * @throws Exception
     */
    public void sendAttachmentEmail(ModelMap valueMap) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf8");
        mimeMessageHelper.setFrom(environment.getProperty("mail.send.from"));
        mimeMessageHelper.setTo(valueMap.get("to").toString().split(","));//可群发，“ , ”分割
        mimeMessageHelper.setSubject(valueMap.get("subject").toString());
        mimeMessageHelper.setText(valueMap.get("text").toString());
        //加入附件
        mimeMessageHelper.addAttachment(environment.getProperty("mail.send.attachment.one.name"), new File(environment.getProperty("mail.send.attachment.one.location")));
        mailSender.send(message);
        //log.debug("发送带附件邮件成功！");
    }

    /**
     * 发送模板邮件，比如淘宝京东广告邮件
     * @param valueMap
     * @throws Exception
     */
    public void sendHtmlEmail(final ModelMap valueMap) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //true表示开启带附件
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        mimeMessageHelper.setFrom(environment.getProperty("mail.send.from"));
        mimeMessageHelper.setTo(valueMap.get("to").toString().split(","));//可群发，“ , ”分割
        mimeMessageHelper.setSubject(valueMap.get("subject").toString());
        //true表示以html展示
        mimeMessageHelper.setText(valueMap.get("html").toString(), true);

        //加入附件
        //messageHelper.addAttachment(environment.getProperty("mail.send.attachment.one.name"),new File(environment.getProperty("mail.send.attachment.one.location")));

        mailSender.send(mimeMessage);
        //log.info("发送带HTML邮件成功--->");
    }

    /**
     * 渲染模板
     *
     * @param templateFile
     * @param paramMap
     */
    public String renderTemplate(final String templateFile, Map<String, Object> paramMap) {
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariables(paramMap);
        return templateEngine.process(templateFile, context);
    }


    /**
     * 上传文件名长度校验=false 不校验     @PostConstruct该注解修饰，随着类加载而初始化
     */
    @PostConstruct
    public void init() {
        System.setProperty("mail.mime.splitlongparameters", "false");
    }
}
