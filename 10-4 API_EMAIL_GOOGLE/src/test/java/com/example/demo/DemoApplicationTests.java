package com.example.demo;

import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private JavaMailSenderImpl mailSender;
	@Test
	void contextLoads() {
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


		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("jwg8910@naver.com");
		message.setSubject("안녕 나야");
		message.setText("내용내용~");

		mailSender.send(message);

	}

}
