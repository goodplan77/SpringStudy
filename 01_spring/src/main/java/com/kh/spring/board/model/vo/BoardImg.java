package com.kh.spring.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardImg {
	private int boardImgNo;
	private String originName;
	private String changeName;
	private int refBno;
	private int imgLevel;

}
