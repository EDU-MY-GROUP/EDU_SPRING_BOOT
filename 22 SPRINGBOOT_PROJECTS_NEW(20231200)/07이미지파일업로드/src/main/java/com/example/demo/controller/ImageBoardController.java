package com.example.demo.controller;


import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.entity.ImageBoardFileInfo;
import com.example.demo.domain.repository.ImageBoardFileInfoRepository;
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
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/imageboard")
public class ImageBoardController {

    @Autowired
    private ImageBoardService imageBoardService;

    @Autowired
    private ImageBoardFileInfoRepository imageBoardFileInfoRepository;
    @GetMapping("/add")
    public void add(){
        log.info("GET /imageBoard/add");
    }

    @PostMapping("/add")
    public void f2_post(ImageBoardDto dto, Authentication authentication) throws IOException {

        log.info("POST /imageBoard/add..."+ dto+" | " + authentication);
        dto.setCreatedAt(LocalDateTime.now());
        imageBoardService.addImageBoard(dto);

    }

    @GetMapping("/list")
    public void f1(Model model){
        log.info("GET /imageboard/list");

        List<ImageBoardFileInfo> filelist =  imageBoardFileInfoRepository.findAll();

//        filelist.forEach(item->System.out.println(item));

        // ImageBoard의 id를 기준으로 중복을 제거하여 Map을 생성합니다.
        Map<Long, ImageBoardFileInfo> uniqueItemsById = filelist.stream()
                .collect(Collectors.toMap(item -> item.getImageBoard().getId(), Function.identity(), (existing, replacement) -> existing));
        // 중복이 제거된 값들을 다시 List로 변환합니다.
        List<ImageBoardFileInfo> uniqueFileList = uniqueItemsById.values().stream().collect(Collectors.toList());

        // 결과를 출력합니다.
        uniqueFileList.forEach(item -> System.out.println(item));

        model.addAttribute("list",uniqueFileList);

    }

    @GetMapping("/read")
    public void read(Long id,Model model){
        log.info("GET /imageboard/read...id :" + id);


        List<ImageBoardFileInfo> list=  imageBoardFileInfoRepository.findByImageBoardId(id);
        list.forEach(item->System.out.println(item));
        model.addAttribute("list",list);


    }


}
