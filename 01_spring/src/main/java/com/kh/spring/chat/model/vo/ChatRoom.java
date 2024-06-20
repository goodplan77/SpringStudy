package com.kh.spring.chat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // final 키워드 필드만 getter // setter 메서스 생성
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
	
	private int chatRoomNo;
	private String title;
	private String status;
	private int userNo;
	
	private String userName;
	private int cnt;

}
