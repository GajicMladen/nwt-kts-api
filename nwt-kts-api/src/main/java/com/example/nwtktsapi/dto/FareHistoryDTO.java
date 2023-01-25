package com.example.nwtktsapi.dto;

import java.util.List;

public class FareHistoryDTO {

	private List<FareDTO> fares;
	private long count;
	
	public FareHistoryDTO() {}

	public FareHistoryDTO(List<FareDTO> fares, long count) {
		this.fares = fares;
		this.count = count;
	}
	
	public List<FareDTO> getFares() {
		return fares;
	}
	public void setFares(List<FareDTO> fares) {
		this.fares = fares;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
}
