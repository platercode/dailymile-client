package com.pc.dailymile.domain;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import com.google.gson.Gson;
import com.pc.dailymile.utils.DailyMileUtil;
import com.pc.dailymile.utils.Feeling;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;
public class WorkoutTest {
	
	/*
	 *           {"felt":"good",
	 *            "type":"running",
	 *            "duration":1645,
	 *            "distance":{"units":"miles",
	 *                        "value":3.1},
	 *            "completed_at":"2010-01-01 00:00:01"
	 *            },
	 */
	
	@Test
	public void testWorkoutCreation() throws Exception {
		String expected = "{\"felt\":\"good\",\"type\":\"running\",\"distance\":{\"units\":\"miles\",\"value\":\"2\"},\"completed_at\":\"2010-01-01 00:00:01\"}";
		Workout wo = new Workout();
		wo.setDistanceUnits(Units.miles);
		wo.setDistanceValue("2");
		wo.setFelt(Feeling.good);
		wo.setType(Type.running);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.YEAR, 2010);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		wo.setCompleted_at(cal.getTime());
		Gson gson = DailyMileUtil.getGson();
		String actual = gson.toJson(wo, Workout.class);	
		assertEquals(expected, actual);
	}
}
