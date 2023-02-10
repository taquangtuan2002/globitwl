package com.globits.wl.dto;

import java.util.HashSet;
import java.util.Set;

import com.globits.core.dto.BaseObjectDto;
import com.globits.core.dto.PersonDto;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.wl.domain.AnimalProductTarget;
import com.globits.wl.domain.AnimalRequired;
import com.globits.wl.domain.AnimalRequiredProductTarget;

public class AnimalRequiredDto extends BaseObjectDto{
	private Long id;
	private String name;
	private String code;
	private String oldCode;
	private String description;

	private AnimalTypeDto typeDto;

	private OriginalDto originalDto;

	private Set<ProductTargetDto> animalProductTargets = new HashSet<ProductTargetDto>();// Hướng sản phẩm
	
//	private ProductTargetDto productTargetDto;
	private AnimalDto parent;
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;
	private Integer orderCode;
	private String reportCode;//Sử dụng khi làm báo cáo, nếu cần nhóm 1 số loại vật nuôi lại làm 1 nhóm
	private String reportName;//Sử dụng khi làm báo cáo, nếu cần nhóm 1 số loại vật nuôi lại làm 1 nhóm
	private Double farmingTime;//thời gian nuôi (ngày)
	private Double weightGain;//tăng trọng (kg)
	private Double loss;//hao hụt(% )
	private Integer eggYield;//năng suất trứng quả/năm
	private Integer reportType;
	private Integer liveStockMethod;
	private String scienceName;// Tên Khoa học
	private String enName;// Tên tiếng Anh
	private String cites;//Cấp độ nguy cấp (CITES list là IB, IIB và IIIB)
	private String aniGroup;//Nhóm động vật - bộ
	private String vnlist;//DVHD nguy cap, quy, hiem (nghị định 64-2019)
	private String breed;//Giống động vật
	private String animalClass;//Lớp động vật
	
	private String vnlist06;

	private String animalClassSci; // Lớp động vật-tên khoa học

	private String ordo; // Bộ động vật (có vảy,...)

	private String ordoSci; // Bộ động vật (có vảy,...) - tên khoa học

	private String subOrdo; // Phân bộ bộ động vật (rắn,...)

	private String family; // Họ động vật
	private String familySci; // Họ động vật
	
	private Integer reproductionForm;// hình thức sinh sản: if is null or 1 is default to give birth; if is null or 2 to give eggs
	
	private Integer animalRequiredStatus; /**Trạng thái required tạo mới 1 animal của người dùng tới admin giá trị trong WLConstant.AnimalRequiredStatus*/
	
	private UserDto user;// user create required update animal to list
	
	private String animalGroup;
	
	private String feedBack;
	
	public AnimalRequiredDto() {
		super();
	}
	
	public AnimalRequiredDto(AnimalRequired animal) {
		super();
		if (animal != null) {
			this.code = animal.getCode();
			this.oldCode = animal.getOldCode();
			this.description = animal.getDescription();
			this.id = animal.getId();
			this.name = animal.getName();
			this.orderCode = animal.getOrderCode();
			this.reportCode=animal.getReportCode();
			this.reportName=animal.getReportName();
			this.liveStockMethod = animal.getLiveStockMethod();
			this.reportType = animal.getReportType();
			this.scienceName = animal.getScienceName();
			this.animalClass = animal.getAnimalClass();
			this.cites = animal.getCites();
			this.enName = animal.getEnName();
			this.scienceName = animal.getScienceName();
			this.aniGroup = animal.getAniGroup();
			this.vnlist = animal.getVnlist();
			this.animalClassSci = animal.getAnimalClassSci();
			this.family = animal.getFamily();
			this.familySci = animal.getFamilySci();
			this.ordo = animal.getOrdo();
			this.ordoSci = animal.getOrdoSci();
			this.subOrdo = animal.getSubOrdo();
			this.vnlist06 = animal.getVnlist06();
			this.feedBack=animal.getFeedBack();
			if (animal.getOriginal() != null) {
				this.originalDto = new OriginalDto(animal.getOriginal());
			}
			if (animal.getType() != null) {
				this.typeDto = new AnimalTypeDto(animal.getType());
			}
//			if (animal.getProductTarget() != null) {
//				this.productTargetDto = new ProductTargetDto(animal.getProductTarget());
//				;
//			}
			if(animal.getParent()!=null){
				this.parent = new AnimalDto();
				this.parent.setCode(animal.getParent().getCode());
				this.parent.setId(animal.getParent().getId());
				this.parent.setName(animal.getParent().getName());
				this.parent.setDescription(animal.getParent().getDescription());
				this.parent.setOrderCode(animal.getParent().getOrderCode());
				if(animal.getParent().getOriginal()!=null){
					this.parent.setOriginalDto(new OriginalDto(animal.getParent().getOriginal()));
				}
				if(animal.getParent().getType()!=null){
					this.parent.setTypeDto(new AnimalTypeDto(animal.getParent().getType()));
				}
//				if(animal.getParent().getProductTarget()!=null){
//					this.parent.setProductTargetDto(new ProductTargetDto(animal.getParent().getProductTarget()));
//				}
				
			}
			
			if(animal.getAnimalProductTargets()!=null && animal.getAnimalProductTargets().size()>0){
				for (AnimalRequiredProductTarget item : animal.getAnimalProductTargets()) {
					this.animalProductTargets.add(new ProductTargetDto(item.getProductTarget()));				
				
				}
			}
			
			if(animal.getUser() != null) {
				UserDto userDto = new UserDto();
				userDto.setId(animal.getUser().getId());
				userDto.setEmail(animal.getUser().getEmail());
				
				if(animal.getUser().getPerson() != null) {
					PersonDto pDto = new PersonDto();
					pDto.setId(animal.getUser().getPerson().getId());
					pDto.setDisplayName(animal.getUser().getPerson().getDisplayName());
					pDto.setPhoneNumber(animal.getUser().getPerson().getPhoneNumber());
					userDto.setPerson(pDto);
				}
				this.user=userDto;
			}
			
			this.farmingTime=animal.getFarmingTime();
			this.weightGain=animal.getWeightGain();
			this.loss=animal.getLoss();
			this.eggYield=animal.getEggYield();
			this.reproductionForm = animal.getReproductionForm();
			this.animalRequiredStatus = animal.getAnimalRequiredStatus();
			this.animalGroup= animal.getAnimalGroup();
		}
	}
	
	
	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

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
	public String getOldCode() {
		return oldCode;
	}
	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AnimalTypeDto getTypeDto() {
		return typeDto;
	}
	public void setTypeDto(AnimalTypeDto typeDto) {
		this.typeDto = typeDto;
	}
	public OriginalDto getOriginalDto() {
		return originalDto;
	}
	public void setOriginalDto(OriginalDto originalDto) {
		this.originalDto = originalDto;
	}
	public Set<ProductTargetDto> getAnimalProductTargets() {
		return animalProductTargets;
	}
	public void setAnimalProductTargets(Set<ProductTargetDto> animalProductTargets) {
		this.animalProductTargets = animalProductTargets;
	}
	public AnimalDto getParent() {
		return parent;
	}
	public void setParent(AnimalDto parent) {
		this.parent = parent;
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
	public Integer getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public Double getFarmingTime() {
		return farmingTime;
	}
	public void setFarmingTime(Double farmingTime) {
		this.farmingTime = farmingTime;
	}
	public Double getWeightGain() {
		return weightGain;
	}
	public void setWeightGain(Double weightGain) {
		this.weightGain = weightGain;
	}
	public Double getLoss() {
		return loss;
	}
	public void setLoss(Double loss) {
		this.loss = loss;
	}
	public Integer getEggYield() {
		return eggYield;
	}
	public void setEggYield(Integer eggYield) {
		this.eggYield = eggYield;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public Integer getLiveStockMethod() {
		return liveStockMethod;
	}
	public void setLiveStockMethod(Integer liveStockMethod) {
		this.liveStockMethod = liveStockMethod;
	}
	public String getScienceName() {
		return scienceName;
	}
	public void setScienceName(String scienceName) {
		this.scienceName = scienceName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCites() {
		return cites;
	}
	public void setCites(String cites) {
		this.cites = cites;
	}
	public String getAniGroup() {
		return aniGroup;
	}
	public void setAniGroup(String aniGroup) {
		this.aniGroup = aniGroup;
	}
	public String getVnlist() {
		return vnlist;
	}
	public void setVnlist(String vnlist) {
		this.vnlist = vnlist;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public String getAnimalClass() {
		return animalClass;
	}
	public void setAnimalClass(String animalClass) {
		this.animalClass = animalClass;
	}
	public String getVnlist06() {
		return vnlist06;
	}
	public void setVnlist06(String vnlist06) {
		this.vnlist06 = vnlist06;
	}
	public String getAnimalClassSci() {
		return animalClassSci;
	}
	public void setAnimalClassSci(String animalClassSci) {
		this.animalClassSci = animalClassSci;
	}
	public String getOrdo() {
		return ordo;
	}
	public void setOrdo(String ordo) {
		this.ordo = ordo;
	}
	public String getOrdoSci() {
		return ordoSci;
	}
	public void setOrdoSci(String ordoSci) {
		this.ordoSci = ordoSci;
	}
	public String getSubOrdo() {
		return subOrdo;
	}
	public void setSubOrdo(String subOrdo) {
		this.subOrdo = subOrdo;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public Integer getReproductionForm() {
		return reproductionForm;
	}
	public void setReproductionForm(Integer reproductionForm) {
		this.reproductionForm = reproductionForm;
	}
	public Integer getAnimalRequiredStatus() {
		return animalRequiredStatus;
	}
	public void setAnimalRequiredStatus(Integer animalRequiredStatus) {
		this.animalRequiredStatus = animalRequiredStatus;
	}

	public String getAnimalGroup() {
		return animalGroup;
	}

	public void setAnimalGroup(String animalGroup) {
		this.animalGroup = animalGroup;
	}

	public String getFamilySci() {
		return familySci;
	}

	public void setFamilySci(String familySci) {
		this.familySci = familySci;
	}
	
	
}
