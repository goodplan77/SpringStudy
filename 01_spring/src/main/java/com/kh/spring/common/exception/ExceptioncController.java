package com.kh.spring.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 3. @ControllerAdvice - 어플리케이션 전역에서 에러 감지
@ControllerAdvice
public class ExceptioncController {
	
	// 감지후 메소드 별로 처리 가능?
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e , Model model) {
		e.printStackTrace();
		
		model.addAttribute("errorMsg" , "서비스 이용중 문제가 발생했습니다. 관리자에게 문의해주세요.");
		
		return "common/errorPage";
	}
}
