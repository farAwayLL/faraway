package com.sboot.study.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.io.File;

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

    @Autowired
    private Environment environment;

    public void sendEmail(ModelMap valueMap) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("mail.send.from"));
        message.setTo(valueMap.get("to").toString().split(","));//可群发，“ , ”分割
        message.setSubject(valueMap.get("subject").toString());
        message.setText(valueMap.get("text").toString());
        mailSender.send(message);
        log.debug("发送简单邮件成功！");
    }

    public void sendAttachmentEmail(ModelMap valueMap) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf8");
        mimeMessageHelper.setFrom(environment.getProperty("mail.send.from"));
        mimeMessageHelper.setTo(valueMap.get("to").toString().split(","));//可群发，“ , ”分割
        mimeMessageHelper.setSubject(valueMap.get("subject").toString());
        mimeMessageHelper.setText(valueMap.get("text").toString());
        //加入附件
        mimeMessageHelper.addAttachment("图片.jpg", new File("E:\\MyFile\\360picture\\225836.jpg"));
        mailSender.send(message);
        log.debug("发送带附件邮件成功！");
    }


    /**
     * 上传文件名长度校验=false  该注解修饰，随着服务初始化而初始化
     */
    @PostConstruct
    public void init(){
        System.setProperty("mail.mime.splitlongparameters","false");
    }
}
