package com.globits.wl.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalCertificate;
import com.globits.wl.domain.AnimalCertificateDetail;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.ForestProductsListDetail;

public class AnimalCertificateDto extends BaseObjectDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String code;
	
	private String organizationProvinceName; // vd : SỞ NÔNG NGHIỆP VÀ PT TỈNH X
	
	private String organizationName; // tên cơ quan vd: Chi cục kiểm lâm
	
	private Date dateIssue;
	private String provinceName; 
	private String signerName;
	private FarmDto farm;
	private boolean isDuplicate;
	private Set<AnimalCertificateDetailDto> details;
	private AnimalCertificateDetailDto animalCertificateDetailDto;
	private AdministrativeOrganizationDto administrativeOrganization;
	private String signerNameDeputy;
	private String contentProvided;
	private String recipientFirst;
	private String recipientSecond;
	private String recipientThird;
	private String recipientFourth;
	private String recipientOther;
	private String typeSigner;
	private Boolean isCheckFarm;
	
	
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
	public FarmDto getFarm() {
		return farm;
	}
	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}
	public Set<AnimalCertificateDetailDto> getDetails() {
		return details;
	}
	public void setDetails(Set<AnimalCertificateDetailDto> details) {
		this.details = details;
	}
	public AnimalCertificateDetailDto getAnimalCertificateDetailDto() {
		return animalCertificateDetailDto;
	}
	public void setAnimalCertificateDetailDto(AnimalCertificateDetailDto animalCertificateDetailDto) {
		this.animalCertificateDetailDto = animalCertificateDetailDto;
	}

	public AdministrativeOrganizationDto getAdministrativeOrganization() {
		return administrativeOrganization;
	}

	public void setAdministrativeOrganization(AdministrativeOrganizationDto administrativeOrganization) {
		this.administrativeOrganization = administrativeOrganization;
	}

	public boolean isDuplicate() {
		return isDuplicate;
	}
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
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
	public Boolean getIsCheckFarm() {
		return isCheckFarm;
	}
	public void setIsCheckFarm(Boolean isCheckFarm) {
		this.isCheckFarm = isCheckFarm;
	}
	public AnimalCertificateDto() {
		super();
		
	}
	public AnimalCertificateDto(AnimalCertificate entity) {
		super();
		this.id = entity.getId();
		if(entity!=null) {
			this.id=entity.getId();
		}
		if(entity.getName()!=null) {
			this.name=entity.getName();
		}
		if(entity.getCode()!=null) {
			this.code=entity.getCode();
		}
		if(entity.getOrganizationProvinceName()!=null) {
			this.organizationProvinceName=entity.getOrganizationProvinceName();
		}
		if(entity.getOrganizationName()!=null) {
			this.organizationName=entity.getOrganizationName();
		}
		if(entity.getDateIssue()!=null) {
			this.dateIssue=entity.getDateIssue();
		}
		if(entity.getProvinceName()!=null) {
			this.provinceName=entity.getProvinceName();
		}
		if(entity.getFarm()!=null) {
			this.farm=new FarmDto(entity.getFarm());
		}
		if(entity.getSignerName()!=null) {
			this.signerName= entity.getSignerName();
		}
		if(entity.getSignerNameDeputy() != null) {
			this.signerNameDeputy = entity.getSignerNameDeputy();
		}
		if(entity.getContentProvided() != null) {
			this.contentProvided = entity.getContentProvided();
		}
		if(entity.getRecipientFirst() != null) {
			this.recipientFirst = entity.getRecipientFirst();
		}
		if(entity.getRecipientSecond() != null) {
			this.recipientSecond = entity.getRecipientSecond();
		}
		if(entity.getRecipientThird() != null) {
			this.recipientThird = entity.getRecipientThird();
		}
		if(entity.getRecipientFourth() != null) {
			this.recipientFourth = entity.getRecipientFourth();
		}
		if(entity.getRecipientOther() != null) {
			this.recipientOther = entity.getRecipientOther();
		}
		if(entity.getTypeSigner() != null) {
			this.typeSigner = entity.getTypeSigner();
		}
		if (entity.getAdministrativeOrganization() != null){
			this.administrativeOrganization = new AdministrativeOrganizationDto(entity.getAdministrativeOrganization());
		}
		if(entity.getDetails() != null && entity.getDetails().size() > 0) {
			this.details = new HashSet<AnimalCertificateDetailDto>();
			for(AnimalCertificateDetail animalCertificateDetail : entity.getDetails()) {
				if(animalCertificateDetail != null) { 
					this.details.add(new AnimalCertificateDetailDto(animalCertificateDetail));
				}
			}
		}	
	}
	public AnimalCertificateDto(AnimalCertificateDto another) {
		super();
		this.id = another.getId();
		if(another!=null) {
			this.id=another.getId();
		}
		if(another.getName()!=null) {
			this.name=another.getName();
		}
		if(another.getCode()!=null) {
			this.code=another.getCode();
		}
		if(another.getOrganizationProvinceName()!=null) {
			this.organizationProvinceName=another.getOrganizationProvinceName();
		}
		if(another.getOrganizationName()!=null) {
			this.organizationName=another.getOrganizationName();
		}
		if(another.getDateIssue()!=null) {
			this.dateIssue=another.getDateIssue();
		}
		if(another.getProvinceName()!=null) {
			this.provinceName=another.getProvinceName();
		}
		if(another.getFarm()!=null) {
			this.farm=another.getFarm();
		}
		if(another.getFarm()!=null) {
			this.farm=another.getFarm();
		}
		if(another.getSignerName()!=null) {
			this.signerName= another.getSignerName();
		}
		if(another.getDetails() != null && another.getDetails().size() > 0) {
			this.details = new HashSet<AnimalCertificateDetailDto>();
			for(AnimalCertificateDetailDto animalCertificateDetailDto : another.getDetails()) {
				if(animalCertificateDetailDto != null) { 
					this.details.add(animalCertificateDetailDto);
				}
			}
		}
	}
	
}
