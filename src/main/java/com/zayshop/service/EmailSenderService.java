package com.zayshop.service;

import com.zayshop.dto.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailSenderService {

    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(Mail mail) throws MessagingException {
        // Tạo message
        MimeMessage message = mailSender.createMimeMessage();
        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(mail.getMailTo());
        helper.setSubject(mail.getMailSub());
        helper.setText(mail.getMailContent(), true);
        // Gửi message đến SMTP server
        mailSender.send(message);
    }

}
