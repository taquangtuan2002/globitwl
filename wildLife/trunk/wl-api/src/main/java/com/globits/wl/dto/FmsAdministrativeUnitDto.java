package com.globits.wl.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.dto.AdministrativeUnitDto;
import com.globits.wl.domain.FmsAdministrativeUnit;

public class FmsAdministrativeUnitDto {

	private Long id;
	private String name;
	private String code;
	private Integer level;

	private FmsRegionDto regionDto;
	private FmsAdministrativeUnitDto parentDto;

	private Set<FmsAdministrativeUnitDto> subAdministrativeUnitsDto;
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;

	private List<FmsAdministrativeUnitDto> children;
	private String mapCode;
	private String longitude;// Kinh độ
	private String latitude;// Vĩ độ
	private String gMapX;// Google map X	
	private String gMapY;// Google map Y
	private Double totalAcreage;// Tổng Diện tích
	private Long parentId;
	private Long regionId;
	private String vn2000;

	public String getVn2000() {return vn2000;}

	public void setVn2000(String vn2000) {this.vn2000 = vn2000;}

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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public FmsRegionDto getRegionDto() {
		return regionDto;
	}

	public void setRegionDto(FmsRegionDto regionDto) {
		this.regionDto = regionDto;
	}

	public FmsAdministrativeUnitDto getParentDto() {
		return parentDto;
	}

	public void setParentDto(FmsAdministrativeUnitDto parentDto) {
		this.parentDto = parentDto;
	}

	public Set<FmsAdministrativeUnitDto> getSubAdministrativeUnitsDto() {
		return subAdministrativeUnitsDto;
	}

	public void setSubAdministrativeUnitsDto(Set<FmsAdministrativeUnitDto> subAdministrativeUnitsDto) {
		this.subAdministrativeUnitsDto = subAdministrativeUnitsDto;
	}

	public boolean isDuplicate() {
		return isDuplicate;
	}

	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public String getDupName() {
		return dupName;
	}

	public void setDupName(String dupName) {
		this.dupName = dupName;
	}

	public String getDupCode() {
		return dupCode;
	}

	public void setDupCode(String dupCode) {
		this.dupCode = dupCode;
	}	

	public List<FmsAdministrativeUnitDto> getChildren() {
		return children;
	}

	public void setChildren(List<FmsAdministrativeUnitDto> children) {
		this.children = children;
	}	

	public String getMapCode() {
		return mapCode;
	}

	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getgMapX() {
		return gMapX;
	}

	public void setgMapX(String gMapX) {
		this.gMapX = gMapX;
	}

	public String getgMapY() {
		return gMapY;
	}

	public void setgMapY(String gMapY) {
		this.gMapY = gMapY;
	}	

	public Double getTotalAcreage() {
		return totalAcreage;
	}

	public void setTotalAcreage(Double totalAcreage) {
		this.totalAcreage = totalAcreage;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public FmsAdministrativeUnitDto() {
		super();
	}
	public FmsAdministrativeUnitDto(FmsAdministrativeUnit administrativeUnit,boolean simple, int child) {
		super();
		this.id = administrativeUnit.getId();
		this.name = administrativeUnit.getName();
		this.code = administrativeUnit.getCode();
		this.level = administrativeUnit.getLevel();
		this.vn2000 = administrativeUnit.getVn2000();
//		if(administrativeUnit.getRegion()!=null) {
//			this.regionDto = new FmsRegionDto(administrativeUnit.getRegion());
//		}
		
//		if(administrativeUnit.getParent()!=null) {
//			FmsAdministrativeUnit parent = administrativeUnit.getParent();
//			parent.setId(administrativeUnit.getParent().getId());
//			parent.setCode(administrativeUnit.getParent().getCode());
//			parent.setName(administrativeUnit.getParent().getName());
//			parent.setLevel(administrativeUnit.getParent().getLevel());
//
//			this.parentDto = new FmsAdministrativeUnitDto(parent,true);
//		}
	}
	public FmsAdministrativeUnitDto(FmsAdministrativeUnit administrativeUnit,boolean simple) {
		super();
		this.id = administrativeUnit.getId();
		this.name = administrativeUnit.getName();
		this.code = administrativeUnit.getCode();
		this.level = administrativeUnit.getLevel();
		this.latitude=administrativeUnit.getLatitude();
		this.longitude=administrativeUnit.getLongitude();
		this.mapCode=administrativeUnit.getMapCode();
		this.gMapX=administrativeUnit.getgMapX();
		this.gMapY=administrativeUnit.getgMapY();
		this.totalAcreage=administrativeUnit.getTotalAcreage();
		this.vn2000 = administrativeUnit.getVn2000();
		
		if(administrativeUnit.getRegion()!=null) {
			this.regionDto = new FmsRegionDto(administrativeUnit.getRegion());
		}
		
		if(administrativeUnit.getParent()!=null) {
			this.parentDto = new FmsAdministrativeUnitDto(administrativeUnit.getParent(),true);
//			FmsAdministrativeUnit parent = administrativeUnit.getParent();
//			this.parentDto.setId(administrativeUnit.getParent().getId());
//			this.parentDto.setCode(administrativeUnit.getParent().getCode());
//			this.parentDto.setName(administrativeUnit.getParent().getName());
//			this.parentDto.setLevel(administrativeUnit.getParent().getLevel());
//			this.parentDto.setMapCode(administrativeUnit.getParent().getMapCode());
//			this.parentDto.setLatitude(administrativeUnit.getParent().getLatitude());
//			this.parentDto.setLongitude(administrativeUnit.getParent().getLongitude());
//			this.parentDto.setgMapX(administrativeUnit.getParent().getgMapX());
//			this.parentDto.setgMapY(administrativeUnit.getParent().getgMapY());
//			this.parentDto.setTotalAcreage(administrativeUnit.getParent().getTotalAcreage());
//			this.parentDto.setVn2000(administrativeUnit.getVn2000());
//			this.parentDto = new FmsAdministrativeUnitDto(parent,true);
		}
	}
	public FmsAdministrativeUnitDto(FmsAdministrativeUnit administrativeUnit) {
		super();
		this.id = administrativeUnit.getId();
		this.name = administrativeUnit.getName();
		this.code = administrativeUnit.getCode();
		this.level = administrativeUnit.getLevel();
		this.mapCode=administrativeUnit.getMapCode();
		this.latitude=administrativeUnit.getLatitude();
		this.longitude=administrativeUnit.getLongitude();
		this.gMapX=administrativeUnit.getgMapX();
		this.gMapY=administrativeUnit.getgMapY();
		this.totalAcreage=administrativeUnit.getTotalAcreage();
		this.vn2000 = administrativeUnit.getVn2000();
		if(administrativeUnit.getRegion()!=null) {
			this.regionDto = new FmsRegionDto(administrativeUnit.getRegion());
		}
		
		if(administrativeUnit.getParent()!=null) {
			this.parentDto = new FmsAdministrativeUnitDto(administrativeUnit.getParent(),true);
//			FmsAdministrativeUnit parent = administrativeUnit.getParent();
//			this.parentDto.setId(administrativeUnit.getParent().getId());
//			this.parentDto.setCode(administrativeUnit.getParent().getCode());
//			this.parentDto.setName(administrativeUnit.getParent().getName());
//			this.parentDto.setLevel(administrativeUnit.getParent().getLevel());
//			this.parentDto.setMapCode(administrativeUnit.getParent().getMapCode());
//			this.parentDto.setLatitude(administrativeUnit.getParent().getLatitude());
//			this.parentDto.setLongitude(administrativeUnit.getParent().getLongitude());
//			this.parentDto.setgMapX(administrativeUnit.getParent().getgMapX());
//			this.parentDto.setgMapY(administrativeUnit.getParent().getgMapY());
//			this.parentDto.setTotalAcreage(administrativeUnit.getParent().getTotalAcreage());
//			this.parentDto.setVn2000(administrativeUnit.getParent().getVn2000());;
//			this.parentDto = new FmsAdministrativeUnitDto(parent);
			this.parentId =administrativeUnit.getParent().getId();
		}
		
		Set<FmsAdministrativeUnitDto> administrativeUnitsDtos = new HashSet<FmsAdministrativeUnitDto>();
		if (administrativeUnit != null && administrativeUnit.getSubAdministrativeUnits() != null
				&& administrativeUnit.getSubAdministrativeUnits().size() > 0) {
			for (FmsAdministrativeUnit adu : administrativeUnit.getSubAdministrativeUnits()) {
				FmsAdministrativeUnitDto subAdministrativeUnitsDto = new FmsAdministrativeUnitDto();
				subAdministrativeUnitsDto.setId(adu.getId());
				subAdministrativeUnitsDto.setCode(adu.getCode());
				subAdministrativeUnitsDto.setName(adu.getName());
				subAdministrativeUnitsDto.setVn2000(adu.getVn2000());
				subAdministrativeUnitsDto.setMapCode(adu.getMapCode());
				subAdministrativeUnitsDto.setLatitude(adu.getLatitude());
				subAdministrativeUnitsDto.setLongitude(adu.getLongitude());
				subAdministrativeUnitsDto.setgMapX(adu.getgMapX());
				subAdministrativeUnitsDto.setgMapY(adu.getgMapY());
				subAdministrativeUnitsDto.setTotalAcreage(adu.getTotalAcreage());
				subAdministrativeUnitsDto.setVn2000(adu.getVn2000());
				administrativeUnitsDtos.add(subAdministrativeUnitsDto);

			}
			this.subAdministrativeUnitsDto = administrativeUnitsDtos;
		}
		//this.setChildren(getListChildren(administrativeUnit));
	}
	
	private List<FmsAdministrativeUnitDto> getListChildren(FmsAdministrativeUnit unit){
		List<FmsAdministrativeUnitDto> ret = new ArrayList<FmsAdministrativeUnitDto>();
		
		if(unit.getSubAdministrativeUnits()!=null &&unit.getSubAdministrativeUnits().size()>0) {
			for(FmsAdministrativeUnit s : unit.getSubAdministrativeUnits()) {
				FmsAdministrativeUnitDto sDto = new FmsAdministrativeUnitDto();
				sDto.setId(s.getId());
				sDto.setCode(s.getCode());
				sDto.setName(s.getName());
				sDto.setLevel(s.getLevel());
				sDto.setChildren(getListChildren(s));
				
				sDto.setMapCode(s.getMapCode());
				sDto.setLatitude(s.getLatitude());
				sDto.setLongitude(s.getLongitude());
				sDto.setgMapX(s.getgMapX());
				sDto.setgMapY(getgMapY());
				sDto.setTotalAcreage(getTotalAcreage());
				sDto.setVn2000(s.getVn2000());
				ret.add(sDto);
			}
		}
		return ret;
	}
	
	public FmsAdministrativeUnitDto(FmsAdministrativeUnit administrativeUnit, int type) {
		super();
		this.id = administrativeUnit.getId();
		this.name = administrativeUnit.getName();
		this.code = administrativeUnit.getCode();
		this.level = administrativeUnit.getLevel();
		this.mapCode = administrativeUnit.getMapCode();
		this.latitude = administrativeUnit.getLatitude();
		this.longitude = administrativeUnit.getLongitude();
		this.gMapX = administrativeUnit.getgMapX();
		this.gMapY = administrativeUnit.getgMapY();
		this.totalAcreage=administrativeUnit.getTotalAcreage();
		this.vn2000 = administrativeUnit.getVn2000();
		if(administrativeUnit.getParent()!=null) {
			this.parentDto = new FmsAdministrativeUnitDto(administrativeUnit.getParent(),true);
//			FmsAdministrativeUnit parent = administrativeUnit.getParent();
//			this.parentDto.setId(administrativeUnit.getParent().getId());
//			this.parentDto.setCode(administrativeUnit.getParent().getCode());
//			this.parentDto.setName(administrativeUnit.getParent().getName());
//			this.parentDto.setLevel(administrativeUnit.getParent().getLevel());
//			
//			this.parentDto.setMapCode(administrativeUnit.getParent().getMapCode());
//			this.parentDto.setLatitude(administrativeUnit.getParent().getLatitude());
//			this.parentDto.setLongitude(administrativeUnit.getParent().getLongitude());
//			this.parentDto.setgMapX(administrativeUnit.getParent().getgMapX());
//			this.parentDto.setgMapY(administrativeUnit.getParent().getgMapY());
//			this.parentDto.setTotalAcreage(administrativeUnit.getParent().getTotalAcreage());
//			this.parentDto.setVn2000(administrativeUnit.getVn2000());
//			this.parentDto = new FmsAdministrativeUnitDto(parent);
		}
		
		if(type==1) {
			if(administrativeUnit.getRegion()!=null) {
				this.regionDto = new FmsRegionDto(administrativeUnit.getRegion());
			}
			Set<FmsAdministrativeUnitDto> administrativeUnitsDtos = new HashSet<FmsAdministrativeUnitDto>();
			if (administrativeUnit != null && administrativeUnit.getSubAdministrativeUnits() != null
					&& administrativeUnit.getSubAdministrativeUnits().size() > 0) {
				for (FmsAdministrativeUnit adu : administrativeUnit.getSubAdministrativeUnits()) {
					FmsAdministrativeUnitDto subAdministrativeUnitsDto = new FmsAdministrativeUnitDto();
					subAdministrativeUnitsDto.setId(adu.getId());
					subAdministrativeUnitsDto.setCode(adu.getCode());
					subAdministrativeUnitsDto.setName(adu.getName());
					subAdministrativeUnitsDto.setVn2000(adu.getVn2000());
					administrativeUnitsDtos.add(subAdministrativeUnitsDto);
				}
				this.subAdministrativeUnitsDto = administrativeUnitsDtos;
			}
			this.setChildren(getListChildren(administrativeUnit));
		}		
	}
}
