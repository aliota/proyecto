package com.ae.proyecto.impl;

import com.ae.proyecto.Size;

public class SizeImpl implements Size {
	private Integer width, high, diameter;
	
	public SizeImpl(Integer width, Integer high) {	
		this.width = width;
		this.high = high;
		this.diameter = 0;
	}
	
	public SizeImpl(Integer diameter) {	
		this.width = 0;
		this.high = 0;
		this.diameter = diameter;
	}
	
	@Override
	public Integer getWidth() {
		return width;
	}

	@Override
	public Integer getHigh() {
		return high;
	}

	@Override
	public Integer getDiameter() {
		return diameter;
	}

	public String toString() {
		return String.format("w=%s, h=%s, d=%s", this.width, this.high, this.diameter);
	}
}
