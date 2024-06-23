package com.kh.study.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardDao {
	
	private final SqlSession sqlSession;

	public List<Board> selectBoardList() {
		return sqlSession.selectList("board.selectBoardList");
	}

	public int insertBoard(Board b) {
		return sqlSession.insert("board.insertBoard" , b);
	}

	public Board selectBoard(int boardNo) {
		return sqlSession.selectOne("board.selectBoard" , boardNo);
	}

	public int insertBoardImg(BoardImg bi) {
		return sqlSession.insert("board.insertBoardImg" , bi);
	}

}
