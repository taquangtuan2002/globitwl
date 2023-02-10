package com.globits.wl.utils;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globits.wl.dto.functiondto.DataDvhdDto;
import com.globits.wl.dto.functiondto.ImportResultDto;
import com.globits.wl.dto.functiondto.ImpotList16Dto;

public class HelperUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	public static ImportResultDto<DataDvhdDto> callApiUpdateListFromFileImportByListDto(String url, ImpotList16Dto dto)
			throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<ImportResultDto> rateResponse = restTemplate.postForEntity(url, dto, ImportResultDto.class);
		return rateResponse.getBody();
	}

}
