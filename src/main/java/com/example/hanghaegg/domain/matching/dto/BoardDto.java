package com.example.hanghaegg.domain.matching.dto;

import com.example.hanghaegg.domain.matching.entity.Board;

import lombok.Getter;

@Getter
public class BoardDto {

	private Long boardId;

	private String title;
	private String content;

	public BoardDto(Board board){
		this.boardId = board.getId();
		this.title = board.getTitle();
		this.content = board.getContent();
	}

}
