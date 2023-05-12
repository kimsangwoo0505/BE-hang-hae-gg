// package com.example.hanghaegg.search.service;
//
// import java.io.UnsupportedEncodingException;
// import java.net.URLEncoder;
//
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.*;
// import org.springframework.web.client.*;
//
// import com.example.hanghaegg.search.dto.RiotResponseDto;
//
// public class GetSummonerInfo  {
// 	public RiotResponseDto getSummonerInfo(String summonerName) {
// 		RestTemplate restTemplate = new RestTemplate();
// 		String apiKey = System.getenv("RIOT_API_KEY"); // 환경 변수에서 API 키를 가져옵니다.
// 		String encodedSummonerName;
//
// 		try {
// 			encodedSummonerName = URLEncoder.encode(summonerName, "UTF-8");
// 		} catch (UnsupportedEncodingException e) {
// 			e.printStackTrace();
// 			throw new RuntimeException("Unsupported encoding: UTF-8");
// 		}
//
// 		String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + encodedSummonerName + "?api_key="
// 			+ apiKey;
//
// 		try {
// 			ResponseEntity<RiotResponseDto> response = restTemplate.exchange(
// 				url,
// 				HttpMethod.GET,
// 				null,
// 				new ParameterizedTypeReference<RiotResponseDto>() {
// 				}
// 			);
//
// 			if (response.getStatusCode() == HttpStatus.OK) {
// 				return response.getBody();
// 			} else {
// 				throw new RuntimeException("Failed to get summoner info: " + response.getStatusCode());
// 			}
// 		} catch (HttpClientErrorException | HttpServerErrorException e) {
// 			throw new RuntimeException("Failed to get summoner info: " + e.getStatusCode());
// 		}
// 	}
// }
