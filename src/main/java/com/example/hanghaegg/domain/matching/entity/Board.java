package com.example.hanghaegg.domain.matching.entity;

import com.example.hanghaegg.domain.matching.dto.BoardRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOARD_ID")
	private Long id;
	private String title;
	private String content;

	public Board(BoardRequest boardRequest){
		this.title = boardRequest.getTitle();
		this.content = boardRequest.getContent();
	}
}
