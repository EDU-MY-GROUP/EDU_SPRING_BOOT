package com.example.demo.C05GoogleAPI;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;

@Controller
@Slf4j
@RequestMapping("/google/mail")
public class C01GoogleMailAPIController {


    @GetMapping("/request")
    public @ResponseBody String getRequest(String email)
    {

        //메일 설정
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jwg135790@gmail.com");
        mailSender.setPassword("djfucqgdihudefec");

        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(mailProps);


        System.out.println(mailSender);
        System.out.println(mailSender.getHost());
        System.out.println(mailSender.getPort());

        //넣을 값 지정
        String code = (int)((Math.random()*1000000))+"";

        //메일 메시지 만들기
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[WEB-Server]임시패스워드 발급");
        message.setText(code);


        //
        mailSender.send(message);



        return "";


    }


//----------------------------------------------------------------
// Bean 으로 등록
//----------------------------------------------------------------
//    @Bean
//    public JavaMailSender mailSender(String email, String password) {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(host);
//        mailSender.setUsername(email);
//        mailSender.setPassword(password);
//        mailSender.setPort(port);
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.smtp.auth",auth);
//        props.put("mail.debug",debug);
//        props.put("mail.smtp.starttls.enable",enable);
//        props.put("mail.mime.charset",charset);
//        props.put("mail.transport.protocol",protocol);
//        return mailSender;
//    }

}
