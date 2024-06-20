package com.kh.spring.chat.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.chat.model.vo.ChatMessage;
import com.kh.spring.chat.model.vo.ChatRoom;
import com.kh.spring.chat.model.vo.ChatRoomJoin;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatDao {
	
	private final SqlSessionTemplate sqlSession;

	public List<ChatRoom> selectChatRoomList() {
		return sqlSession.selectList("chat.selectChatRoomList");
	}

	public int openChatRoom(ChatRoom room) {
		return sqlSession.insert("chat.openChatRoom" , room);
	}

	public int joinChatRoom(ChatRoomJoin join) {
		return sqlSession.insert("chat.joinChatRoom" , join);
	}

	public List<ChatMessage> selectChatMessage(int chatRoomNo) {
		return sqlSession.selectList("chat.selectChatMessage" , chatRoomNo);
	}

	public int insertMessage(ChatMessage chatMessage) {
		return sqlSession.insert("chat.insertMessage" , chatMessage);
	}

}
