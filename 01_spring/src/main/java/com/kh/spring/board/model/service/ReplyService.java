package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.ReplyDao;
import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyDao replyDao;
	
	public List<Reply> selectReplyList(int boardNo) {
		return replyDao.selectReplyList(boardNo);
	}

	public int insertReply(Reply r) {
		return replyDao.insertReply(r);
	}

	public int updateReply(Reply r) {
		return replyDao.updateReply(r);
	}

}
