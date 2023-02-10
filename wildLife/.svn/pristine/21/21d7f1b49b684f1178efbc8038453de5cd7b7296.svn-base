package com.globits.wl.dto;

import java.util.HashMap;
import java.util.Map;

public class DeleteMapByAreaDto {
	public String areaId;
	public String year;
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> jo = new HashMap<String, Object>();
		if(areaId != null)
		jo.put("area_id", areaId);
		if(year!=null) {
			jo.put("year", year);
		}
		return jo;
	}	
}
