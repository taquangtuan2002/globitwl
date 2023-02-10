package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.FileDescription;

@Entity
@Table(name = "tbl_farm_file_attachment")
@XmlRootElement
/*
 * Cơ sở chăn nuôi
 * Danh mục trang trại cần quản lý
 */
public class FarmFileAttachment extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="file_id")
	private FileDescription file;
	
	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;

	public FileDescription getFile() {
		return file;
	}

	public void setFile(FileDescription file) {
		this.file = file;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	public FarmFileAttachment() {
		this.setUuidKey(UUID.randomUUID());
	}
}
