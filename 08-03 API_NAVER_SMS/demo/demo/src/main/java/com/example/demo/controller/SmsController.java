package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.MessageDto;
import com.example.demo.dto.SmsResponseDto;
import com.example.demo.service.SmsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SmsController {
    private final SmsService smsService;
    @GetMapping("/send")
    public String getSmsPage() {
        return "sendSms";
    }
    @PostMapping("/sms/send")
    public String sendSms(MessageDto messageDto, Model model) throws Exception{
        SmsResponseDto response = smsService.sendSms(messageDto);
        model.addAttribute("response", response);
        return "result";
    }
}