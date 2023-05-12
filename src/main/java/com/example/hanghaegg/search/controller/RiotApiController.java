// package com.example.hanghaegg.search.controller;
//
// import org.springframework.web.bind.annotation.*;
//
// import com.example.hanghaegg.search.dto.RiotResponseDto;
// import com.example.hanghaegg.search.service.GetSummonerInfo;
//
// @RestController
// @RequestMapping("/api/summoners")
// public class SummonerController {
// 	private final GetSummonerInfo getSummonerInfo;
// 	//
// 	public SummonerController(GetSummonerInfo getSummonerInfo) {
// 		this.getSummonerInfo = getSummonerInfo;
// 	}
//
// 	@GetMapping("/{summonerName}")
// 	public RiotResponseDto getSummonerInfo(@PathVariable String summonerName) {
// 		return getSummonerInfo.getSummonerInfo(summonerName);
// 	}
// }