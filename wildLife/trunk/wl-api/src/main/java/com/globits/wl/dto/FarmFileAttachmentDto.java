package com.globits.wl.dto;

import com.globits.core.dto.FileDescriptionDto;
import com.globits.wl.domain.Farm;

public class FarmFileAttachmentDto {
	private Long id;
	private FileDescriptionDto file;
	private Farm farm;

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

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

}
