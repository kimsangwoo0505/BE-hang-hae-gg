package com.example.hanghaegg.search.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.example.hanghaegg.search.dto.SummonerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.properties")
public class SummonerService {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Value("${riot.api.key}")
	private String mykey;

	public SummonerDTO callRiotAPISummonerByName(String summonerName) {
		SummonerDTO result = null;

		String serverUrl = "https://kr.api.riotgames.com";

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI(serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey))
				.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if(response.statusCode() != 200) {
				return null;
			}

			result = objectMapper.readValue(response.body(), SummonerDTO.class);

		} catch (IOException | InterruptedException | URISyntaxException e){
			e.printStackTrace();
			return null;
		}

		return result;
	}
}
