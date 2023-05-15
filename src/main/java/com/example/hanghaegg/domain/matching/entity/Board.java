package com.example.hanghaegg.domain.matching.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.hanghaegg.domain.matching.dto.BoardRequest;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private int fileAttached; //1 or 0

	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<BoardFile> boardFiles = new ArrayList<>();

	public Board(BoardRequest boardRequest){
		this.title = boardRequest.getTitle();
		this.content = boardRequest.getContent();
		this.fileAttached = 0;
	}

	public Board(BoardRequest boardRequest, int fileAttached){
		this.title = boardRequest.getTitle();
		this.content = boardRequest.getContent();
		this.fileAttached = fileAttached;
	}

	// public void setFileAttached(int fileAttached) {
	// 	this.fileAttached = fileAttached;
	// }
}
