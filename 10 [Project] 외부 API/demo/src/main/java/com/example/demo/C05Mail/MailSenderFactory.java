package com.example.demo.C05Mail;

import org.springframework.mail.javamail.JavaMailSender;

public interface MailSenderFactory {
    JavaMailSender getSender(String email, String password);
}
