package com.example.demo.config.auth.exceptionhandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

<<<<<<< HEAD
=======

>>>>>>> 5c16fd128d7ccd751bad90b228909ea937a6e4b3
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		System.out.println("권한 부족 Exception: " + accessDeniedException);
		System.out.println("권한 부족 Message: " + accessDeniedException.getMessage());
		request.setAttribute("msg", accessDeniedException.getMessage());
		request.getRequestDispatcher("/error").forward(request, response);
	}

}
