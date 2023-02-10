package com.globits.wl.dto.functiondto;

import java.util.ArrayList;
import java.util.List;

public class ImportResultDto<T> {
	private int totalRow=0;
	private int totalErr=0;
	private int totalSuccess=0;
	private List<T> listErr = new ArrayList<T>();
	
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	public int getTotalErr() {
		if(listErr!=null) {
			totalErr=listErr.size();
		}
		return totalErr;
	}
	public void setTotalErr(int totalErr) {
		this.totalErr = totalErr;
	}
	public int getTotalSuccess() {
		return totalSuccess;
	}
	public void setTotalSuccess(int totalSuccess) {
		this.totalSuccess = totalSuccess;
	}
	public List<T> getListErr() {
		return listErr;
	}
	public void setListErr(List<T> listErr) {
		this.listErr = listErr;
	}
	
	
}
