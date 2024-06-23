package com.kh.study.board.model.vo;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	
	private int no;
	private String title;
	private String subTitle;
	private String content;
	private Date createDate;
	
	private List<BoardImg> imgList;

}
