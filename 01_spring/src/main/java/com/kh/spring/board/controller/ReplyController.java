package com.kh.spring.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.board.model.service.ReplyService;
import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@RequestMapping("/reply")
@RequiredArgsConstructor // 생성자 자동 생성 , final 키워드만 생성해줌
@RestController
/*
 * @ResponseBody + @Controller : 요청에 대한 응답값이 모두 값 그자체인 컨트롤러
 * 메서드들 내부에는 비동기방식으로 통신하는 메서드들로만 구현되어 있을 예정
 * 
 * Rest(Representational State Transfer) : 자원을 url 이름으로 구분하여 자원의 상태(state)를 주고 받는것
 */
public class ReplyController {

	private final ReplyService service;
	
	// 댓글목록 조회 (비동기)
	@GetMapping("/selectReplyList")
	public List<Reply> selectReplyList(int boardNo) {
		List<Reply> rList = service.selectReplyList(boardNo);
		return rList;
	}
	
	// 댓글 등록 (비동기)
	@PostMapping("/insertReply")
	public int insertReply(Reply r /*커맨드 객체*/) {
		return service.insertReply(r);
	}
	
	// 댓글 삭제 , 수정 (비동기)
	
	@PostMapping("/update")
	public int updateReply(Reply r /*커맨드 객체*/) {
		return service.updateReply(r);
	}
}
