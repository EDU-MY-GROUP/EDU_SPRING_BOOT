package com.example.demo.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class ImageBoardDisplayDto {
    private Long id;
    private String seller;
    private String productname;
    private String category;
    private String brandname;
    private String itemdetals;
    private String amount;
    private String size;

    private String price;

    private String dir;
    private String filename;
    private LocalDateTime createdAt;
}
