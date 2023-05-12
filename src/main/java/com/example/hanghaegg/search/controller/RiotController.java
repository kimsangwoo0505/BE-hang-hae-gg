// package com.example.hanghaegg.search.controller;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.example.hanghaegg.search.dto.RiotResponseDto;
// import com.example.hanghaegg.search.service.RiotAPIService;
//
//
// @RestController
// @RequestMapping("/riot")
// public class RiotController {
//
// 	@Autowired
// 	RiotAPIService riotAPIService;
//
// 	@RequestMapping(method = RequestMethod.GET, value="/summoner")
// 	public RiotResponseDto findSummonerName(@RequestParam(value="summonerName") String summonerName){
//
// 		RiotResponseDto riotResponseDto = riotAPIService.findSummoner(summonerName);
// 		return riotResponseDto;
// 	}
//
// 	// @RequestMapping(method = RequestMethod.GET, value="/matches")
// 	// public RiotResponseDto findRecentMatches(@RequestParam(value="summonerName") String summonerName){
// 	//
// 	// 	RiotResponseDto riotResponseDto = riotAPIService.showRecentMatches(summonerName);
// 	// 	return riotResponseDto;
// 	// }
//
// }
