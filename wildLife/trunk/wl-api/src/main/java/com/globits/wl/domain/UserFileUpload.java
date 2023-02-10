package com.globits.wl.domain;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.FileDescription;
import com.globits.security.domain.User;

@Entity
@Table(name = "tbl_user_file_upload")
@XmlRootElement
/**
 * Lưu trữ file người dùng tải lên để chia sẻ cho nhau
 */
public class UserFileUpload extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "title")
	private String title;//tiêu đề
	
	@Column(name = "description")
	private String description;//mô tả về file upload
	
	@Column(name = "organization_name")
	private String organizationName;//tên cơ quan, tổ chức

	@OneToMany(mappedBy = "userFileUpload", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<UserFileAttachment> attachments = new HashSet<UserFileAttachment>();

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="ward_id")
	private FmsAdministrativeUnit ward;
	
	@ManyToOne
	@JoinColumn(name="district_id")
	private FmsAdministrativeUnit district;
	
	@ManyToOne
	@JoinColumn(name="province_id")
	private FmsAdministrativeUnit province;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<UserFileAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<UserFileAttachment> attachments) {
		this.attachments = attachments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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

	public FmsAdministrativeUnit getWard() {
		return ward;
	}

	public void setWard(FmsAdministrativeUnit ward) {
		this.ward = ward;
	}

	public FmsAdministrativeUnit getDistrict() {
		return district;
	}

	public void setDistrict(FmsAdministrativeUnit district) {
		this.district = district;
	}

	public FmsAdministrativeUnit getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnit province) {
		this.province = province;
	}
}
