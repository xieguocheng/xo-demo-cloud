package com.xo.common.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: xo
 * @DATE: 2020/8/17
 * @Description: SendMailUtil
 **/
@Slf4j
@Service
public class SendMailUtil {

    /**
     * 默认接收对象
     */
    public final static String DEFAULT_RECEIVE="843781279@QQ.com";

    /**
     * 默认多人接收对象
     */
    public final static String []DEFAULT_MULTI_RECEIVE=new String[]{"843781279@QQ.com"};

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送纯文本邮件
     * @param to            邮件接收方
     * @param subject       邮件主题
     * @param text          邮件内容
     */
    public void sendTextMail( String to, String subject, String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        try{
            javaMailSender.send(simpleMailMessage);
            log.info("邮件已发送。");
        }catch (Exception e){
            log.error("邮件发送失败。" + e.getMessage());
        }
    }

    /**
     * 发送纯文本邮件，群发
     * @param to            邮件接收方
     * @param subject       邮件主题
     * @param text          邮件内容
     */
    public void sendTextMailToMultiUser(String []to,String subject,String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(text);
        try{
            javaMailSender.send(simpleMailMessage);
            log.info("群发邮件已发送。");
        }catch (Exception e){
            log.error("群发邮件发送失败。" + e.getMessage());
        }
    }

    /**
     * 发送带附件的邮件
     * @param to            邮件接收方
     * @param subject       邮件主题
     * @param text          邮件内容
     * @param path          附件所在的文件路径
     */
    public void sendAttachmentMail(String to,String subject,String text,String path){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            // 创建一个multipart格式的message
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text,true);
            // 添加附件信息
            FileSystemResource file = new FileSystemResource(new File(path));
            String fileName = path.substring(path.lastIndexOf(File.separator));
            messageHelper.addAttachment(fileName,file);
            // 发送带附件的邮件
            javaMailSender.send(mimeMessage);
            log.info("邮件发送成功");
        }catch (Exception e){
            log.error("带有附件的邮件发送失败。" + e.getMessage());
        }
    }

    /**
     * 发送带附件的邮件
     * @param to            邮件接收方
     * @param subject       邮件主题
     * @param text          邮件内容
     */
    public void sendAttachmentMail(String to,String subject,String text) throws IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            // 创建一个multipart格式的message
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text,true);
            // 添加附件信息
            File file=new File("AttachmentMail.txt");
            if(file.exists()) {
                FileOutputStream out=new FileOutputStream(file,true);
                out.write(text.getBytes(StandardCharsets.UTF_8));
                out.close();
            }
            messageHelper.addAttachment("AttachmentMail.txt",file);
            // 发送带附件的邮件
            javaMailSender.send(mimeMessage);
            log.info("邮件发送成功");
        }catch (Exception e){
            log.error("带有附件的邮件发送失败。" + e.getMessage());
        }finally {
            File file=new File("AttachmentMail.txt");
            if(file.exists()&&file.isFile()) {
                FileWriter fileWriter =new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
//                file.delete();
            }
        }
    }
}

