package com.example.demo.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message="username을 입력하세요")
    private String username;

    @NotBlank(message = "password를 반드시 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "repassword를 반드시 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String repassword;

    @NotBlank(message="연락처를 반드시 입력하세요(-없이입력)")
    private String phone;
    
    @NotBlank(message="zipcode를 입력하세요")
    private String zipcode;

    @NotBlank(message="기본주소를 입력하세요")
    private String addr1;
    private String addr2;

    private String role;

    //OAUTH2
    private String provider;
    private String providerId;
}
