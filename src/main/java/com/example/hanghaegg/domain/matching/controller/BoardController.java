package com.example.hanghaegg.domain.matching.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.hanghaegg.domain.matching.dto.BoardDto;
import com.example.hanghaegg.domain.matching.dto.BoardRequest;
import com.example.hanghaegg.domain.matching.dto.BoardResponse;
import com.example.hanghaegg.domain.matching.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("matching")
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardRequest boardRequest){

		boardService.createBoard(boardRequest);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new BoardResponse("게시글을 작성하였습니다."));
	}

	@DeleteMapping("/{board-id}")
	public ResponseEntity<BoardResponse> deleteBoard(@PathVariable(name = "board-id")Long boardId){

		boardService.deleteBoard(boardId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new BoardResponse("게시글을 삭제하였습니다."));
	}

	@GetMapping
	public ResponseEntity<List<BoardDto>> getAllBoards(){
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(boardService.getAllBoards());
	}

	@GetMapping("/{board-id}")
	public ResponseEntity<BoardDto> getBoard(@PathVariable(name = "board-id") Long boardId){
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(boardService.getBoard(boardId));
	}
}
