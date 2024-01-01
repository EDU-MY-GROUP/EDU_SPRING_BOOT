package com.example.demo.controller;


import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.service.ImageBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/imageboard")
public class ImageBoardController {

    @Autowired
    private ImageBoardService imageBoardService;

    @GetMapping("/add")
    public void add(){
        log.info("GET /imageBoard/add");
    }

    @PostMapping("/add")
    public void f2_post(ImageBoardDto dto, Authentication authentication) throws IOException {

        log.info("POST /imageBoard/add..."+ dto+" | " + authentication);
        dto.setCreatedAt(LocalDateTime.now());
        PrincipalDetails principal = (PrincipalDetails)authentication.getPrincipal();
        imageBoardService.addImageBoard(dto);

    }

    @GetMapping("/list")
    public void f1(Model model){
        log.info("GET /imageboard/list");
        List<ImageBoard> list =  imageBoardService.getImageboardList();
        System.out.println(list);
        list.stream().forEach(item->System.out.println(item));

        model.addAttribute("list",list);
    }



}
