package com.example.hanghaegg.domain.matching.dto;

import com.example.hanghaegg.domain.matching.entity.Board;

import lombok.Getter;

@Getter
public class BoardDto {

	private long boardId;

	private String title;

	private String content;

	private String img;

	private Long memberId;

	public BoardDto(Board board){
		this.boardId = board.getId();
		this.title = board.getTitle();
		this.content = board.getContent();
		this.img = board.getImg();
		this.memberId = board.getMember().getId();
	}
}
