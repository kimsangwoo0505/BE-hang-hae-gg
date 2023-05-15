package com.example.hanghaegg.domain.matching.dto;

import org.springframework.web.multipart.MultipartFile;

import com.example.hanghaegg.domain.matching.entity.Board;

import lombok.Getter;

@Getter
public class BoardDto {

	private String title;
	private String content;

	private String img;

}
