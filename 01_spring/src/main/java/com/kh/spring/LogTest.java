package com.kh.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogTest {
	
	/*
	 * Loggin Level
	 * - fatal	: 아주 심각한 에러 -> 지금은 존재하지 않음
	 * - error	: 요청처리중 발생하는 오류에 사용(Exception 클래스에서 사용)
	 * - warn	: 경고성 메세지. 실행에는 문제 없지만 , 향후 오류가 발생할 경우가 있을때 사용
	 * - info	: 요청처리중 발생하는 정보성 메세지 출력시 사용
	 * - debug	: 개발중 필요한 로그가 있을 경우 사용
	 * - trace	: 개발용 debug범위를 한정해서 출력 
	 */
	
	// Logger객체 생성방식1
	// Logger log = LoggerFactory.getLogger(LogTest.class);

	// Logger객체 생성방식2
	// Logger log = LoggerFactory.getLogger(this.getClass());
	
	// Logger객체 생성방식3
	// lombok 으로 로거 객체 얻어오기
	
	public static void main(String[] args) {
		log.error("error - {}" , "에러메세지"); // error - 에러메세지
		log.warn("warn - {}" , "경고메세지"); // warn - 경고메세지
		log.info("info - {}" , "인포메세지");
		log.debug("debug - {}" , "디버그"); // 무시됨 - log4j.xml -> info 레벨 이상만 출력
		log.trace("trace - {}" , "트레이스"); // 무시됨 - log4j.xml -> info 레벨 이상만 출력
	}
}
