package com.example.demo.controller;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Properties;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    public static boolean isEmailAuth;


    @Autowired
    private UserService userService;


    @GetMapping("/mypage")
    public void user(Authentication authentication) {
        log.info("GET /user/mypage...");
        System.out.println("authentication : " + authentication);
        System.out.println("name : " + authentication.getName());
        System.out.println("principal : " + authentication.getPrincipal());
        System.out.println("authorities : " + authentication.getAuthorities());
        System.out.println("detail : " + authentication.getDetails());
        System.out.println("credential : " + authentication.getCredentials());


    }





    @GetMapping("/error")
    public void error() {
        log.info("GET /error...");
    }


    @GetMapping("/login")
    public void login(){
        log.info("GET /user/login");
    }


    @GetMapping("/join")
    public void join_get() {
        log.info("GET /join");
    }

    @PostMapping("/join")
    public String join_post(@Valid UserDto dto, BindingResult bindingResult, Model model,HttpServletRequest request) {
        log.info("POST /join "+dto);

        //01

        //02
        if(bindingResult.hasFieldErrors()) {
            for( FieldError error  : bindingResult.getFieldErrors()) {
                log.info(error.getField()+ " : " + error.getDefaultMessage());
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "/user/join";
        }

        HttpSession session = request.getSession();
        boolean isEmailAuth = (boolean)session.getAttribute("isEmailAuth");


        //03

        if(isEmailAuth) //인증된 이메일 계정인지 확인
        {
            boolean isjoin = userService.joinMember(dto, model);
            if (!isjoin) {
                return "/user/join";
            }
        }else{
            model.addAttribute("username","이메일 인증 실패");

        }


        //04
        return "redirect:login?msg=Join_Success!";

    }

    //----------------------------------------------------------------

    //-------------------
    //메일인증
    //-------------------

    private boolean isIsEmailAuth;
    @GetMapping(value = "/auth/email/{username}" )
    public @ResponseBody void email_auth(@PathVariable String username , HttpServletRequest request){
        System.out.println("GET /user/auth/email " + username);

        //메일설정
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jwg135790@gmail.com");
        mailSender.setPassword("qysh uudz wbpo vbud");

        Properties props = new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        mailSender.setJavaMailProperties(props);

        //난수값생성
        String tmpPassword = (int)(Math.random()*10000000)+"";

        //본문내용 설정
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username);
        message.setSubject("[WEB_TEST]EMAIL 인증번호");
        message.setText(tmpPassword);

        //발송
        mailSender.send(message);

        //세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("tmp_auth_value",tmpPassword);

    }

    @GetMapping("/auth/confirm/{code}")
    public @ResponseBody String email_auth_confirm(@PathVariable  String code,Model model,HttpServletRequest request){
        HttpSession  session = request.getSession();
        String tmp_auth_value= (String)session.getAttribute("tmp_auth_value");
        if(tmp_auth_value!=null)
        {
            if(tmp_auth_value.equals(code)) {
                session.setAttribute("isEmailAuth", true);
                //model.addAttribute("username","인증성공");
                return "success";
            }
            else {
                session.setAttribute("isEmailAuth", true);
                //model.addAttribute("username","인증실패");
                return "fail";
            }

        }
        return "fail";


    }



    @GetMapping("/myAuthNumber")
    public @ResponseBody void get_my_number(HttpServletRequest request) {
        HttpSession  session = request.getSession();
        String tmp_auth_value= (String)session.getAttribute("tmp_auth_value");
        log.info("GET /user/myAuthNumber : " + tmp_auth_value);
    }





}
