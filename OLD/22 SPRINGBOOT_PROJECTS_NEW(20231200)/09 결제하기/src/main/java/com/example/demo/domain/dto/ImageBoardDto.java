package com.example.demo.domain.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class ImageBoardDto {
    private Long id;
    private String seller;
    private String productname;
    private String category;
    private String brandname;
    private String itemdetals;
    private String amount;
    private String size;

    private String price;
    private MultipartFile[] files;	//이미지 파일들

    private LocalDateTime createdAt;





}
