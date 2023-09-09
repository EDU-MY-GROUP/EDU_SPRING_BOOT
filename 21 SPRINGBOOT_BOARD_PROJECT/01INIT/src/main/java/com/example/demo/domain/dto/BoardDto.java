package com.example.demo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {
    private Long no;
    private String username;
    private String title;
    private String content;
    private LocalDate regdate;
    private String count;
    private String dirpath;
    private String filename;
    private String filesize;
}
