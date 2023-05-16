package com.example.hanghaegg.search.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.example.hanghaegg.exception.CommonErrorCode;
import com.example.hanghaegg.exception.RestApiException;
import com.example.hanghaegg.exception.SearchErrorCode;
import com.example.hanghaegg.search.dto.FinalResponsDto;
import com.example.hanghaegg.search.dto.MatchResponseDto;
import com.example.hanghaegg.search.dto.SummernerRealDto;
import com.example.hanghaegg.search.dto.SummonerDTO;
import com.example.hanghaegg.search.dto.InfoResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class SummonerService {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Value("${riot.api.key}")
	private String mykey;

	public FinalResponsDto callRiotAPISummonerByName(int page, int size,String summonerName) {
		SummonerDTO result;
		List<SummernerRealDto> result2;
		List<String> matchIds;
		List<MatchResponseDto> matchresult = new ArrayList<>();
		boolean didSummonerWin = false;


		String serverUrl = "https://kr.api.riotgames.com";
		String matchUrl="https://asia.api.riotgames.com";
		String encodedSummonerName;

		try {
			encodedSummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8.toString());//띄어쓰기 코딩(HTTP 요청을 보낼때 소환사 이름을 URL에 안전하게 포함시키는 용도)

			HttpClient client = HttpClient.newHttpClient();//새로운 HttpClient 객체를 생성(HTTP 요청을 보내고 응답을 받는데 사용)
			HttpRequest request = HttpRequest.newBuilder()//HttpRequest는 요청 메서드(GET, POST, PUT, DELETE 등), URI, 헤더, 본문 등의 정보를 가지고 있으며,HttpClient의 send() 또는 sendAsync() 메서드에 전달하여 HTTP 요청을 보낼 수 있음
				.uri(new URI(serverUrl + "/lol/summoner/v4/summoners/by-name/" + encodedSummonerName + "?api_key=" + mykey))
				.build();//HttpRequest 객체를 생성하는 코드


			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			//request를 보내고 HttpResponse.BodyHandlers.ofString()로 BodyHandler를 생성(.ofString()으로 응답 본문(body)을 문자열(String)로 변환하여 처리)

			if(response.statusCode() != 200) {
				return null;
			}

			result = objectMapper.readValue(response.body(), SummonerDTO.class);//objectMapper로 response.body()을 SummonerDTO.class로 변경


		} catch (IOException |InterruptedException|URISyntaxException e) {
			throw new RestApiException(SearchErrorCode.RIOT_ERROR);
		}

		try {
			HttpClient client2 = HttpClient.newHttpClient();
			HttpRequest request2 = HttpRequest.newBuilder()
				.uri(new URI(serverUrl + "/lol/league/v4/entries/by-summoner/" + result.getId() + "?api_key=" + mykey))
				.build();

			HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

			result2 = objectMapper.readValue(response2.body(), new TypeReference<List<SummernerRealDto>>(){});


		}catch (IOException |InterruptedException|URISyntaxException e) {
			throw new RestApiException(SearchErrorCode.RIOT_ERROR);
		}
		SummernerRealDto result3= result2.get(0);



		try {
			HttpClient client3 = HttpClient.newHttpClient();
			HttpRequest request3 = HttpRequest.newBuilder()
				.uri(new URI(matchUrl + "/lol/match/v5/matches/by-puuid/" + result.getPuuid() +"/ids?type=ranked&start=0&count=40&api_key=" + mykey))
				.build();
			HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());

			matchIds = objectMapper.readValue(response3.body(), new TypeReference<List<String>>(){});
			//////////////매치아이디 추출완료

		}catch (IOException |InterruptedException|URISyntaxException e) {
			throw new RestApiException(SearchErrorCode.RIOT_ERROR);
		}


		int start = Math.max(0, (page - 1) * size);
		int end = Math.min(matchIds.size(), start + size);
		List<String> paginatedMatchIds = matchIds.subList(start, end);


		for (String matchId : paginatedMatchIds) {
			try {
				// String encodedSummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8.toString());//띄어쓰기 코딩
				HttpClient client4 = HttpClient.newHttpClient();
				HttpRequest request4 = HttpRequest.newBuilder()
					.uri(new URI(matchUrl + "/lol/match/v5/matches/" + matchId + "?api_key=" + mykey))
					.build();
				HttpResponse<String> response4 = client4.send(request4, HttpResponse.BodyHandlers.ofString());
				JsonNode matchDetail = objectMapper.readTree(response4.body());



				JsonNode participants = matchDetail.get("info").get("participants");

				int teamId = 0;//검색한 소환사의 팀 ID를 저장할 변수를 선언
				String championName=null;//챔피언이름
				int kills=0;//킬
				int deaths=0;//데스
				int assists=0;//어시스트

				// 검색한 소환사를 찾았다면 그의 팀 ID를 저장
				for (JsonNode participant : participants) {
					String participantName = participant.get("summonerName").asText().replaceAll("\\s+", "").toLowerCase();//URL부분이 아니라 이미수신한 JSON응답에서 특정 소환사를 찾는 작업을 수행하는 것이므로 다르게 인코딩 해야함
					//asText()로 String으로 바꾸고->.replaceAll("\\s+", "")을 통해서 믄자열 내의 모든 공백(띄어쓰기, 탭, 줄바꿈 등)을 제거->toLowerCase()모두 소문자로 변경
					//("\\s+"는 공백문자가 하나이상 연속으로 나타나는패턴을 말하며,.replaceAll("\\s+", "")은 공백문자가 하나이상 연속으로 나타나면 ""인 빈문자열로 대체하게함
					String rawName = summonerName.replaceAll("\\s+", "").toLowerCase();
					if (participantName.equals(rawName)) {
						teamId = participant.get("teamId").asInt();
						championName = participant.get("championName").asText();
						kills=participant.get("kills").asInt();
						deaths=participant.get("deaths").asInt();
						assists=participant.get("assists").asInt();
						break;
					}
				}


				// 검색한 소환사의 팀이 이겼는지를 확인
				for (JsonNode team : matchDetail.get("info").get("teams")) {
					if (team.get("teamId").asInt() == teamId) {
						didSummonerWin = team.get("win").asBoolean();
						break;
					}
				}


				String champonImg="https://ddragon.leagueoflegends.com/cdn/13.9.1/img/champion/"+championName+".png";

				MatchResponseDto matchResponseDto0=new MatchResponseDto("랭크게임",didSummonerWin,championName,champonImg,kills,deaths,assists);
				matchresult.add(matchResponseDto0);
				// i++;


			} catch (IOException |InterruptedException|URISyntaxException e) {
				throw new RestApiException(SearchErrorCode.RIOT_ERROR);
			}
		}




		String summonerIcon="https://ddragon.leagueoflegends.com/cdn/13.9.1/img/profileicon/" + result.getProfileIconId() + ".png";


		InfoResponseDto infoResponseDto =new InfoResponseDto(result3.getTier(),result3.getRank(),result3.getSummonerName(),result3.getLeaguePoints(),summonerIcon,result3.getWins(),result3.getLosses(),matchresult);




		return FinalResponsDto.setSuccess("검색완료",infoResponseDto);
	}
}



