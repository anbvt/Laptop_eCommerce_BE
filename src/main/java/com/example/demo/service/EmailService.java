package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.demo.constants.Constants;
import com.example.demo.entity.Orders;
import com.example.demo.model.MailInfo;
import com.example.demo.model.MailUserInfo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    public void send(MailInfo mail) throws MessagingException {
        // Tạo message
        MimeMessage message = sender.createMimeMessage();
        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        Map<String, Object> map = new HashMap<>();
        map.put("name", mail.getBody().getUser().getFullname());
        map.put("ord", mail.getBody().getOrder_details());
        map.put("id", mail.getBody().getId());
        Context context = new Context();
        context.setVariables(map);
        String htmlBody = templateEngine.process("/util/OrderTemplateEmail", context);
        helper.setText(htmlBody, true);
        // Gửi message đến SMTP server
        sender.send(message);

    }

    public void send(String to, String subject, Orders body) throws MessagingException {
        this.send(new MailInfo(to, subject, body));
    }

    public String sendCode(MailUserInfo mail) throws MessagingException {
        // Tạo message
        MimeMessage message = sender.createMimeMessage();
        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        Map<String, Object> map = new HashMap<>();
        map.put("name", mail.getBody().getFullname());
        map.put("url", Constants.URL);
        // random token
        String token = generateRandomToken();
        map.put("token", token);
        Context context = new Context();
        context.setVariables(map);
        String htmlBody = templateEngine.process("/util/SendOTP", context);
        helper.setText(htmlBody, true);
        // Gửi message đến SMTP server
        sender.send(message);
        return token;
    }

    public static String generateRandomToken() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String token = "";
        for (int i = 0; i < 31; i++) {
            if (i == 5 || i == 13 || i == 17 || i == 21) {
                token += "-";
            }
            token += characters.charAt(random.nextInt(characters.length()));
        }
        return token;
    }

}
