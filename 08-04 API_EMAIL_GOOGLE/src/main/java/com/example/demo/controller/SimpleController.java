package com.example.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;

@Controller
@AllArgsConstructor
public class SimpleController {



    @GetMapping("/index")
    public void index_page(){

    }
    @GetMapping("/main")
    public void main_page(){

    }

    @Autowired
    private  JavaMailSender mailSender;


    @GetMapping("/reqmailSend")
    public String reqMailSend(String email){


//----------------------------------------------------------------------
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jwg135790@gmail.com");
        mailSender.setPassword("gdfdpyfgyhodsusd");

        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(mailProps);

        System.out.println(mailSender);
        System.out.println(mailSender.getHost());
        System.out.println(mailSender.getPort());


        String code = (int)((Math.random()*1000000))+"";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[WEB-Server]임시패스워드 발급");
        message.setText(code);

        mailSender.send(message);



        return "redirect:/index";


    }
}
