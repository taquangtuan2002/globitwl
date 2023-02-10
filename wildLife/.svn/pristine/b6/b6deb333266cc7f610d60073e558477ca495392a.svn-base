package com.globits.wl.dto.report;

public class AnimalRaisingSymbolNameDto {
	private String symbol;
	private String name;
	int a = 10;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return a;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		AnimalRaisingSymbolNameDto other = (AnimalRaisingSymbolNameDto) obj;
		if(a != other.a) {
			return false;
		}
		if(this.getSymbol() != other.getSymbol()) {
			return false;
		}
		return true;
	}
}
