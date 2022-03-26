package com.zayshop.config;

import com.zayshop.service.EnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private EnvService envService;

    @Bean("mail")
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(envService.getEnvValue("MAIL_USERNAME"));
        javaMailSender.setPassword(envService.getEnvValue("MAIL_PASSWORD"));
        javaMailSender.setHost(envService.getEnvValue("MAIL_HOST"));
        javaMailSender.setPort(Integer.valueOf(envService.getEnvValue("MAIL_PORT")));
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return javaMailSender;
    }
}
