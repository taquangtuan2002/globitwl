package com.globits.wl.dto;

import java.util.HashMap;
import java.util.Map;

import com.globits.wl.domain.Animal;

public class AnimalMapDto {
	private String aniGroup;
	private String animalClass;
	private String cities;
	private String code;
	private String description;
	private String enName;
	private String family;
	private String name;
	private String ordo;
	private String scienceName;
	private String vnlist;
	private String vnlist06;
	
	public String getAniGroup() {
		return aniGroup;
	}
	public void setAniGroup(String aniGroup) {
		this.aniGroup = aniGroup;
	}
	public String getAnimalClass() {
		return animalClass;
	}
	public void setAnimalClass(String animalClass) {
		this.animalClass = animalClass;
	}
	public String getCities() {
		return cities;
	}
	public void setCities(String cities) {
		this.cities = cities;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrdo() {
		return ordo;
	}
	public void setOrdo(String ordo) {
		this.ordo = ordo;
	}
	public String getScienceName() {
		return scienceName;
	}
	public void setScienceName(String scienceName) {
		this.scienceName = scienceName;
	}
	public String getVnlist() {
		return vnlist;
	}
	public void setVnlist(String vnlist) {
		this.vnlist = vnlist;
	}
	public String getVnlist06() {
		return vnlist06;
	}
	public void setVnlist06(String vnlist06) {
		this.vnlist06 = vnlist06;
	}
	public AnimalMapDto() {
		
	}
	public AnimalMapDto(Animal animal) {
		this.aniGroup = animal.getAniGroup();
		this.animalClass = animal.getAnimalClass();
		this.cities = animal.getCites();
		this.code = animal.getCode();
		this.description = animal.getDescription();
		this.enName = animal.getEnName();
		this.family = animal.getFamily();
		this.name = animal.getName();
		this.ordo = animal.getOrdo();
		this.scienceName = animal.getScienceName();
		this.vnlist = animal.getVnlist();
		this.vnlist06 = animal.getVnlist06();
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> jo = new HashMap<String, Object>();
		if(aniGroup != null)
		jo.put("aniGroup", aniGroup);
		
		if(animalClass != null)
		jo.put("animalClass", animalClass);
		
		if(cities != null)
		jo.put("cities", cities);
		
		if(code != null)
		jo.put("code", code);
		
		if(description != null)
		jo.put("description", description);
		
		if(enName != null)
		jo.put("enName", enName);
		
		if(family != null)
		jo.put("family", family);
		
		if(name != null)
		jo.put("name", name);
		
		if(ordo != null)
		jo.put("ordo", ordo);
		
		if(scienceName != null)
		jo.put("scienceName", scienceName);
		
		if(vnlist != null)
		jo.put("vnlist", vnlist);
		
		if(vnlist06 != null)
		jo.put("vnlist06", vnlist06);
		
		return jo;
	}
}
