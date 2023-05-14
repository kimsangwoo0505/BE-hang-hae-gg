package com.example.hanghaegg.domain.matching.dto;

import org.springframework.web.multipart.MultipartFile;

import com.example.hanghaegg.domain.matching.entity.Board;

import lombok.Getter;

@Getter
public class BoardDto {

	private Long boardId;
	private String title;
	private String content;
	private MultipartFile boardFile; //FE -> BE (controller) 로 파일 담는 용도
	private String originalFileName; //원본 파일 이름
	private String storedFileName; //서버 저장용 파일 이름
	private int fileAttached; //파일 첨부 여부 (첨부 1, 미첨부 0)


	public BoardDto(Board board){
		this.boardId = board.getId();
		this.title = board.getTitle();
		this.content = board.getContent();
		if(board.getFileAttached() == 0){
			this.fileAttached = 0;
		}else{
			this.fileAttached = 1;
			this.originalFileName = board.getBoardFiles().get(0).getOriginalFilename();
			this.storedFileName = board.getBoardFiles().get(0).getStoredFilename();
		}
	}

}
