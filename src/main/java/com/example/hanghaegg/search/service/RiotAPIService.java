// package com.example.hanghaegg.search.service;
//
// import java.util.HashMap;
// import java.util.Map;
//
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.util.UriComponents;
// import org.springframework.web.util.UriComponentsBuilder;
// import org.springframework.web.util.UriUtils;
//
// import com.example.hanghaegg.search.dto.RiotResponseDto;
// import com.example.hanghaegg.search.dto.MatchList;
//
// import lombok.Value;
//
// @Service
// public class RiotAPIService {
//
// 	RestTemplate restTemplate = new RestTemplate();
//
// 	String AccessKey = "RGAPI-d0bd6554-3ef0-4be7-bca9-5061668589fb";
//
// 	String summonerurl = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name";
//
// 	public RiotResponseDto findSummoner(String summonerName) {
// 		RiotResponseDto riotResponseDto = new RiotResponseDto();
//
// 		try {
// 			UriComponents builder = UriComponentsBuilder.fromHttpUrl(summonerurl)
// 				.path(UriUtils.encode(summonerName, "UTF-8"))
// 				.queryParam("api_key", AccessKey)
// 				.build();
//
// 			RiotResponseDto summonerVO = restTemplate.getForObject(builder.toUri(), RiotResponseDto.class);
//
// 			riotResponseDto.setData(summonerVO);
//
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 		riotResponseDto.setResponseCode(200);
// 		riotResponseDto.setResponseMsg("Success");
//
// 		return riotResponseDto;
// 	}
// }
//
//
//
//
//
//
//
//
// // @Service
// // public class RiotAPIService {
// //
// // 	// @Autowired
// // 	// Gson gson;
// //
// // 	RestTemplate restTemplate = new RestTemplate();
// //
// // 	String AccessKey = "RGAPI-b425887d-be93-4264-8197-b3f764b07206";
// //
// // 	String summonerurl = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name";
// //
// // 	// String matchurl=;
// //
// // 	public RiotResponseDto findSummoner(String summonerName) {
// // 		RiotResponseDto riotResponseDto = new RiotResponseDto();
// //
// // 		Map<String, String> param = new HashMap<>();
// // 		param.put("summonerName", summonerName);
// // 		try {
// //
// // 			UriComponents builder = UriComponentsBuilder.fromHttpUrl(summonerurl)
// // 				.path("/{summonerName}")
// // 				.queryParam("api_key", AccessKey)
// // 				.encode() //UTF-8 encoding해줌 toUri()로 URI타입을 넘기는 경우 사용
// // 				.buildAndExpand(param);
// //
// // 			RiotResponseDto summonerVO = restTemplate.getForObject(builder.toUri(), RiotResponseDto.class);
// //
// // 			riotResponseDto.setData(summonerVO);
// //
// // 		} catch (Exception e) {
// // 			e.printStackTrace();
// // 		}
// // 		riotResponseDto.setResponseCode(200);
// // 		riotResponseDto.setResponseMsg("Success");
// //
// // 		return riotResponseDto;
// // 	}
// // }
//
//
// 	// RiotResponseDto riotResponseDto = new RiotResponseDto();
// 	//
// 	// Map<String, String> param = new HashMap<>();
// 	// 	param.put("summonerName", summonerName);
// 	// 		try {
// 	//
// 	// 		UriComponents builder = UriComponentsBuilder.fromHttpUrl(summonerurl)
// 	// 		.path("/{summonerName}")
// 	// 		.queryParam("api_key", AccessKey)
// 	// 		.encode() //UTF-8 encoding해줌 toUri()로 URI타입을 넘기는 경우 사용
// 	// 		.buildAndExpand(param);
// 	//
// 	// 		RiotResponseDto summonerVO = restTemplate.getForObject(builder.toUri(), RiotResponseDto.class);
// 	//
// 	// riotResponseDto.setData(summonerVO);
// 	//
// 	// } catch (Exception e) {
// 	// e.printStackTrace();
// 	// }
// 	// riotResponseDto.setResponseCode(200);
// 	// riotResponseDto.setResponseMsg("Success");
// 	//
// 	// return riotResponseDto;
//
//
//
// 	// public RiotResponseDto showRecentMatches(String summonerName){
// 	// 	RiotResponseDto riotResponseDto = new RiotResponseDto();
// 	// 	String accountId = String.valueOf(findSummoner(summonerName).getData()).split(",|=")[3];
// 	//
// 	// 	Map<String,String> param = new HashMap<>();
// 	// 	param.put("accountId",accountId);
// 	// 	MatchList mathchList = new MatchList();
// 	// 	try {
// 	//
// 	// 		UriComponents builder = UriComponentsBuilder.fromHttpUrl(matchurl)
// 	// 			.path("/{accountId}")
// 	// 			.queryParam("api_key",AccessKey)
// 	// 			.encode() //UTF-8 encoding해줌 toUri()로 URI타입을 넘기는 경우 사용
// 	// 			.buildAndExpand(param);
// 	//
// 	// 		System.out.println(builder.toUri());
// 	// 		mathchList = restTemplate.getForObject(builder.toUri(), MatchList.class);
// 	// 		riotResponseDto.setData(mathchList);
// 	//
// 	// 	} catch (Exception e) {
// 	// 		e.printStackTrace();
// 	// 	}
// 	// 	riotResponseDto.setResponseCode(200);
// 	// 	riotResponseDto.setResponseMsg("Success");
// 	//
// 	// 	return riotResponseDto;
// 	// }
// // }
