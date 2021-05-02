package com.churilovich.bortnik.darya.shop.on.sofa.web.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.port}")
    private int mailPort;
    @Value("${mail.username}")
    private String mailUsername;
    @Value("${mail.password}")
    private String mailPassword;
    @Value("${mail.transport.protocol}")
    private String mailTransportProtocol;
    @Value("${mail.smtp.starttls.enable}")
    private String mailSmtpStarttlsEnable;
    @Value("${mail.debug}")
    private String mailDebug;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);
        Properties mailProperties = javaMailSender.getJavaMailProperties();
        mailProperties.put("mail.transport.protocol", mailTransportProtocol);
        mailProperties.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
        mailProperties.put("mail.debug", mailDebug);
        return javaMailSender;
    }
}
