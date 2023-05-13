package com.example.hanghaegg.search.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.example.hanghaegg.search.dto.FinalResponsDto;
import com.example.hanghaegg.search.dto.SummernerRealDto;
import com.example.hanghaegg.search.dto.SummonerDTO;
import com.example.hanghaegg.search.dto.infoResponsDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.core.type.TypeReference;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.properties")
public class SummonerService {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Value("${riot.api.key}")
	private String mykey;

	public infoResponsDto callRiotAPISummonerByName(String summonerName) {
		SummonerDTO result = null;
		List<SummernerRealDto> result2=null;
		List<String> matchIds;


		String serverUrl = "https://kr.api.riotgames.com";
		String matchUrl="https://asia.api.riotgames.com";

		try {
			HttpClient client = HttpClient.newHttpClient();//새로운 HttpClient 객체를 생성(HTTP 요청을 보내고 응답을 받는데 사용)
			HttpRequest request = HttpRequest.newBuilder()//HttpRequest는 요청 메서드(GET, POST, PUT, DELETE 등), URI, 헤더, 본문 등의 정보를 가지고 있으며,HttpClient의 send() 또는 sendAsync() 메서드에 전달하여 HTTP 요청을 보낼 수 있음
				.uri(new URI(serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey))
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

		// return result;
////////////////////////////////////////////////////////////////////////////////////////////////소환사 id추출완료


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
///////////////////////////////////////////////////////////////////////////////////////////////소환사 기본적인 전적 추출 완료

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
//////////////////////////////////////////////매치 아이디 추출

		for (String matchId : matchIds) {
			try {
				HttpClient client4 = HttpClient.newHttpClient();
				HttpRequest request4 = HttpRequest.newBuilder()
					.uri(new URI(matchUrl + "/lol/match/v5/matches/" + matchId + "?api_key=" + mykey))
					.build();
				HttpResponse<String> response4 = client4.send(request4, HttpResponse.BodyHandlers.ofString());
				JsonNode matchDetail = objectMapper.readTree(response4.body());

				// 여기에서 'teams'는 각 팀의 정보를 포함하는 배열입니다.
				// 이 예제에서는 첫 번째 팀의 승/패 여부만 확인하지만, 두 팀 모두를 확인하려면 반복문을 사용하셔야 합니다.
				boolean didFirstTeamWin = matchDetail.get("info").get("teams").get(0).get("win").asBoolean();

				// TODO: 여기에서 승/패 정보를 저장하거나 처리합니다.
			} catch (IOException | InterruptedException | URISyntaxException e) {
				throw new IllegalArgumentException("오류가 발생했습니다", e);
			}
		}


//////////


		infoResponsDto infoResponsDto =new infoResponsDto(result3.getTier(),result3.getRank(),result3.getSummonerName(),result3.getWins(),result3.getLosses());

		 // FinalResponsDto.setSuccess("검색완료",new result3);


		return infoResponsDto;
	}
}




