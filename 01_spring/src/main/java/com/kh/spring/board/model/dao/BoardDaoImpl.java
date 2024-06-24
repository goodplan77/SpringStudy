package com.kh.spring.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.board.model.vo.BoardType;
import com.kh.spring.common.model.vo.PageInfo;

import lombok.RequiredArgsConstructor;

@Repository // 저장소 역할
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao{
	
	// @Autowired
	private final SqlSession sqlSession;
	// final? 객체 불변성 유지를 위해 사용
	
//	@RequiredArgsConstructor로 아래 코드를 대체
//	public BoardDaoImpl(SqlSession sqlSession) {
//		this.sqlSession = sqlSession;
//	}
	
//	@Override
//	public List<Board> selectList(PageInfo pi) {
//		// rowNum 방식 페이징 처리
//		// startRow : (currentPage - 1) * pageLimit + 1
//		// endRow : startRow + pageLimit - 1
//		int startRow = (pi.getCurrentPage() - 1) * pi.getPageLimit() + 1;
//		int endRow = startRow + pi.getPageLimit() - 1;
//		
//		Map<String , Object> param = new HashMap<>();
//		param.put("startRow", startRow);
//		param.put("endRow", endRow);
//		
//		return sqlSession.selectList("board.selectList" , param);
//	}
	
	@Override
	public List<Board> selectList(PageInfo pi , Map<String, Object> param) {
		// MyBatis의 RowBounds 객체를 이용한 페이징 처리
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset , limit);
		
		return sqlSession.selectList("board.selectList" , param , rowBounds);
	}
	
	@Override
	public List<BoardType> selectBoardTypeList() {
		return sqlSession.selectList("board.selectBoardTypeList");
	}
	
	@Override
	public int selectListCount(Map<String, Object> param) {
		return sqlSession.selectOne("board.selectListCount" , param);
	}

	@Override
	public int insertBoard(Board b) {
		return sqlSession.insert("board.insertBoard" , b);
	}

	@Override
	public int insertBoardImg(BoardImg bi) {
		return sqlSession.insert("board.insertBoardImg" , bi);
	}

	@Override
	public Board selectBoard(int boardNo) {
		return sqlSession.selectOne("board.selectBoard" , boardNo);
	}

	@Override
	public int increaseCount(int boardNo) {
		return sqlSession.update("board.increaseCount" , boardNo);
	}

	@Override
	public int updateBoardImg(BoardImg bi) {
		return sqlSession.update("board.updateBoardImg" , bi);
	}

	@Override
	public int updateBoard(Board board) {
		return sqlSession.update("board.updateBoard" , board);
	}

	@Override
	public int deleteBoardImg(String deleteList) {
		return sqlSession.delete("board.deleteBoardImg" , deleteList);
	}

	@Override
	public List<String> selectFileList() {
		return sqlSession.selectList("board.selectFileList");
	}

	

	

}
