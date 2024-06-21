package com.kh.spring.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReplyDao {
	
	private final SqlSessionTemplate sqlSession;

	public List<Reply> selectReplyList(int boardNo) {
		return sqlSession.selectList("reply.selectReplyList" , boardNo);
	}

	public int insertReply(Reply r) {
		return sqlSession.insert("reply.insertReply" , r);
	}

	public int updateReply(Reply r) {
		return sqlSession.update("reply.updateReply" , r);
	}

}
