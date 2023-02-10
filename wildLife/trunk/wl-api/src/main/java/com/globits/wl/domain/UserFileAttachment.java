package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.FileDescription;
import com.globits.security.domain.User;

@Entity
@Table(name = "tbl_user_file_attachment")
@XmlRootElement
/*
 * Các file user upload lên
 */
public class UserFileAttachment extends BaseObject{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="file_id")
	private FileDescription file;
	
	@ManyToOne
	@JoinColumn(name = "user_file_upload_id")
	private UserFileUpload userFileUpload;

	public FileDescription getFile() {
		return file;
	}

	public void setFile(FileDescription file) {
		this.file = file;
	}

	public UserFileUpload getUserFileUpload() {
		return userFileUpload;
	}

	public void setUserFileUpload(UserFileUpload userFileUpload) {
		this.userFileUpload = userFileUpload;
	}

	public UserFileAttachment() {
		this.setUuidKey(UUID.randomUUID());
	}
}
