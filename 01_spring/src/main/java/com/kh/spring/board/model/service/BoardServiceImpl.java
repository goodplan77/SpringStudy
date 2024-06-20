package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.common.Utils;
import com.kh.spring.common.model.vo.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardDao boardDao;
	
	private final ServletContext application;
	
	@Override
	public List<Board> selectList(PageInfo pi, Map<String, Object> param) {
		return boardDao.selectList(pi , param);
	}
	
	@Override
	public int selectListCount(Map<String, Object> param) {
		return boardDao.selectListCount(param);
	}

	@Override
	@Transactional (rollbackFor = {Exception.class}) // 어떤 예외든 발생시 rollback 처리
	public int insertBoard(Board b, BoardImg bi) throws Exception {
		// 0) 게시글 삽입전 , xss 파싱처리 , 개행 처리 수행
		String boardTitle = b.getBoardTitle();
		String boardContent = b.getBoardContent();
		boardTitle = Utils.XSSHandling(boardTitle);
		boardContent = Utils.XSSHandling(boardContent);
		boardContent = Utils.newLineHandling(boardContent);
		
		b.setBoardTitle(boardTitle);
		b.setBoardContent(boardContent);
		
		
		// 1) INSRT BOARD 후
		int result = boardDao.insertBoard(b); //수행완료시 b에는 boardNo저장 -> selectKey 확인
		
		int boardNo = b.getBoardNo();
		if(bi != null) {
			bi.setRefBno(boardNo);
			result *= boardDao.insertBoardImg(bi);
		}
		
		if(result == 0) {
			throw new Exception("예외 발생");
		}
		
		// 2) INSERT BOARD_IMG -> bi != null 등록
		
		// 1-2)의 수행결과는 항상 동일하게 처리해야함
		// 처리 결과 값 반환
		
		return result;
	}

	@Override
	public Board selectBoard(int boardNo) {
		return boardDao.selectBoard(boardNo);
	}

	@Override
	public int increaseCount(int boardNo) {
		return boardDao.increaseCount(boardNo);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int updateBoard(Board board, MultipartFile upfile, int boardImgNo , String deleteList) {
		// IMG가 있으면 이미지 테이블 수정
		// 사진이 없던 곳에서 새롭게 추가된 경우	-> INSERT
		// 사진이 있던 곳에서 새롭게 추가된 경우	-> UPDATE
		// 사진이 있던 곳에서 삭제가 된 경우		-> DELETE
		// 원래 사진이 없었고 , 추가된 것도 없는 경우 -> 아무것도 안함.
		
		// 0) 데이터 파싱 (insert 시 와 동일)
		
		// 1) Board테이블 업데이트
		int result = boardDao.updateBoard(board);
		
		// 2) BOARD_IMG테이블에 Insert , Update , Delete
		
		if(result == 0) {
			throw new RuntimeException("수정 실패");
		}
		
		BoardImg bi = new BoardImg();
		String webPath = "/resources/images/board/N/";
		String serverFolderPath = application.getRealPath(webPath);
		
		log.debug("board {} " , board);
		log.debug("upfile {} " , upfile);
		log.debug("boardImgNo {} " , boardImgNo);
		log.debug("deleteList {} " , deleteList);
		
		// 사진이 없던 곳에서 새롭게 추가된 경우	-> INSERT
		if(boardImgNo == 0 && upfile != null && !upfile.isEmpty()) {
			bi.setRefBno(board.getBoardNo());
			bi.setImgLevel(0);
					
			String changeName = Utils.saveFile(upfile , serverFolderPath);
			bi.setChangeName(changeName);
			bi.setOriginName(upfile.getOriginalFilename());
			
			result *= boardDao.insertBoardImg(bi);
		} 
		// 사진이 있던 곳에서 새롭게 추가된 경우	-> UPDATE
		else if (boardImgNo != 0 && upfile != null && !upfile.isEmpty()) {
			bi.setBoardImgNo(boardImgNo);
			
			String changeName = Utils.saveFile(upfile , serverFolderPath);
			bi.setChangeName(changeName);
			bi.setOriginName(upfile.getOriginalFilename());
			
			result *= boardDao.updateBoardImg(bi);
		} 
		// 사진이 있던 곳에서 삭제가 된 경우		-> DELETE
		// 새롭게 등록된 파일이 없고 upfile.isEmpty() + 삭제할 목록이 존재하는 경우 !(deleteList.equals(""))
		else if (boardImgNo != 0 && upfile.isEmpty() && !(deleteList.equals(""))) {
			result *=boardDao.deleteBoardImg(deleteList);
			// 웹서버의 파일 시스템안에 있는 첨부파일도 삭제 해줘야함.
		}
		
		return result;
	}

}
