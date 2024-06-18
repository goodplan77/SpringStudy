package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {

	List<Board> selectList(PageInfo pi);

	int selectListCount();

	List<Board> searchList(PageInfo pi, Map<String, Object> paramMap);

	int searchListCount(Map<String, Object> paramMap);

}
