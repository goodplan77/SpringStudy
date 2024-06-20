package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.board.model.vo.BoardType;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardDao {

	List<BoardType> selectBoardTypeList();

	List<Board> selectList(PageInfo pi, Map<String, Object> paramMap);

	int selectListCount(Map<String, Object> paramMap);

	int insertBoard(Board b);

	int insertBoardImg(BoardImg bi);

	Board selectBoard(int boardNo);

	int increaseCount(int boardNo);

	int updateBoardImg(BoardImg bi);

	int updateBoard(Board board);

	int deleteBoardImg(String deleteList);

}
