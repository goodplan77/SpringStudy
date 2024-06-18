package com.kh.spring.board.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.Utils;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes({"loginUser"}) // model에 추가된 key값과 동일한 속성을 session 으로 이관
@RequestMapping("/board") // 해당 링크로 요청이 들어오면 해당 객체가 요청 처리
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	private final ServletContext application;
	private final ResourceLoader resourceLoader;
	
	/*
	 * "/resources/*{file}"?
	 */
	
	
	@GetMapping("/list/{boardCode}")
	// 동적 파라미터 설정 : {변수명}
	// 정규식을 활용한 이유? : 여러 url 요청을 하나로 처리 하기 위해서
	// {boardCode} : 하나의 문자열? 정규식 , 패턴과 일치하는 boardCode에 담겠다
	
	public String selectList(
			@PathVariable("boardCode") String boardCode ,
			// @PathVariable 로 등록된 변수는 request 에 자동 저장
			@RequestParam(value="currentPage" , defaultValue = "1") int currentPage,
			// 현재 요청한 페이지
			Model model ,
			// 데이터를 저장할 객체
			@RequestParam Map<String , Object> paramMap
			// 검색시 사용 (key 값 = 변수명 시 생략가능)
			// ? 뒤로 오는 쿼리스트링 값을 key-value 형태로 담음
			) {
		// 페이징 작업 처리
		// 1) 게시글 갯수 카운팅
		int listCount = boardService.selectListCount();
		int pageLimit = 10; // 페이징 바의 갯수
		int boardLimit = 10; // 실제로 보여질 게시물 갯수
		log.info("paramMap?? {}" , paramMap);
		
		PageInfo pi = Pagination.getpageInfo(listCount, currentPage, pageLimit, boardLimit);
			
		List<Board> list = null;
		if(paramMap.isEmpty() && !paramMap.containsKey("currentPage")) {
			list = boardService.selectList(pi);
			model.addAttribute("list", list);
			model.addAttribute("pi",pi);
			return "board/boardListView";
		} else {
			listCount = boardService.searchListCount(paramMap);
			list = boardService.searchList(pi,paramMap);
			
			pi = Pagination.getpageInfo(listCount, currentPage, pageLimit, boardLimit);
			
			model.addAttribute("list", list);
			model.addAttribute("pi",pi);
				
			return "board/boardListView";		
		}
		// 게시글 데이터 조회
		// List<Board> list = boardService.selectList(pi);
		
		// 응답 View 페이지에 게시글 데이터 삽입
		// model.addAttribute("list", list);
		// model.addAttribute("pi",pi);
		
		// viewName 반환
		// return "board/boardListView";
	}
	
	@GetMapping("/insert/{boardCode}")
	public String enrollBoard(@PathVariable("boardCode") String boardCode) {
		return "board/boardEnrollForm";
	}
	
	@PostMapping("/insert/{boardCode}")
	public String insertBoard(
			@PathVariable("boardCode") String boardCode ,
			Board b ,
			@ModelAttribute("loginUser") Member loginUser ,
			// Model 객체 안에 있는 loginUser 를 꺼냄
			// 이때 key=loginUser의 경우 sessionScope에 보관 되어 있음
			Model model , //errorMsg
			RedirectAttributes ra , // 1회성 메세지 저장용 alertMsg
			// 첨부 파일
			@RequestParam(value="upfile" , required=false) MultipartFile upfile
			// upfile에 넘어온 데이터의 경우 , 필수 속성이 아님을 지정
			// 기본값이 true 이므로 반드시 값이 들어가게됨
			) {
		// 업무 로직
		// 1) 웹서버에 클라이언트가 전달한 FILE 저장
		if(upfile != null && !upfile.getOriginalFilename().equals("")) {
			// 첨부파일 , 이미지들을 저장할 저장경로 얻어오기.
			String webPath = "/resources/images/board/"+boardCode+"/";
			String serverFolderPath = application.getRealPath(webPath);
			
			// 디렉토리가 존재하지 않는다면 생성하는 코드 추가
			File dir = new File(serverFolderPath);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			// 사용자가 등록한 첨부파일의 이름을 수정
			String changeName = Utils.saveFile(upfile, serverFolderPath);
		}
		
		// 2) 저장 완료시 파일이 저장된 경로를 BOARD_IMG에 등록후 테이블에 추가
		// -> 1) Board INSERT
		// -> 2) BOARD_IMG INSERT -> 클라이언트가 upfile에 데이터를 작성했을때만.
		
		// 3) 반환값을 통해 메세지 등록
		
		// 4) 응답 페이지 설정
		return "redirect:/board/list/"+boardCode;
	}
}
