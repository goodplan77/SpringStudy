package com.kh.study.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;
import com.kh.study.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final ServletContext application;

	@GetMapping("/list")
	public String list(Model model) {
		List<Board> boardList = boardService.selectBoardList();
		model.addAttribute("boardList", boardList);
		return "border/list";
	}

	@GetMapping("/insert")
	public String insertPage() {
		return "border/insert";
	}

	@PostMapping("/insert")
	public String insert(Board b, Model model, RedirectAttributes ra,
			@RequestParam(value = "images", required = false) List<MultipartFile> files) {

		String webPath = "/resources/upload/study/";
		String serverFolderPath = application.getRealPath(webPath);

		List<BoardImg> filelist = new ArrayList<>();

		boardService.insertBoard(b);

		for (MultipartFile s : files) {

			if (s != null && !s.isEmpty()) {
				BoardImg bi = null;

				String originName = s.getOriginalFilename();

				String currentTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				int random = (int) (Math.random() * 90000 + 10000); // 10000 - 99999 (5자리 랜덤값)
				String ext = originName.substring(originName.indexOf("."));
				String changeName = currentTime + random + ext;

				try {
					s.transferTo(new File(serverFolderPath + changeName)); // 파일 객체를 path 경로 상으로 이동 시킴
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				bi = new BoardImg();
				bi.setUploadPath(webPath);
				bi.setOriginName(originName);
				bi.setChangeName(changeName);
				bi.setRefNo(b.getNo());

				boardService.insertBoardImg(bi);

				filelist.add(bi);
			}

		}

		b.setImgList(filelist);

		model.addAttribute("board", b);

		return "border/detail";
	}

	@GetMapping("/detail/{boardCode}")
	public String detail(@PathVariable("boardCode") int boardNo, Model model) {

		Board b = boardService.selectBoard(boardNo);
		model.addAttribute("board", b);

		return "border/detail";
	}

}
