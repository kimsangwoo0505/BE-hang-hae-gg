package com.example.hanghaegg.domain.matching.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.hanghaegg.domain.matching.dto.BoardDto;
import com.example.hanghaegg.domain.matching.dto.BoardRequest;
import com.example.hanghaegg.domain.matching.dto.BoardResponse;
import com.example.hanghaegg.domain.matching.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@PostMapping("/matches")
	public ResponseEntity<BoardResponse> createBoard(
		@RequestPart(value = "data") final BoardRequest boardRequest,
		@RequestPart(value = "img") final MultipartFile file) {

		boardService.createBoard(boardRequest, file);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new BoardResponse("게시글을 작성하였습니다."));
	}

	// @GetMapping("/matches")
	// public ResponseEntity<List<BoardDto>> getBoardList() {
	//
	// 	return ResponseEntity.status(HttpStatus.OK)
	// 		.body(boardService.searchBoards());
	// }
	//
	// @GetMapping("/matches/{board-id}")
	// public ResponseEntity<BoardResponse> getBoard(@PathVariable(name = "board-id") final Long boardId) {
	//
	// 	return ResponseEntity.status(HttpStatus.OK)
	// 		.body(boardService.searchBoard(boardId));
	// }

	@DeleteMapping("/matches/{board-id}")
	public ResponseEntity<Void> deleteArticle(
		@PathVariable(name = "board-id") final Long boardId) {

		boardService.deleteBoard(boardId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
