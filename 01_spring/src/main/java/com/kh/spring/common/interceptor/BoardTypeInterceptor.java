package com.kh.spring.common.interceptor;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.BoardType;

public class BoardTypeInterceptor implements HandlerInterceptor{
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private ServletContext application; // spring 컨테이너가 보관

	// 전처리함수 : 컨트롤러가 서블릿의 요청을 처리하기 전에 먼저 실행되는 함수
	// DispatcherServlet -> Controller
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 항상 저장시키는게 아니라 처음 한번만 저장
		if(application.getAttribute("boardTypeList") == null) {
			// DB에서 boardTypeList 조회 후 applicationContext에 저장
			List<BoardType> list = boardDao.selectBoardTypeList();
			
			application.setAttribute("boardTypeList", list);
			application.setAttribute("contextPath", request.getContextPath()); // application 영역에 contextpath 저장

		} 
		
		return true;
		
	}
}
