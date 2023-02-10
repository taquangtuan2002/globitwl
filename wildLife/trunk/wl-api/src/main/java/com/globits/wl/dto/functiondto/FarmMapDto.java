package com.globits.wl.dto.functiondto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class FarmMapDto {
	private String addressDetail;
	
	private String code;
	
	private String districtCode;
	
	private Double latitude;
	
	private Double longitude;
	
	private String name;
	
	private String newRegistrationCode;
	
	private String ownerCitizenCode;
	
	private String ownerGender;
	
	private String ownerName;
	
	private String ownerPhoneNumber;
	
	private String phoneNumber;
	
	private String provinceCode;
	
	private Integer status;
	
	private String stopDate;
	
	private String village;
	
	private String wardCode;
	
	private Integer year;

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNewRegistrationCode() {
		return newRegistrationCode;
	}

	public void setNewRegistrationCode(String newRegistrationCode) {
		this.newRegistrationCode = newRegistrationCode;
	}

	public String getOwnerCitizenCode() {
		return ownerCitizenCode;
	}

	public void setOwnerCitizenCode(String ownerCitizenCode) {
		this.ownerCitizenCode = ownerCitizenCode;
	}

	public String getOwnerGender() {
		return ownerGender;
	}

	public void setOwnerGender(String ownerGender) {
		this.ownerGender = ownerGender;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}

	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		this.ownerPhoneNumber = ownerPhoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public String getStopDate() {
		return this.stopDate;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getWardCode() {
		return wardCode;
	}

	public void setWardCode(String wardCode) {
		this.wardCode = wardCode;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("addressDetail", addressDetail);
		jo.put("code", code);
		jo.put("districtCode", districtCode);
		jo.put("latitude", latitude);
		jo.put("longitude", longitude);
		jo.put("name", name);
		jo.put("newRegistrationCode", newRegistrationCode);
		jo.put("ownerCitizenCode", ownerCitizenCode);
		jo.put("ownerGender", ownerGender);
		jo.put("ownerName", ownerName);
		jo.put("ownerPhoneNumber", ownerPhoneNumber);
		jo.put("phoneNumber", phoneNumber);
		jo.put("provinceCode", provinceCode);
		jo.put("status", status);
		if(this.stopDate != null) {			
			jo.put("stopDate", stopDate);
		}
		jo.put("village", village);
		jo.put("wardCode", wardCode);
		jo.put("year", year);
		
		return jo;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> jo = new HashMap<String, Object>();
		if(addressDetail != null)
		jo.put("addressDetail", addressDetail);
		jo.put("code", code);
		
		if(districtCode != null)
		jo.put("districtCode", districtCode);
		
		if(latitude != null)
		jo.put("latitude", latitude);
		
		if(longitude != null)
		jo.put("longitude", longitude);
		
		if(name != null)
		jo.put("name", name);
		
		if(newRegistrationCode != null)
		jo.put("newRegistrationCode", newRegistrationCode);
		if(ownerCitizenCode != null)
			
		jo.put("ownerCitizenCode", ownerCitizenCode);
		if(ownerGender != null)
		jo.put("ownerGender", ownerGender);
		
		if(ownerName != null)
		jo.put("ownerName", ownerName);
		
		if(ownerPhoneNumber != null)
		jo.put("ownerPhoneNumber", ownerPhoneNumber);
		
		if(phoneNumber != null)
		jo.put("phoneNumber", phoneNumber);
		
		if(provinceCode != null)
		jo.put("provinceCode", provinceCode);
		
		if(status != null)
		jo.put("status", status);
		
		if(this.stopDate != null) {			
			jo.put("stopDate", stopDate);
		}
		
		if(village != null)
		jo.put("village", village);
		
		if(wardCode != null)
		jo.put("wardCode", wardCode);
		
		jo.put("year", year);
		
		return jo;
	}

	public FarmMapDto() {
		super();
	}
	
	
}

