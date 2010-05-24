package com.pc.dailymile.cli;

import com.pc.dailymile.DailyMileClient;

public abstract class Converter {

	private String inputFilePath;
	
	public Converter(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	
	/**
	 * Perform the conversion
	 * 
	 * @return the number of workouts converted
	 */
	public abstract long doConversion(DailyMileClient client);
	
	protected String getInputFilePath() {
		return inputFilePath;
	}
	
}
