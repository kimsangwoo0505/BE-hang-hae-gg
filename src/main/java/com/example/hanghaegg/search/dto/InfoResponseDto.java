package com.example.hanghaegg.search.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InfoResponseDto {

	private	String tier;
	private	String rank;
	private	String summonerName;
	private	int leaguePoints;
	private	String summonerIconUrl;
	private	int wins;
	private	int losses;
	private List<MatchResponseDto> matchResults;

}
