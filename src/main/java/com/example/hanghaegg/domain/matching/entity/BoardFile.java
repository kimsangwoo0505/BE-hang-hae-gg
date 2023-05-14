package com.example.hanghaegg.domain.matching.entity;

import com.example.hanghaegg.domain.matching.dto.BoardRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class BoardFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String originalFilename;

	@Column
	private String storedFilename;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	public BoardFile(Board board, String originalFilename, String storedFilename){
		this.board = board;
		this.originalFilename = originalFilename;
		this.storedFilename = storedFilename;
	}
}
