package com.example.demo.domain.dto;


import com.example.demo.domain.entity.Board;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BoardDto {



    private Long no;
    @NotBlank(message = "username을 입력하세요")
    @Email(message = "유효한 이메일 주소를 입력하세요")
    private String username;
    private String title;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regdate;
    private Long count;
    private String dirpath;
    private String filename;
    private Long filesize;



    public static BoardDto Of(Board board) {
        BoardDto dto = new BoardDto();
        dto.no = board.getNo();
        dto.title=board.getTitle();
        dto.content = board.getContent();
        dto.regdate = board.getRegdate();
        dto.count = board.getCount();
        dto.dirpath = board.getDirpath();
        dto.filename = board.getFilename();
        dto.filesize = board.getFilesize();
        dto.username  = board.getUsername();
        return dto;
    }
}
