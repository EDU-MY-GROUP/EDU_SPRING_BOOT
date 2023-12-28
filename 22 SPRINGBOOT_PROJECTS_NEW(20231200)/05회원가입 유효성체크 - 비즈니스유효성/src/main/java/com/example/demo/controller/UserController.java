package com.example.demo.controller;

import com.example.demo.domain.dto.CertificationDto;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.service.UserService;
import com.example.demo.properties.EmailAuthProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static java.rmi.server.LogStream.log;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;


    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        // 휴대전화번호에 대한 커스텀 에디터 등록
        dataBinder.registerCustomEditor(String.class, new PhoneNumberEditor());

    }


    @GetMapping(value = "/myinfo" )
    public void user(Authentication authentication , Model model){
        UserController.log.info("GET /user/myinfo...Authentication : " + authentication);
        UserController.log.info("username : " + authentication.getName());
        UserController.log.info("principal : " + authentication.getPrincipal());
        UserController.log.info("authorities : " + authentication.getAuthorities());
        UserController.log.info("details :  " +authentication.getDetails());
        UserController.log.info("credentials : " + authentication.getCredentials());

        model.addAttribute("authentication",authentication);

    }



    @GetMapping("/join")
    public void join(){
        UserController.log.info("GET /join");
    }


    //리다이렉트로 메시지 전달할 때 사용
    @PostMapping("/join")
    public String join_post(@Valid UserDto dto, BindingResult bindingResult, Model model, RedirectAttributes attributes){
        UserController.log.info("POST /join...dto " + dto);
        //파라미터 받기

        //입력값 검증(유효성체크)
        //System.out.println(bindingResult);
        if(bindingResult.hasFieldErrors()){
            for(FieldError error :bindingResult.getFieldErrors()){
                log.info(error.getField() +" : " + error.getDefaultMessage());

                //하나이상의 오류 메시지
                if(model.getAttribute(error.getField())!=null) {
                     ;//model.addAttribute(error.getField(), model.getAttribute(error.getField()) + "\n" + error.getDefaultMessage());;
                }else {
                    model.addAttribute(error.getField(), error.getDefaultMessage());
                }
            }
            return "user/join";
        }

        //서비스 실행
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        boolean isJoin =  userService.memberJoin(dto,model);
        //View로 속성등등 전달
        if(isJoin) {
           // return "redirect:login?msg=MemberJoin Success!";
            attributes.addFlashAttribute("message", "MemberJoin Success");
            return "redirect:login";
        }
        else {
            return "forward:join?msg=Join Failed....";
        }
        //+a 예외처리

    }

    @GetMapping("/certification")
    public String certification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserController.log.info("GET /user/certification");

        if(request.getCookies() !=null) {
            boolean isExisted = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("importAuth")).findFirst()
                    .isEmpty();

            //True/False 가 아니라 우리가 원하는
            if (!isExisted) {
                response.sendRedirect("/user/join");
                return null;
            }
        }
        return "user/certification";
    }



    @GetMapping("/findId")
    public void findId(){
        UserController.log.info("GET /user/findId");}
    @GetMapping("/findPw")
    public void findPw(){
        UserController.log.info("GET /user/findPw");}



    //---------------------
    @PostMapping(value = "/certification",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject>  certification_post(@RequestBody CertificationDto params, HttpServletResponse response) throws IOException {
        UserController.log.info("POST /user/certification.." + params);
        //쿠키로 본인인증 완료값을 전달!(난수값만들기!)
        Cookie authCookie = new Cookie("importAuth","true");
        authCookie.setMaxAge(60*30); //30분동안 유지
        authCookie.setPath("/");
        response.addCookie(authCookie);


        JSONObject obj = new JSONObject();
        obj.put("success",true);

        return  new ResponseEntity<JSONObject>(obj, HttpStatus.OK);


    }

    @GetMapping("/sendMail/{email}")
    @ResponseBody
    public ResponseEntity<JSONObject> sendmailFunc(@PathVariable("email") String email){
        UserController.log.info("GET /user/sendMail.." + email);
        //넣을 값 지정
        String code = EmailAuthProperties.planText;

        passwordEncoder.encode(code);
        //메일 메시지 만들기
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[EMAIL AUTHENTICATION] CODE ");
        message.setText(passwordEncoder.encode(code));

        javaMailSender.send(message);


        return new ResponseEntity(new JSONObject().put("success", true) , HttpStatus.OK);
    }

    @GetMapping("/emailConfirm")
    public @ResponseBody JSONObject emailConfirmFunc(String emailCode){
        UserController.log.info("GET /user/emailConfirm... code : " + emailCode);

        boolean isAuth= passwordEncoder.matches(EmailAuthProperties.planText,emailCode);
        JSONObject obj = new JSONObject();

        if(isAuth) {
            obj.put("success",true);
            obj.put("message","이메일 인증을 성공하셨습니다.");
            return obj;
        }

        obj.put("success",false);
        obj.put("message","이메일 인증을 실패했습니다.");
        return obj;


    }

}

class PhoneNumberEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        // 입력된 문자열에서 하이픈 제거
        String formattedPhoneNumber = text.replaceAll("-", "");
        setValue(formattedPhoneNumber);
    }

    @Override
    public String getAsText() {
        // 필요에 따라 다시 포맷팅이 필요한 경우 구현
        return (String) getValue();
    }
}