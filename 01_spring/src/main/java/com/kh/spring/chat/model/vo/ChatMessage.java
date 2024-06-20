package com.kh.spring.chat.model.vo;

import lombok.Data;

@Data
public class ChatMessage {
	
	private int cmNo; // pk
	private String message;
	private String createDate;
	private int chatRoomNo;
	private int userNo;
	
	private String userName;

}
