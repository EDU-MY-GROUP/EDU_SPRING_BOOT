package com.example.demo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {
    private String no;
    private String username;
    private String title;
    private String content;
    private String regdate;
    private String count;
    private String dirpath;
    private String filename;
    private String filesize;
}
