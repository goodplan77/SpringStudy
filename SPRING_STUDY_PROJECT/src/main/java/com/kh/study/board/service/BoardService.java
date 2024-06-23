package com.kh.study.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.study.board.model.dao.BoardDao;
import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardDao boardDao;
	
	public List<Board> selectBoardList() {
		return boardDao.selectBoardList();
	}

	public int insertBoard(Board b) {
		return boardDao.insertBoard(b);
	}

	public Board selectBoard(int boardNo) {
		return boardDao.selectBoard(boardNo);
	}

	public int insertBoardImg(BoardImg bi) {
		return boardDao.insertBoardImg(bi);
	}

}
