package com.kh.spring.common.scheduling;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kh.spring.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileDeleteScheduler {
	
	// 정적 파일들의 경로를 얻어오기 위한 변수
	private final ServletContext application;
	
	private final BoardService boardService;
	
	@Scheduled(cron = "1 * * * * *")
	public void deleteFile() {
		log.debug("파일 삭제 스케쥴러 시작");
		// 1) board_img안에 있는 모든 파일 목록 조회
		List<String> list = boardService.selectFileList();
		// 서버에 존재하는 파일들의 이름을 문자열 배열 형태로 받아옴 (받아올때 , 경로를 추가해서 저장해놓음)
		log.debug("list {}" , list); // /resources/images.../xxx.jpg
		
		File path = new File(application.getRealPath("/resources/images/board/N/"));
		File[] files = path.listFiles(); // 현재 디렉토리 안에 존재하는 모든 파일을 배열형태로 반환해주는 메소드
		List<File> filesList = Arrays.asList(files); // 배열 -> Collection List 로 변환
		
		if(!list.isEmpty()) {
			for(File serverFile : filesList) {
				String fileName = serverFile.getName(); // 파일 이름 반환
				fileName = "/resources/images/board/N/" + fileName;
				
				// 실제 웹 서버상에는 존재하나 , DB에는 존재하지 않는 파일인 경우
				if(list.indexOf(fileName) == -1) {
					log.debug(fileName+"을 삭제합니다.");
					serverFile.delete();
				}
			}
		}
		
		
		log.debug("파일 삭제 스케쥴러 끝");
	}

}
