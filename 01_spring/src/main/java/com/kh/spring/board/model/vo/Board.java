package com.kh.spring.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //@RequiredArgsConstructor : final 키워드만 생성해서 만들어줌
@AllArgsConstructor // 매개 변수 있는 생성자
@NoArgsConstructor // 매개변수 없는 기본 생성자
@Builder
public class Board {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter; // id, userNo, name
	private int count;
	private Date createDate;
	private String status;
	private String boardCd;

}
