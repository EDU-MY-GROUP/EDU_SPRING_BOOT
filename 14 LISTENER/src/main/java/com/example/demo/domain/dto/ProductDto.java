package com.example.demo.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	private int productCode;		//제품 코드
	private MultipartFile[] files;	//이미지 파일들
	private String productName;		//제품 이름
	private String productType;		//제품 종류
}
