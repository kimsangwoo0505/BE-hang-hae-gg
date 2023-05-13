package com.example.hanghaegg.search.dto;

import lombok.Data;

@Data
public class SummernerRealDto {
	private String leagueId;
	private	String queueType;
	private	String tier;
	private	String rank;
	private	String summonerId;
	private	String summonerName;
	private	int leaguePoints;
	private	int wins;
	private	int losses;
	private	boolean veteran;
	private	boolean inactive;
	private	boolean freshBlood;
	private	boolean hotStreak;

	// public class GetReal(){
	// 	private	String tier;
	// 	private	String rank;
	// 	private	String summonerName;
	// 	private	int wins;
	// 	private	int losses;
	// }


}
