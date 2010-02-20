package com.pc.dailymile.domain;

import java.util.Date;

import com.pc.dailymile.utils.Feeling;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

/*
 *           {"felt":"good",
 *            "type":"running",
 *            "duration":1645,
 *            "distance":{"units":"miles",
 *                        "value":3.1},
 *            "completed_at":"2010-01-01 00:00:01"
 *            },
 */
public class Workout {

	private Feeling felt;
	private Type type;
	private Long duration;
	private Distance distance;
	private Date completed_at;

	public Workout() {
		this.distance = new Distance();
	}

	public Feeling getFelt() {
		return felt;
	}

	public void setFelt(Feeling felt) {
		this.felt = felt;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public void setDistanceUnits(Units units) {
		distance.setUnits(units);
	}
	
	public Units getDistanceUnits() {
		return distance.getUnits();
	}
	
	public void setDistanceValue(String value) {
		distance.setValue(value);
	}
	
	public String getDistanceValue(){
		return distance.getValue();
	}
	
	public Date getCompleted_at() {
		//2010-12-25 12:15:00 
		return completed_at;
	}

	public void setCompleted_at(Date completedAt) {
		completed_at = completedAt;
	}
}
