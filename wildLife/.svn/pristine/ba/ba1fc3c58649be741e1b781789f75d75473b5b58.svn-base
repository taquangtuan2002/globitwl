package com.globits.wl.dto;

import java.util.HashSet;
import java.util.Set;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.BiologicalClass;

public class BiologicalClassDto extends BaseObjectDto {
	private String name; // tên
	
	private String sci; // tên latin
	
	private Integer type; // WLConstant.BiologicalClass 1: lớp, 2: bộ, 3: họ
	
	private String code; // mã để tạo mã động vật
	
	private BiologicalClassDto parent;

	private Set<BiologicalClassDto> subBiologicals; 
	
	private String displayText;
	
	// các trường để import
	private String classAnimal;
	private String classAnimalE;
	private String classCode;
	private String ordo;
	private String ordoE;
	private String family;
	private String familyE;

	public BiologicalClassDto() {
		super();
	}

	public BiologicalClassDto(BiologicalClass entity) {
		if(entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.sci = entity.getSci();
			this.type = entity.getType();
			this.code = entity.getCode();
			this.displayText = this.name + " - " + this.sci;
			if(entity.getParent() != null) {
				this.parent = new BiologicalClassDto(entity.getParent(), false);
			}
			this.subBiologicals = new HashSet<BiologicalClassDto>();
			if(entity.getSubBiologicals() != null && entity.getSubBiologicals().size() > 0) {
				for(BiologicalClass bc : entity.getSubBiologicals()) {
					this.subBiologicals.add(new BiologicalClassDto(bc, false));
				}
			}
			
		}
	}
	
	public BiologicalClassDto(BiologicalClass entity, Boolean simple) {
		if(entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.sci = entity.getSci();
			this.type = entity.getType();
			this.code = entity.getCode();
			this.displayText = this.name + " - " + this.sci;
			if(simple) {
				if(entity.getParent() != null) {
					this.parent = new BiologicalClassDto(entity.getParent());
				}
				this.subBiologicals = new HashSet<BiologicalClassDto>();
				if(entity.getSubBiologicals() != null && entity.getSubBiologicals().size() > 0) {
					for(BiologicalClass bc : entity.getSubBiologicals()) {
						this.subBiologicals.add(new BiologicalClassDto(bc));
					}
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSci() {
		return sci;
	}

	public void setSci(String sci) {
		this.sci = sci;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BiologicalClassDto getParent() {
		return parent;
	}

	public void setParent(BiologicalClassDto parent) {
		this.parent = parent;
	}

	public Set<BiologicalClassDto> getSubBiologicals() {
		return subBiologicals;
	}

	public void setSubBiologicals(Set<BiologicalClassDto> subBiologicals) {
		this.subBiologicals = subBiologicals;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getClassAnimal() {
		return classAnimal;
	}

	public void setClassAnimal(String classAnimal) {
		this.classAnimal = classAnimal;
	}

	public String getClassAnimalE() {
		return classAnimalE;
	}

	public void setClassAnimalE(String classAnimalE) {
		this.classAnimalE = classAnimalE;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getOrdo() {
		return ordo;
	}

	public void setOrdo(String ordo) {
		this.ordo = ordo;
	}

	public String getOrdoE() {
		return ordoE;
	}

	public void setOrdoE(String ordoE) {
		this.ordoE = ordoE;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getFamilyE() {
		return familyE;
	}

	public void setFamilyE(String familyE) {
		this.familyE = familyE;
	}
}
