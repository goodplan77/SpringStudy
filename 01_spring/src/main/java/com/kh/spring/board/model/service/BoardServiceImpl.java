package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardDao boardDao;
	
	@Override
	public List<Board> selectList(PageInfo pi) {
		return boardDao.selectList(pi);
	}
	
	@Override
	public int selectListCount() {
		return boardDao.selectListCount();
	}

	@Override
	public List<Board> searchList(PageInfo pi, Map<String, Object> paramMap) {
		return boardDao.searchList(pi , paramMap);
	}

	@Override
	public int searchListCount(Map<String, Object> paramMap) {
		return boardDao.searchListCount(paramMap);
	}


}
