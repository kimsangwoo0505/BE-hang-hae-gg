package com.example.hanghaegg.search.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

	public FinalResponsDto callRiotAPISummonerByName(String summonerName) {
		SummonerDTO result;
		List<SummernerRealDto> result2;
		List<String> matchIds;
		List<MatchResponseDto> matchresult = new ArrayList<>();
		boolean didSummonerWin = false;

		String serverUrl = "https://kr.api.riotgames.com";
		String matchUrl="https://asia.api.riotgames.com";
		String encodedSummonerName;
		////////////////////////////////////////////////////////////////////////소환사 id추출 완료
		try {
			encodedSummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8.toString());//띄어쓰기 코딩(HTTP 요청을 보낼때 소환사 이름을 URL에 안전하게 포함시키는 용도)
			// String encodedSummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8.toString());//띄어쓰기 코딩
			HttpClient client = HttpClient.newHttpClient();//새로운 HttpClient 객체를 생성(HTTP 요청을 보내고 응답을 받는데 사용)
			HttpRequest request = HttpRequest.newBuilder()//HttpRequest는 요청 메서드(GET, POST, PUT, DELETE 등), URI, 헤더, 본문 등의 정보를 가지고 있으며,HttpClient의 send() 또는 sendAsync() 메서드에 전달하여 HTTP 요청을 보낼 수 있음
				.uri(new URI(serverUrl + "/lol/summoner/v4/summoners/by-name/" + encodedSummonerName + "?api_key=" + mykey))
				.build();//HttpRequest 객체를 생성하는 코드
			//HttpRequest는 HTTP 요청을 나타내는 클래스로, 요청을 보낼 URI, HTTP 메소드 등을 설정할 수 있음(newBuilder() 메소드는 HttpRequest의 Builder 객체를 반환
			//(newBuilder() 메소드는 HttpRequest의 Builder 객체를 반환,uri 메소드는 요청을 보낼 URI를 설정,build 메소드는 설정한 정보를 바탕으로 HttpRequest 객체를 생성)
			//+Builder 객체는 일반적으로 객체 생성에 필요한 로직을 캡슐화하여, 객체 생성을 단순화하고 코드의 가독성을 높이며 메소드를 통해 값을 설정하고, 마지막에 build() 메소드를 호출하여
			//최종적인 객체를 생성

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			//request를 보내고 HttpResponse.BodyHandlers.ofString()로 BodyHandler를 생성(.ofString()으로 응답 본문(body)을 문자열(String)로 변환하여 처리)

			if(response.statusCode() != 200) {
				return null;
			}

			result = objectMapper.readValue(response.body(), SummonerDTO.class);//objectMapper로 response.body()을 SummonerDTO.class로 변경


		} catch (IOException | InterruptedException | URISyntaxException e){
			throw new IllegalArgumentException("오류가 발생했습니다",e);
			// e.printStackTrace();
			// return null;
		}
		//IOException:파일 읽기/쓰기, 네트워크 통신 등 컴퓨터의 입출력 작업오류
		//InterruptedException:쓰레드가 실행 중에 다른 쓰레드에 의해 중단되었을 때 발생하는 예외
		//URISyntaxException: 잘못된 URI 구문을 사용했을 때 발생하는 예외

		////////////////////////////////////////////////////////////////////////////////////////////////소환사 기본적인 전적 추출 완료


		try {
			HttpClient client2 = HttpClient.newHttpClient();
			HttpRequest request2 = HttpRequest.newBuilder()
				.uri(new URI(serverUrl + "/lol/league/v4/entries/by-summoner/" + result.getId() + "?api_key=" + mykey))
				.build();

			HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

			result2 = objectMapper.readValue(response2.body(), new TypeReference<List<SummernerRealDto>>(){});


		}catch (IOException | InterruptedException | URISyntaxException e){
			throw new IllegalArgumentException("오류가 발생했습니다",e);
			// e.printStackTrace();
			// return null;
		}
		SummernerRealDto result3= result2.get(0);

		///////////////////////////////////////////////////////////////////////////////////////////////매치 아이디 추출

		try {
			HttpClient client3 = HttpClient.newHttpClient();
			HttpRequest request3 = HttpRequest.newBuilder()
				.uri(new URI(matchUrl + "/lol/match/v5/matches/by-puuid/" + result.getPuuid() +"/ids?type=ranked&start=0&count=10&api_key=" + mykey))
				.build();
			HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());

			matchIds = objectMapper.readValue(response3.body(), new TypeReference<List<String>>(){});
			//////////////매치아이디 추출완료

		}catch (IOException | InterruptedException | URISyntaxException e){
			throw new IllegalArgumentException("오류가 발생했습니다",e);
			// e.printStackTrace();
			// return null;
		}
		//////////////////////////////////////////////매치 아이디로 최근 매치 조회

		for (String matchId : matchIds) {
			try {
				// String encodedSummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8.toString());//띄어쓰기 코딩
				HttpClient client4 = HttpClient.newHttpClient();
				HttpRequest request4 = HttpRequest.newBuilder()
					.uri(new URI(matchUrl + "/lol/match/v5/matches/" + matchId + "?api_key=" + mykey))
					.build();
				HttpResponse<String> response4 = client4.send(request4, HttpResponse.BodyHandlers.ofString());
				JsonNode matchDetail = objectMapper.readTree(response4.body());


				// 경기의 참가자 목록을 조회// JSON 배열에서는 인덱스를 통해 접근할 수 있지만, 특정 key나 이름으로 직접 접근하는 것은 불가능해서 for문돌려야함
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


				MatchResponseDto matchResponseDto0=new MatchResponseDto(didSummonerWin,championName,kills,deaths,assists);
				matchresult.add(matchResponseDto0);
				// i++;


			} catch (IOException | InterruptedException | URISyntaxException e) {
				throw new IllegalArgumentException("오류가 발생했습니다", e);
			}
		}


		///////////////////////////


		InfoResponseDto infoResponseDto =new InfoResponseDto(result3.getTier(),result3.getRank(),result3.getSummonerName(),result3.getLeaguePoints(),result3.getWins(),result3.getLosses(),matchresult);

		// FinalResponsDto.setSuccess("검색완료",new result3);


		return FinalResponsDto.setSuccess("검색완료",infoResponseDto);
	}
}



