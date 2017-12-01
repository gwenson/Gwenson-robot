package com.gwenson.robot.page.dto;

import java.util.Set;

public class DepthUrlsDTO {
	
	private Set<String> depth;
	
	private Set<String> wide;

	
	public DepthUrlsDTO() {
		
	}

	public DepthUrlsDTO(Set<String> depth, Set<String> wide) {
		this.depth = depth;
		this.wide = wide;
	}

	public Set<String> getDepth() {
		return depth;
	}

	public void setDepth(Set<String> depth) {
		this.depth = depth;
	}

	public Set<String> getWide() {
		return wide;
	}

	public void setWide(Set<String> wide) {
		this.wide = wide;
	}
	
	
}
