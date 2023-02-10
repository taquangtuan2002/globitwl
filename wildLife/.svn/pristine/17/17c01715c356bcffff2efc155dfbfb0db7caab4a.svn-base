package com.globits.wl.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.globits.cms.dto.BaseObjectDto;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.security.dto.UserDto;
import com.globits.wl.domain.FarmFileAttachment;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.UserAttachments;
import com.globits.wl.domain.UserFileAttachment;
import com.globits.wl.domain.UserFileUpload;

public class UserFileUploadDto extends BaseObjectDto {
	private String title;
	private String description;
	private Set<UserFileAttachmentDto> attachments = new HashSet<UserFileAttachmentDto>();
	private UserDto user;
	private String organizationName;
	private FmsAdministrativeUnitDto ward;
	private FmsAdministrativeUnitDto district;
	private FmsAdministrativeUnitDto province;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FmsAdministrativeUnitDto getWard() {
		return ward;
	}
	public void setWard(FmsAdministrativeUnitDto ward) {
		this.ward = ward;
	}
	public FmsAdministrativeUnitDto getDistrict() {
		return district;
	}
	public void setDistrict(FmsAdministrativeUnitDto district) {
		this.district = district;
	}
	public FmsAdministrativeUnitDto getProvince() {
		return province;
	}
	public void setProvince(FmsAdministrativeUnitDto province) {
		this.province = province;
	}
	
	public Set<UserFileAttachmentDto> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<UserFileAttachmentDto> attachments) {
		this.attachments = attachments;
	}
	public UserFileUploadDto() {
		super();
	}
	public UserFileUploadDto(UserFileUpload entity) {
		super();
		if (entity != null) {
			this.setId(entity.getId());
			this.description = entity.getDescription();
			this.title = entity.getTitle();
			this.organizationName = entity.getOrganizationName();
			if (entity.getUser() != null) {
				this.user = new UserDto();
				if (entity.getUser().getPerson() != null && entity.getUser().getPerson().getId() != null) {
					this.user.setDisplayName(entity.getUser().getPerson().getDisplayName());
				}
				this.user.setUsername(entity.getUser().getUsername());
			}
			if (entity.getAttachments() != null) {
				for (UserFileAttachment att : entity.getAttachments()) {
					UserFileAttachmentDto attDto = new UserFileAttachmentDto(att);
					if (att.getFile() != null) {
						FileDescriptionDto fileDescription = new FileDescriptionDto(att.getFile());
						attDto.setFile(fileDescription);
					}
					this.getAttachments().add(attDto);
				}
			}
			if (entity.getWard() != null) {
				this.ward = new FmsAdministrativeUnitDto(entity.getWard());
			}
			if (entity.getDistrict() != null) {
				this.district = new FmsAdministrativeUnitDto(entity.getDistrict());
			}
			if (entity.getProvince() != null) {
				this.province = new FmsAdministrativeUnitDto(entity.getProvince());
			}
		}
	}
}
