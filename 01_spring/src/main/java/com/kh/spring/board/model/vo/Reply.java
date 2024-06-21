package com.kh.spring.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
	
	private int replyNo;
	private String replyContent;
	private int refBno;
	private int replyWriter;
	private String createDate;
	private String status;
	
	// 회원의 이름 저장용
	private String userName;
}
