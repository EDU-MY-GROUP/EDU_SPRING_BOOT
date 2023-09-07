//package com.example.demo.restcontroller;
//
//
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.test.dto.AuthDto;
//import com.test.dto.ReplyDto;
//import com.test.service.BoardService;
//
//@RestController
//@RequestMapping("/board")
//public class BoardRestController {
//
//	@Autowired
//	BoardService service;
//
//
//	@GetMapping(value="/download",produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	public ResponseEntity<Resource> func1(String uuid,String filename,HttpServletRequest req,Authentication authentication)throws Exception {
//
//		//이메일정보 확인
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//	    String email =  userDetails.getUsername();
//
//		//경로설정
//		String path=req.getSession().getServletContext().getRealPath("/");
//		path+="upload"+File.separator+email+File.separator+uuid;
//
//		System.out.println("PATH : " + path);
//
//		//FileSysteResource : 파일시스템의 특정 파일로 부터 정보를 읽어온다.
//		Resource resource = new FileSystemResource(path+File.separator+filename);
//		//파일명 추출
//		String resourcename = resource.getFilename();
//		//헤더정보 수정위함(파일전송용)
//		HttpHeaders headers = new HttpHeaders();
//		//ISO-8859-1 : 라틴어(특수문자등 깨짐 방지)
//		headers.add("Content-Disposition","attachment; filename="+new String(resourcename.getBytes("UTF-8"),"ISO-8859-1"));
//		//리소스,파일정보가포함된 헤더,상태정보를 전달
//		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
//
//	}
//
//	@GetMapping("/downloadzip")
//	public void downloadzip(HttpServletRequest req,HttpServletResponse resp,Authentication authentication) {
//		//유효성
//
//		//서비스
//		service.downloadzip(req, resp,authentication);
//
//		//뷰생략
//	}
//
//	@GetMapping("/replypost")
//	public void replypost(String bno,String comment,HttpServletRequest req,Authentication authentication ) {
//		System.out.println("/board/replypost..bno : " + bno + " comment : " + comment);
//
//		//2 유효성체크
//
//		//3 서비스
//
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//	    String email =  userDetails.getUsername();
//
//		ReplyDto rdto = new ReplyDto();
//		rdto.setBno(bno);
//		rdto.setContent(comment);
//		rdto.setEmail(email);
//
//		boolean flag=service.replyPost(rdto);
//
//	}
//
////	@GetMapping(value="/replylist",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@GetMapping("/replylist")
//	public ResponseEntity<List<ReplyDto>> replylist(int bno,HttpServletResponse resp,Model model) throws IOException {
//
//		//2 유효성 체크
//
//		//3 서비스
//		List<ReplyDto> list=service.replyList(bno);
//		list.forEach(dto->{System.out.println(dto);});
//		PrintWriter out = resp.getWriter();
//		for(ReplyDto rdto : list)
//		{
//
//			out.println("<div class='reply-content' style='display:flex;justify-contents:left;position:relative;overflow:auto'>");
//			out.println("	<div style='margin:10px;'>");
//			out.println("		<i class='bi bi-person-circle' style='font-size:2rem'></i>");
//			out.println("	</div>");
//			out.println("	<div  style='margin:10px;'>");
//			out.println("		<div style=font-size:0.8rem><span>"+rdto.getEmail()+"</span> <span>"+rdto.getRegdate()+"</span></div>");
//			out.println("		<div>"+rdto.getContent()+"</div>");
//			out.println("		<div style='display:flex;justify-contents:left;'>");
//			out.println("			<span class='material-icons' style='color:gray;'>thumb_up_alt</span>&nbsp;&nbsp;");
//			out.println("			<span class='material-icons' style='color:gray;'>thumb_down_alt</span>&nbsp;");
//			out.println("		</div>");
//			out.println("	</div>");
//			out.println("	<div style='position:absolute;right:0px;'>");
//			out.println("		<i class=\"bi bi-three-dots-vertical\"></i>");
//			out.println("	</div>");
//			out.println("</div>");
//		}
//
//		//return new ResponseEntity<>(list,HttpStatus.OK);
//		return null;
//	}
//
//
//	@GetMapping("/replycnt")
//	public void replycnt(int bno,HttpServletResponse resp) throws IOException {
//
//		int count = service.getReplyCount(bno);
//
//		PrintWriter out = resp.getWriter();
//		out.println(count);
//
//	}
//
//
//
//
//}
//
//
//
