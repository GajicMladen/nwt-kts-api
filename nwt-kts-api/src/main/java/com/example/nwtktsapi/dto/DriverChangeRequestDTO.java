package com.example.nwtktsapi.dto;

import java.util.List;

import com.example.nwtktsapi.model.DriverChangeRequest;

public class DriverChangeRequestDTO {
	private long count;
	private List<DriverChangeRequest> res;
	
	public DriverChangeRequestDTO() {}
	
	public DriverChangeRequestDTO(long count, List<DriverChangeRequest> res) {
		this.count = count;
		this.res = res;
	}
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<DriverChangeRequest> getRes() {
		return res;
	}
	public void setRes(List<DriverChangeRequest> res) {
		this.res = res;
	}
	
	
}
