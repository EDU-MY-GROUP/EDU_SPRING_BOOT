//package com.example.demo.domain.service;
//
//
//import com.example.demo.domain.dto.BoardDto;
//import com.example.demo.domain.dto.Criteria;
//import com.example.demo.domain.dto.PageDto;
//import lombok.extern.log4j.Log4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//@Service
//@Log4j
//public class BoardService {
//
//
//	@Autowired
//	BoardMapper dao;
//
//
//	private String uploadDir;
//
//
//	//모든 게시물 가져오기
//	public boolean GetBoardList(Criteria criteria, HttpServletRequest request, HttpServletResponse response) {
//
//		//쿠키 생성(/board/read.do 새로고침시 조회수 반복증가를 막기위한용도)
//		Cookie init = new Cookie("reading","true");
//		response.addCookie(init);
//
//		//전체게시물 건수 받기
//		int totalcount = dao.getAmount();
//
//		//PageDto 만들기
//		PageDto pagedto = new PageDto(totalcount,criteria);
//
//		//시작 게시물 번호 구하기(수정)
//		int startno = criteria.getPageno()*criteria.getAmount()-criteria.getAmount();
//
//		//PageDto를 Session에 추가
//		HttpSession session = request.getSession(false);
//
//		List<BoardDto> list = dao.SelectAll(startno,criteria.getAmount());
//		if(list!=null) {
//			request.setAttribute("list", list);
//			session.setAttribute("pagedto", pagedto);
//			return true;
//		}
//		return false;
//	}
//
//
//
//
//	//게시물 추가하기(업로드포함)
//	@Transactional(rollbackFor=Exception.class)
//	public boolean PostBoard(BoardDto dto, MultipartFile[] uploadfiles, HttpServletRequest request, Authentication authentication)
//	{
//		boolean flag=false;
//
//
//		//디렉토리 경로 설정
//		System.out.println("DIRPATH : " + request.getSession().getServletContext().getRealPath("/"));
//		uploadDir=request.getSession().getServletContext().getRealPath("/")+"upload";
//
//		//접속한 Email 계정 확인!!!!!!!수정할 것
//		 UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//	     String username =  userDetails.getUsername();
//
//		//UUID(랜덤값) 폴더생성
//		UUID uuid = UUID.randomUUID();
//		String path = uploadDir+ File.separator+username+File.separator+uuid;
//
//		//추출된 파일정보 저장용 Buffer
//		StringBuffer filelist = new StringBuffer();
//		StringBuffer filesize= new StringBuffer();
//
//
//		try {
//
//			if(uploadfiles!=null)
//			{
//
//
//				for(MultipartFile part : uploadfiles)
//				{
//
//
//						//파일명 추출
//						String filename=part.getOriginalFilename();
//
//						if(!filename.equals(""))
//						{
//
//							File savefile = new File(path,filename);
//							//폴더생성
//							File dir = new File(path);
//							if(!dir.exists()) {
//								dir.mkdirs();
//							}
//							//업로드
//							part.transferTo(savefile);
//
//							//파일명 DB Table에 추가 위한 저장
//							filelist.append(filename+";");
//							//디렉토리 경로 DB Table에 추가 위한 저장
//							filesize.append(part.getSize()+";");
//						}
//				}
//				dto.setDirpath(uuid+"");
//				dto.setFilename(filelist.toString());
//				dto.setFilesize(filesize.toString());
//			}
//
//			System.out.println("POST : " + dto);
//			//DB연결
//			int result=dao.Insert(dto);
//			if(result>0)
//				flag=true;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//		return flag;
//	}
//
////	@Transactional(rollbackFor=Exception.class)
//	public boolean GetBoard(int bno, HttpServletRequest req,HttpServletResponse resp) {
//
//			boolean flag=false;
//
//			//쿠키 확인 후  CountUp(/board/read.do 새로고침시 조회수 반복증가를 막기위한용도)
//			Cookie[] cookies = req.getCookies();
//			for(Cookie cookie:cookies)
//			{
//				if(cookie.getName().equals("reading"))
//				{
//					if(cookie.getValue().equals("true"))
//					{
//						//CountUp
//						dao.Update(bno);
//
//						//쿠키 value 변경
//						cookie.setValue("false");
//						resp.addCookie(cookie);
//					}
//				}
//			}
//
//			BoardDto dto = dao.Select(bno);
//			if(dto!=null) {
//				//session에 추가한 정보
//				//pagedto / authdto / + boarddto
//				HttpSession session = req.getSession(false);
//				session.setAttribute("boarddto", dto);
//				flag=true;
//			}
//
//		return flag;
//	}
//
//
////	@Transactional(rollbackFor=Exception.class)
//	public void downloadzip(HttpServletRequest req, HttpServletResponse resp,Authentication authentication) {
//		try {
//		//파라미터
//		//String filename=req.getParameter("filename");
//		String uuid=req.getParameter("uuid");
//		//이메일정보 확인
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//	    String email =  userDetails.getUsername();
//
//		//경로설정
//		String path=req.getSession().getServletContext().getRealPath("/");
//		path+="upload"+File.separator+email+File.separator+uuid;
//
//
//
//		//--------------
//		FileInputStream fin=null;
//		ZipEntry zipEntry = null;
//		File dir = new File(path);
//		File filelist[] = dir.listFiles();
//
//		//헤더설정
//		resp.setHeader("Content-Type","application/octet-stream;charset=utf-8");
//		resp.setHeader("Content-Disposition","attachment; filename=All.zip");
//
//		//스트림형성
//		ServletOutputStream bout = resp.getOutputStream();
//		ZipOutputStream zout = new ZipOutputStream(bout);
//
//
//		byte[] buff = new byte[4096];
//		for(File file:filelist)
//		{
//			fin = new FileInputStream(file);
//			ZipEntry zip = new ZipEntry(file.getName().toString());
//			zout.putNextEntry(zip);
//			while (true)
//			{
//				int data = fin.read(buff,0,buff.length);
//				if (data == -1)
//					break;
//				zout.write(buff,0,data);
//			}
//
//			zout.closeEntry();
//			fin.close();
//		}
//		zout.close();
//
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//	public boolean replyPost(ReplyDto rdto) {
//		boolean flag=false;
//		System.out.println("REPLYSERVICE : rdto : "+ rdto);
//		int result= dao.InsertReply(rdto);
//		if(result>0)
//			flag=true;
//
//		return flag;
//
//	}
//
//
//	public List<ReplyDto> replyList(int bno) {
//		return dao.ReplySelectAll(bno);
//	}
//
//
//
//	public int getReplyCount(int bno) {
//
//		return dao.getReplyAmount(bno);
//
//	}
//
//	//파일삭제
//	public void removefile(String filename,String filesize,String dirpath,HttpServletRequest req,Authentication authentication) {
//		try {
//
//			//디렉토리 경로 설정
//			System.out.println("DIRPATH : " + req.getSession().getServletContext().getRealPath("/"));
//			uploadDir=req.getSession().getServletContext().getRealPath("/")+"upload";
//
//			//접속한 Email 계정 확인
//			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//		    String email =  userDetails.getUsername();
//
//			//UUID(랜덤값) 폴더생성
//			String uuid = dirpath;
//			String path = uploadDir+File.separator+email+File.separator+uuid;
//
//			//파일경로 확인
//			String filepath = path+File.separator+filename;
//			System.out.println("파일삭제 경로 : " + filepath);
//			//파일삭제
//
//			 boolean flag = new File(filepath).delete();
//			 System.out.println("삭제 여부 : " + flag);
//
//
//
//
//			//세션 boarddto 재설정
//			HttpSession session = req.getSession(false);
//			BoardDto bdto = (BoardDto)session.getAttribute("boarddto");
//			String filenames = bdto.getFilename();
//			String filesizes = bdto.getFilesize();
//			filenames=filenames.replace(filename+";", "");
//			filesizes=filesizes.replace(filesize+";", "");
//			bdto.setFilename(filenames);
//			bdto.setFilesize(filesizes);
//			session.setAttribute("boarddto", bdto);
//			System.out.println("파일삭제전 BDTO : " + bdto);
//			//db 삭제
//			dao.RemoveFile(Integer.parseInt(bdto.getNo()),filenames,filesizes);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}
//	public void boardUpdate(BoardDto dto,HttpServletRequest req) {
//		HttpSession session = req.getSession(false);
//
//		BoardDto Sessiondto = (BoardDto)session.getAttribute("boarddto");
//
//		dto.setNo(Sessiondto.getNo());
//		dao.UpdateBoard(dto);
//	}
//
//
//	@Transactional(rollbackFor=Exception.class)
//	public boolean RemoveBoard(HttpServletRequest req, HttpServletResponse resp,Authentication authentication) {
//
//		boolean flag = false;
//		//------------------------------------
//		Map<String,String[]> params = req.getParameterMap();
//
//		//디렉토리 경로 설정
//		System.out.println("DIRPATH : " + req.getSession().getServletContext().getRealPath("/"));
//		uploadDir=req.getSession().getServletContext().getRealPath("/")+"upload";
//
//		//접속한 Email 계정 확인
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//	    String email =  userDetails.getUsername();
//
//		//UUID(랜덤값) 폴더생성
//		String uuid = params.get("dirpath")[0];
//		String path = uploadDir+File.separator+email+File.separator+uuid;
//
//
//		//파일삭제
//		File folder = new File(path);
//		File[] folder_list=null;
//		if(folder.exists())
//		{
//			folder_list = folder.listFiles(); //파일리스트 얻어오기
//			for(File file :folder_list) {
//				file.delete();
//			}
//			folder.delete(); //폴더 삭제
//		}
//
//
//		//-------------------------------------
//		String bno = req.getParameter("bno");
//
//		int result = dao.Delete(bno);
//		if(result>0)
//			flag=true;
//		return flag;
//	}
//
//
//
//
//}
