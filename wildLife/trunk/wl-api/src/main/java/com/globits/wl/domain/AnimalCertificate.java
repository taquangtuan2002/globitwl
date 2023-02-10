package com.globits.wl.domain;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_animal_certificate")
@XmlRootElement
/*
 * Chứng chỉ
 * VD: 
 */
public class AnimalCertificate   extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="code",unique=true)
	private String code;
	
	@Column(name="organization_province_name")
	private String organizationProvinceName; // vd : SỞ NÔNG NGHIỆP VÀ PT TỈNH X
	
	@Column(name="organization_name")
	private String organizationName; // tên cơ quan vd: Chi cục kiểm lâm
	
	private Date dateIssue;
	private String provinceName;
	@Column(name="signer_name")
	private String signerName;
	
	@Column(name="signer_name_deputy")
	private String signerNameDeputy;
	
	@Column(name="content_provide")
	private String contentProvided; // Nội dung cấp
	
	@Column(name="recipient_first")
	private String recipientFirst; // Nơi nhận: Chủ cơ sở
	
	@Column(name="recipient_second")
	private String recipientSecond; // Nơi nhận: Cơ quan CITIES-MA
	
	@Column(name="recipient_third")
	private String recipientThird; // Nơi nhận: Hạt kiểm lâm
	
	@Column(name="recipient_fourth")
	private String recipientFourth; // Nơi nhận: Lưu BTTN, TTPC
	
	@Column(name="recipient_other")
	private String recipientOther; 
	
	@Column(name="type_signer")
	private String typeSigner;
	
	@ManyToOne
	@JoinColumn(name = "farm_id")
	private Farm farm;

	@ManyToOne()
	@JoinColumn(name = "administrative_organization_id")
	private AdministrativeOrganization administrativeOrganization;

	@OneToMany(mappedBy = "animalCertificate", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<AnimalCertificateDetail> details;
	
	
	public String getRecipientOther() {
		return recipientOther;
	}
	public void setRecipientOther(String recipientOther) {
		this.recipientOther = recipientOther;
	}
	public String getSignerName() {
		return signerName;
	}
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}
	public Farm getFarm() {
		return farm;
	}
	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	public Set<AnimalCertificateDetail> getDetails() {
		return details;
	}
	public void setDetails(Set<AnimalCertificateDetail> details) {
		this.details = details;
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
	public String getOrganizationProvinceName() {
		return organizationProvinceName;
	}
	public void setOrganizationProvinceName(String organizationProvinceName) {
		this.organizationProvinceName = organizationProvinceName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Date getDateIssue() {
		return dateIssue;
	}
	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public AnimalCertificate() {
		
	}
	public String getContentProvided() {
		return contentProvided;
	}
	public void setContentProvided(String contentProvided) {
		this.contentProvided = contentProvided;
	}
	public String getRecipientFirst() {
		return recipientFirst;
	}
	public void setRecipientFirst(String recipientFirst) {
		this.recipientFirst = recipientFirst;
	}
	public String getRecipientSecond() {
		return recipientSecond;
	}
	public void setRecipientSecond(String recipientSecond) {
		this.recipientSecond = recipientSecond;
	}
	public String getSignerNameDeputy() {
		return signerNameDeputy;
	}
	public void setSignerNameDeputy(String signerNameDeputy) {
		this.signerNameDeputy = signerNameDeputy;
	}
	public String getRecipientThird() {
		return recipientThird;
	}
	public void setRecipientThird(String recipientThird) {
		this.recipientThird = recipientThird;
	}
	public String getRecipientFourth() {
		return recipientFourth;
	}
	public void setRecipientFourth(String recipientFourth) {
		this.recipientFourth = recipientFourth;
	}
	public String getTypeSigner() {
		return typeSigner;
	}
	public void setTypeSigner(String typeSigner) {
		this.typeSigner = typeSigner;
	}

	public AdministrativeOrganization getAdministrativeOrganization() {
		return administrativeOrganization;
	}

	public void setAdministrativeOrganization(AdministrativeOrganization administrativeOrganization) {
		this.administrativeOrganization = administrativeOrganization;
	}
}
