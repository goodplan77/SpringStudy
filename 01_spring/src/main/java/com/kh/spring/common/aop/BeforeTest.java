package com.kh.spring.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class BeforeTest {
	
	@Before("CommonPointCut.implPointCut()")
	public void beforeService(JoinPoint jp) {
		// joinpoint -> pointcut 이 들어갈수 있는 메서드
		
		// JoinPoint : advice가 실제로 적용되는 TargetObject에 접근 할 수 있는 메소드를 제공하는 인터페이스.
		// TargetObject 의 클래스 정보 , 전달되는 매개변수 , 메서드명 , 반환값 , 예외처리등등... 얻어 올 수 있음.
	
		// 모든 advice 메서드의 첫번째 매개변수는 JoinPoint 고정
		
		StringBuilder sb = new StringBuilder();
		sb.append("=========================================\n");
		sb.append("start : " + jp.getTarget().getClass().getSimpleName() + "-"); // BoardServiceImpl
		// 실제 적용 되는 TargetObject 의 클래스 정보를 받아오고 , 해당 클래스의 간단한 이름을 가져옴
		sb.append(jp.getSignature().getName());
		// 실제 적용 되는 TargetObject 의 메서드 정보를 받아오고 , 해당 클래스의 간단한 이름을 가져옴
		sb.append("(" + Arrays.toString(jp.getArgs())+ ")");
		// TargetObject 의 매개변수 배열을 가져오고 , 이것을 Arrays.toString 함수를 통해 문자열 형태로 반환
		
		log.debug(sb.toString());
	}
}
