package com.kh.spring.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
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
	public List<Board> selectList(PageInfo pi) {
		// MyBatis의 RowBounds 객체를 이용한 페이징 처리
		
		int offset = (pi.getCurrentPage() - 1) * pi.getPageLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset , limit);
		
		return sqlSession.selectList("board.selectList" , null , rowBounds);
	}
	
	@Override
	public List<BoardType> selectBoardTypeList() {
		return sqlSession.selectList("board.selectBoardTypeList");
	}
	
	@Override
	public int selectListCount() {
		return sqlSession.selectOne("board.selectListCount");
	}

	

}
