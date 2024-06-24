package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@Order(2)
public class ArroundTest {
	
	@Around("CommonPointCut.implPointCut()")
	public Object checkRunningTime(ProceedingJoinPoint jp) throws Throwable {
		// ProceedingJoinPoint ? Before/After 처리기능을 함께 제공. 리턴값을 얻어올수 있는 메서드도 함께 제공
		
		// before 코드
		long start = System.currentTimeMillis();
		
		Object obj = jp.proceed(); // 메서드 실행. (실제 핵심 업무)
		
		// after 코드
		
		long end = System.currentTimeMillis();
		
		log.debug("Running Time : {} ms" , (end - start));
		
		return obj;
	}
}
