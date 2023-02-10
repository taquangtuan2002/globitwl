package com.globits.wl.dto.functiondto;

import java.util.LinkedHashMap;
import java.util.List;

public class MapReturnDto {
	private List<MapErrorReturnDto> errors;
	private String status;
	public List<MapErrorReturnDto> getErrors() {
		return errors;
	}
	public void setErrors(List<MapErrorReturnDto> errors) {
		this.errors = errors;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public MapReturnDto() {
		
	}
}
