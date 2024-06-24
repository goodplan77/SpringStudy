package com.kh.spring.common.aop;

import org.aspectj.lang.annotation.Pointcut;

// pointcut 모아둔 클래스
public class CommonPointCut {
	
	// 게시판 서비스용 포인트컷
	/*
	 * 반환형 상관 없음
	 * com.kh.spring.board 패키지 아래의 모든 클래스 중
	 * "Impl" 로 끝나는 클래스 내부에 존재 하는 메소드 중
	 * 모든 메소드
	 * 매개변수 0개 이상
	 */
	@Pointcut("execution(* com.kh.spring.board..*Impl.*(..))")
	public void boardPointCut() {}
	
	// 모든 서비스용 포인트컷
	@Pointcut("execution(* com.kh.spring..*Impl.*(..))")
	public void implPointCut() {}
}
