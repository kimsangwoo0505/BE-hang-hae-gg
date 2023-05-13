package com.example.hanghaegg.search.dto;

import lombok.Data;

@Data// getter, setter, equals(), hashCode(), toString() 메서드를 자동으로 생성
public class SummonerDTO {
	private String accountId;//private로 메서드가 속한 클래스 외부에서 직접접근 x
	private int profileIconId;
	private long revisionDate;
	private String name;
	private String id;
	private String puuid;
	private long summonerLevel;




}
