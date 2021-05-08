package com.churilovich.bortnik.darya.shop.on.sofa.web.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {
    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private int mailPort;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);

        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.transport.protocol", "smtp");
        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }
}
