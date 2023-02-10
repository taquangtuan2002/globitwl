package com.globits.wl.dto;

public class FarmStoreDto {
	private Long id;
	private String name;
	private String code;
	private String adress;
	private String phoneNumber;
	private FmsAdministrativeUnitDto administrativeUnitDto;
	private FarmDto farmDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public FmsAdministrativeUnitDto getAdministrativeUnitDto() {
		return administrativeUnitDto;
	}

	public void setAdministrativeUnitDto(FmsAdministrativeUnitDto administrativeUnitDto) {
		this.administrativeUnitDto = administrativeUnitDto;
	}

	public FarmDto getFarmDto() {
		return farmDto;
	}

	public void setFarmDto(FarmDto farmDto) {
		this.farmDto = farmDto;
	}

}
