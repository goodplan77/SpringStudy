package com.kh.study.common.interceptor;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.study.board.model.dao.BoardDao;
import com.kh.study.board.model.vo.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor{
	
	private final BoardDao boardDao;
	
	private final ServletContext application;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(application.getAttribute("boardList") == null) {
			List<Board> boardList = boardDao.selectBoardList();
			application.setAttribute("boardList", boardList);
			application.setAttribute("contextPath", request.getContextPath());
		}
			
		return true;
		
	}
}
