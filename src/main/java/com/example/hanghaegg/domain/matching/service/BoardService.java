package com.example.hanghaegg.domain.matching.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaegg.domain.matching.dto.BoardDto;
import com.example.hanghaegg.domain.matching.dto.BoardRequest;
import com.example.hanghaegg.domain.matching.entity.Board;
import com.example.hanghaegg.domain.matching.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional
	public void createBoard(BoardRequest boardRequest) {
		Board board = new Board(boardRequest);
		boardRepository.save(board);
	}

	@Transactional
	public void deleteBoard(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		boardRepository.delete(board);
	}

	public List<BoardDto> getAllBoards() {
		List<Board> boards = boardRepository.findAll();
		List<BoardDto> boardResponses = new ArrayList<>();

		for(Board board : boards){
			boardResponses.add(new BoardDto(board));
		}

		return boardResponses;
	}

	public BoardDto getBoard(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		return new BoardDto(board);
	}
}
