package com.example.hanghaegg.domain.matching.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.hanghaegg.domain.matching.dto.BoardDto;
import com.example.hanghaegg.domain.matching.dto.BoardRequest;
import com.example.hanghaegg.domain.matching.entity.Board;
import com.example.hanghaegg.domain.matching.entity.BoardFile;
import com.example.hanghaegg.domain.matching.repository.BoardFileRepository;
import com.example.hanghaegg.domain.matching.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardFileRepository boardFileRepository;

	@Transactional
	public void createBoard(BoardRequest boardRequest) throws IOException {
		if(boardRequest.getBoardFile().isEmpty()){
			Board board = new Board(boardRequest, 0);
			boardRepository.save(board);
		}else{
			// 1. DTO에 담긴 파일 꺼내기
			MultipartFile boardFile = boardRequest.getBoardFile();

			// 2. 파일의 이름 가져오기
			String originalFilename = boardFile.getOriginalFilename();

			// 3. 서버 저장용 이름 만들기
			String storedFilename = System.currentTimeMillis() + "_" + originalFilename;

			// 4. 저장 경로 설정하기
			String savePath = "C:/springboot_img/" + storedFilename;

			// 5. 해당 경로에 파일 저장하기
			boardFile.transferTo(new File(savePath));

			// 6. board_table에 해당 데이터 save 처리
			Board board = new Board(boardRequest);
			boardRepository.save(board);

			// 7. board_file_table에 해당 데이터 save 처리
			BoardFile boardFileEntity = new BoardFile(board, originalFilename, storedFilename);
			boardFileRepository.save(boardFileEntity);
		}

	}

	@Transactional
	public void deleteBoard(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		boardRepository.delete(board);
	}

	@Transactional
	public List<BoardDto> getAllBoards() {
		List<Board> boards = boardRepository.findAll();
		List<BoardDto> boardResponses = new ArrayList<>();

		for(Board board : boards){
			boardResponses.add(new BoardDto(board));
		}

		return boardResponses;
	}

	@Transactional
	public BoardDto getBoard(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		return new BoardDto(board);
	}
}
