package com.globits.wl.dto;

import com.globits.core.dto.FileDescriptionDto;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.wl.domain.UserFileAttachment;

public class UserFileAttachmentDto {
	private Long id;
	private FileDescriptionDto file;
	private UserFileUploadDto userFileUpload;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FileDescriptionDto getFile() {
		return file;
	}

	public void setFile(FileDescriptionDto file) {
		this.file = file;
	}

	
	public UserFileUploadDto getUserFileUpload() {
		return userFileUpload;
	}

	public void setUserFileUpload(UserFileUploadDto userFileUpload) {
		this.userFileUpload = userFileUpload;
	}

	public UserFileAttachmentDto() {
		
	}
	
	public UserFileAttachmentDto(UserFileAttachment entity) {
		if(entity!=null) {
			if(entity.getFile()!=null) {
				this.file = new FileDescriptionDto();
				file.setId(entity.getFile().getId());
				file.setContentSize(entity.getFile().getContentSize());
				file.setContentType(entity.getFile().getContentType());
				file.setName(entity.getFile().getName());
				file.setFilePath(entity.getFile().getFilePath());
			}
			if(entity.getUserFileUpload() != null) {
				this.userFileUpload = new UserFileUploadDto();
				this.userFileUpload.setId(entity.getUserFileUpload().getId());
				this.userFileUpload.setTitle(entity.getUserFileUpload().getTitle());
				this.userFileUpload.setDescription(entity.getUserFileUpload().getDescription());
			}
		}
	}
}
