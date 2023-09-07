package com.example.demo.controller;


import com.example.demo.domain.dto.BoardDto;
import com.example.demo.domain.dto.Criteria;
import com.example.demo.domain.dto.PageDto;
import com.example.demo.domain.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {

	@Autowired
//	BoardService service;
	
	@GetMapping("/list")
	public String doGet(Integer pageno, HttpServletRequest req, HttpServletResponse resp) {
			log.info("board list...pageno : " + pageno);
			System.out.println(req.getSession().getServletContext().getRealPath("/"));
	
			//1 파라미터
			
			//2 유효성
			
			//3 서비스
			Criteria criteria=null;
			if(pageno==null) 	//최초 /board/list 접근
			{
				criteria = new Criteria(); //pageno=1 , amount=10
			}
			else
			{
				//페이지이동 요청 했을때
				criteria = new Criteria(pageno,10);
			}
			//boolean result=service.GetBoardList(criteria,req,resp);
			
			//4 뷰
			return "/board/list";		
	}


//
//	@GetMapping("/post")
//	public void dogetPost() {
//		log.info("board get/post....");
//	}
//
//	@PostMapping("/post")
//	public String doPostPost(BoardDto dto, @RequestParam("files") MultipartFile[] uploadfiles, HttpServletRequest req) {
//		log.info("board post/post....");
//
//		//유효성 체크(생략)
//
//		//서비스 실행
//		boolean result = service.PostBoard(dto, uploadfiles,req,authentication);
//		System.out.println("RESULT :" + result);
//		//뷰이동
//		return "redirect:/board/list";
//	}
//
//
//	@GetMapping("/read")
//	public String dogetRead(int bno, int pageno, HttpServletRequest req, HttpServletResponse resp, Model model) {
//		log.info("board read....");
//
//		//유효성(생략)
//
//		//서비스
//		service.GetBoard(bno, req, resp);
//		//뷰(생략 /board/read)
//		return "board/read";
//	}
//
//
//
//
//	@GetMapping("/update")
//	public void update(int pageno) {
//		log.info("board update....");
//	}
//	@PostMapping("/update")
//	public String postupdate(BoardDto dto,HttpServletRequest req) {
//
//		//3 Service 실행
//		System.out.println("UPDATE DTO : " +dto);
//		service.boardUpdate(dto,req);
//		HttpSession session = req.getSession();
//		BoardDto sdto =(BoardDto)session.getAttribute("boarddto");
//		PageDto pdto = (PageDto)session.getAttribute("pagedto");
//		return "redirect:/board/read?bno="+sdto.getNo()+"&pageno="+pdto.getCriteria().getPageno();
//	}
//
//	@GetMapping("/removefile")
//	public String removefile(String filename,String filesize,String dirpath,HttpServletRequest req,Authentication authentication) {
//
//
//		service.removefile(filename,filesize,dirpath,req,authentication);
//
//
//		HttpSession session = req.getSession(false);
//		PageDto pdto = (PageDto)session.getAttribute("pagedto");
//		int pageno = pdto.getCriteria().getPageno();
//		System.out.println("REMOVEFILE : " + pageno);
//		return "redirect:/board/update?pageno="+pageno;
//	}
//
//	@GetMapping("/delete")
//	public String delete(int pageno,int bno , HttpServletRequest req,HttpServletResponse resp,Authentication authentication) {
//
//		service.RemoveBoard(req,resp,authentication);
//
//		return "redirect:/board/list?pageno="+pageno;
//	}


	
}
