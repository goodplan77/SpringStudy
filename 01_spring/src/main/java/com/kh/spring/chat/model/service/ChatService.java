package com.kh.spring.chat.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.chat.model.dao.ChatDao;
import com.kh.spring.chat.model.vo.ChatMessage;
import com.kh.spring.chat.model.vo.ChatRoom;
import com.kh.spring.chat.model.vo.ChatRoomJoin;
import com.kh.spring.common.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	
	private final ChatDao chatDao;

	public List<ChatRoom> selectChatRoomList() {
		return chatDao.selectChatRoomList();
	}

	public int openChatRoom(ChatRoom room) {
		return chatDao.openChatRoom(room);
	}

	public List<ChatMessage> joinChatRoom(ChatRoomJoin join) {
		// 1. 채팅방에 참여
		int result = 1;
		List<ChatMessage> list = null;
		try {
			result = chatDao.joinChatRoom(join);
		} catch (Exception e) {
			
		}
		// 2. 채팅 메세지 조회
		if(result > 0) {
			list = chatDao.selectChatMessage(join.getChatRoomNo());
		} 
		return list;
	}

	public int insertMessage(ChatMessage chatMessage) {
		// XSS 핸들링
		// 개행 처리
		chatMessage.setMessage(Utils.XSSHandling(chatMessage.getMessage()));
		chatMessage.setMessage(Utils.newLineHandling(chatMessage.getMessage()));
		
		return chatDao.insertMessage(chatMessage);
	}

	@Transactional(rollbackFor = Exception.class)
	public int exitChatRoom(ChatRoomJoin join) {
		// 업무로직
		// 1) chatRoomNo로 DELETE 실행 - (ChatRoomJoin)
		int result = chatDao.exitChatRoom(join);
		// 2) 현재 채팅방에 참여고하고 있는 인원정보 확인 SELECT
		if(result == 0) {
			return 0;
		}
		
		int cnt = chatDao.countChatRoomMember(join.getChatRoomNo());
		
		// 3) 내가 마지막 인원이라면 채팅방 정보를 DELETE - (ChatRoom)
		if(cnt == 0) {
			result = chatDao.closeChatRoom(join.getChatRoomNo());
		}
		return result;
	}

}
