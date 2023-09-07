package com.example.demo.domain.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class BoardDto {
	private String no;
	private String email;
	private String title;
	private String content;
	private String regdate;
	private String count;
	private String dirpath;
	private String filename;
	private String filesize;

}