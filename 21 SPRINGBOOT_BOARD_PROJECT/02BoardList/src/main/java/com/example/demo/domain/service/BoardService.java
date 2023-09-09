package com.example.demo.domain.service;


import com.example.demo.domain.dto.BoardDto;
import com.example.demo.domain.dto.Criteria;
import com.example.demo.domain.dto.PageDto;
import com.example.demo.domain.entity.Board;
import com.example.demo.domain.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;


    //모든 게시물 가져오기
    public Map<String,Object> GetBoardList(Criteria criteria) {

        Map<String,Object> returns = new HashMap<String,Object>();


        //전체게시물 건수 받기
        int totalcount = (int)boardRepository.count();
        System.out.println("COUNT  :" + totalcount);

        //PageDto 만들기
        PageDto pagedto = new PageDto(totalcount,criteria);

        //시작 게시물 번호 구하기(수정) - OFFSET
        int offset =(criteria.getPageno()-1) * criteria.getAmount();    //1page = 0, 2page = 10

        List<Board> list  =  boardRepository.findBoardAmountStart(pagedto.getCriteria().getAmount(),offset);

        returns.put("list",list);
        returns.put("pageDto",pagedto);

        return returns;


    }





}
