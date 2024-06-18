package com.kh.spring.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor // 매개변수 있는 생성자
@Builder
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
// @RequiredArgsConstructor : 필수 매개변수 생성자 (매개변수가 final 인 경우?)
public class BoardType {

	private String boardCode; 	// BOARD_CD
	private String boardName; 	// BOARD_NAME
}
