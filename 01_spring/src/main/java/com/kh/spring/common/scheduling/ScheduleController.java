package com.kh.spring.common.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduleController {

	// @Scheduled(fixedDelay = 1000) //1000ms = 1sec 마다 메소드 실행
	public void test() {
		log.debug("1 초마다 출력");
	}
	
	// @Scheduled(cron = "0/1 * * * * *")
	public void testCron() {
		log.debug("크론탭방식 스케쥴링");
	}
}
