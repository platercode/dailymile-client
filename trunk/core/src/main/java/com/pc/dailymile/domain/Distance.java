package com.pc.dailymile.domain;

import com.pc.dailymile.utils.Units;

/*
 *"distance":{"units":"miles",
 *            "value":3.1}
 *           },
 */
public class Distance {

	private Units units;
	private String value;
	
	public Distance() {}

	public Units getUnits() {
		return units;
	}

	public void setUnits(Units units) {
		this.units = units;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
