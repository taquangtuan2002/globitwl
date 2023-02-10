
package com.globits.wl.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.globits.wl.domain.AdministrativeOrganization;
import com.globits.wl.domain.FmsOrganizationAdministrative;

public class AdministrativeOrganizationDto {
	private Long id;
	private Integer organizationLevel;
	private String name;
	private String abbreviations;
	private String address;
	private Integer governmentLevel;
	private Integer organizationalForm;
	private String email;
	private String phoneNumber;
	private String fax;
	private String description;
	private String website;
	private Integer numbericalOrder;
	private AdministrativeOrganizationDto parentDto;
	private Long parentId;
	private String displayName;
	private String positionName;
	private String phoneNumberAgencyRepresentative;
	private String emailAgencyRepresentative;
	private Set<AdministrativeOrganizationDto> childrenDto;
	private List<FmsAdministrativeUnitDto> fmsOrganiration;

	public AdministrativeOrganizationDto() {

	}

	public AdministrativeOrganizationDto(AdministrativeOrganization entity) {
		this.id = entity.getId();
		this.organizationLevel = entity.getOrganizationLevel();
		this.name = entity.getName();
		this.abbreviations = entity.getAbbreviations();
		this.address = entity.getAddress();
		this.governmentLevel = entity.getGovernmentLevel();
		this.organizationalForm = entity.getOrganizationalForm();
		this.email = entity.getEmail();
		this.phoneNumber = entity.getPhoneNumber();
		this.fax = entity.getFax();
		this.description = entity.getDescription();
		this.website = entity.getWebsite();
		this.numbericalOrder = entity.getNumbericalOrder();
		this.displayName = entity.getDisplayName();
		this.positionName = entity.getPositionName();
		this.phoneNumberAgencyRepresentative = entity.getPhoneNumberAgencyRepresentative();
		this.emailAgencyRepresentative = entity.getEmailAgencyRepresentative();
		// Parent
		if (entity.getParent() != null) {
			AdministrativeOrganization parent = entity.getParent();
			parent.setId(entity.getParent().getId());
			parent.setOrganizationLevel(entity.getParent().getOrganizationLevel());
			parent.setName(entity.getParent().getName());
			parent.setAbbreviations(entity.getParent().getAbbreviations());
			parent.setAddress(entity.getParent().getAddress());
			parent.setGovernmentLevel(entity.getParent().getGovernmentLevel());
			parent.setOrganizationalForm(entity.getParent().getOrganizationalForm());
			parent.setEmail(entity.getParent().getEmail());
			parent.setPhoneNumber(entity.getParent().getPhoneNumber());
			parent.setFax(entity.getParent().getFax());
			parent.setDescription(entity.getParent().getDescription());
			parent.setWebsite(entity.getParent().getWebsite());
			parent.setNumbericalOrder(entity.getParent().getNumbericalOrder());
			parent.setDisplayName(entity.getParent().getDisplayName());
			parent.setPositionName(entity.getParent().getPositionName());
			parent.setPhoneNumberAgencyRepresentative(entity.getParent().getPhoneNumberAgencyRepresentative());
			parent.setEmailAgencyRepresentative(entity.getParent().getEmailAgencyRepresentative());

			this.parentDto = new AdministrativeOrganizationDto(parent);
			this.parentId = entity.getParent().getId();

		}
		// Children
		Set<AdministrativeOrganizationDto> administrativeOrganizationDtos = new HashSet<AdministrativeOrganizationDto>();
		if (entity != null && entity.getChildren() != null
				&& entity.getChildren().size() > 0) {
			for (AdministrativeOrganization ao : entity.getChildren()) {
				AdministrativeOrganizationDto subAoDto = new AdministrativeOrganizationDto();
				subAoDto.setId(ao.getId());
				subAoDto.setOrganizationLevel(ao.getOrganizationLevel());
				subAoDto.setName(ao.getName());
				subAoDto.setAbbreviations(ao.getAbbreviations());
				subAoDto.setAddress(ao.getAddress());
				subAoDto.setGovernmentLevel(ao.getGovernmentLevel());
				subAoDto.setOrganizationalForm(ao.getOrganizationalForm());
				subAoDto.setEmail(ao.getEmail());
				subAoDto.setPhoneNumber(ao.getPhoneNumber());
				subAoDto.setFax(ao.getFax());
				subAoDto.setDescription(ao.getDescription());
				subAoDto.setWebsite(ao.getWebsite());
				subAoDto.setNumbericalOrder(ao.getNumbericalOrder());
				subAoDto.setDisplayName(ao.getDisplayName());
				subAoDto.setPositionName(ao.getPositionName());
				subAoDto.setPhoneNumberAgencyRepresentative(ao.getPhoneNumberAgencyRepresentative());
				subAoDto.setEmailAgencyRepresentative(ao.getEmailAgencyRepresentative());

				administrativeOrganizationDtos.add(subAoDto);
			}
			this.childrenDto = administrativeOrganizationDtos;
		}
		// Map tỉnh, huyện, xã qua bảng chung gian
		if(entity.getFmsOrganization() != null && !entity.getFmsOrganization().isEmpty()) {
			this.fmsOrganiration = new ArrayList<>();
			for(FmsOrganizationAdministrative fmsOrganization : entity.getFmsOrganization()) {
				this.fmsOrganiration.add(new FmsAdministrativeUnitDto(fmsOrganization.getFmsAdministrativeUnit(), true));
			}
		}
		
	}

	public AdministrativeOrganizationDto(AdministrativeOrganization entity, boolean arc) {
		this.id = entity.getId();
		this.organizationLevel = entity.getOrganizationLevel();
		this.name = entity.getName();
		this.abbreviations = entity.getAbbreviations();
		this.address = entity.getAddress();
		this.governmentLevel = entity.getGovernmentLevel();
		this.organizationalForm = entity.getOrganizationalForm();
		this.email = entity.getEmail();
		this.phoneNumber = entity.getPhoneNumber();
		this.fax = entity.getFax();
		this.description = entity.getDescription();
		this.website = entity.getWebsite();
		this.numbericalOrder = entity.getNumbericalOrder();

		// Parent
		if (entity.getParent() != null) {
			AdministrativeOrganization parent = entity.getParent();
			parent.setId(entity.getParent().getId());
			parent.setOrganizationLevel(entity.getParent().getOrganizationLevel());
			parent.setName(entity.getParent().getName());
			parent.setAbbreviations(entity.getParent().getAbbreviations());
			parent.setAddress(entity.getParent().getAddress());
			parent.setGovernmentLevel(entity.getParent().getGovernmentLevel());
			parent.setOrganizationalForm(entity.getParent().getOrganizationalForm());
			parent.setEmail(entity.getParent().getEmail());
			parent.setPhoneNumber(entity.getParent().getPhoneNumber());
			parent.setFax(entity.getParent().getFax());
			parent.setDescription(entity.getParent().getDescription());
			parent.setWebsite(entity.getParent().getWebsite());
			parent.setNumbericalOrder(entity.getParent().getNumbericalOrder());
			parent.setDisplayName(entity.getParent().getDisplayName());
			parent.setPositionName(entity.getParent().getPositionName());
			parent.setPhoneNumberAgencyRepresentative(entity.getParent().getPhoneNumberAgencyRepresentative());
			parent.setEmailAgencyRepresentative(entity.getParent().getEmailAgencyRepresentative());

			this.parentDto = new AdministrativeOrganizationDto(parent, true);
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrganizationLevel() {
		return organizationLevel;
	}

	public void setOrganizationLevel(Integer organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviations() {
		return abbreviations;
	}

	public void setAbbreviations(String abbreviations) {
		this.abbreviations = abbreviations;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGovernmentLevel() {
		return governmentLevel;
	}

	public void setGovernmentLevel(Integer governmentLevel) {
		this.governmentLevel = governmentLevel;
	}

	public Integer getOrganizationalForm() {
		return organizationalForm;
	}

	public void setOrganizationalForm(Integer organizationalForm) {
		this.organizationalForm = organizationalForm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getNumbericalOrder() {
		return numbericalOrder;
	}

	public void setNumbericalOrder(Integer numbericalOrder) {
		this.numbericalOrder = numbericalOrder;
	}

	public AdministrativeOrganizationDto getParentDto() {
		return parentDto;
	}

	public void setParentDto(AdministrativeOrganizationDto parentDto) {
		this.parentDto = parentDto;
	}

	public Set<AdministrativeOrganizationDto> getChildrenDto() {
		return childrenDto;
	}

	public void setChildrenDto(Set<AdministrativeOrganizationDto> childrenDto) {
		this.childrenDto = childrenDto;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<FmsAdministrativeUnitDto> getFmsOrganiration() {
		return fmsOrganiration;
	}

	public void setFmsOrganiration(List<FmsAdministrativeUnitDto> fmsOrganiration) {
		this.fmsOrganiration = fmsOrganiration;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPhoneNumberAgencyRepresentative() {
		return phoneNumberAgencyRepresentative;
	}

	public void setPhoneNumberAgencyRepresentative(String phoneNumberAgencyRepresentative) {
		this.phoneNumberAgencyRepresentative = phoneNumberAgencyRepresentative;
	}

	public String getEmailAgencyRepresentative() {
		return emailAgencyRepresentative;
	}

	public void setEmailAgencyRepresentative(String emailAgencyRepresentative) {
		this.emailAgencyRepresentative = emailAgencyRepresentative;
	}
}
