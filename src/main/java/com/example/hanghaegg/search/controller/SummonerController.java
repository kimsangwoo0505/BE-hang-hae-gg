package com.example.hanghaegg.search.controller;

import java.util.List;

import com.example.hanghaegg.search.dto.SummernerRealDto;
import com.example.hanghaegg.search.dto.SummonerDTO;
import com.example.hanghaegg.search.dto.infoResponsDto;
import com.example.hanghaegg.search.service.SummonerService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SummonerController {

	private final SummonerService summonerService;

	@PostMapping(value = "/summonerByName")
	@ResponseBody
	public infoResponsDto callSummonerByName(@RequestParam String summonerName){

		summonerName = summonerName.replaceAll(" ","%20");//URL에서는 공백 문자를 직접 사용할 수 없으므로, 대신에 %20이라는 URL 인코딩된 형태를 사용
		//summonerName 문자열에서 모든 공백 문자를 URL 인코딩된 형태인 %20으로 교체함

		// SummonerDTO apiResult = summonerService.callRiotAPISummonerByName(summonerName);

		return summonerService.callRiotAPISummonerByName(summonerName);
	}
}
