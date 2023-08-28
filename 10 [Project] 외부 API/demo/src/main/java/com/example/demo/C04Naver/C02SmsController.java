package com.example.demo.C04Naver;

import com.example.demo.C04Naver.dto.MessageDto;
import com.example.demo.C04Naver.dto.SmsResponseDto;
import com.example.demo.C04Naver.service.SmsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/th/sms")
public class C02SmsController {
    private final SmsService smsService;
    @GetMapping("/index")
    public void getSmsPage() {

    }


    @PostMapping("/send")
    public String sendSms(MessageDto messageDto, Model model) throws Exception{
        SmsResponseDto response = smsService.sendSms(messageDto);
        model.addAttribute("response", response);
        return "result";
    }
}