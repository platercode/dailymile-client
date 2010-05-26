package com.pc.dailymile.domain;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gson.Gson;
import com.pc.dailymile.utils.DailyMileUtil;
import com.pc.dailymile.utils.Feeling;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

public class WorkoutTest {
    
    private static final Gson GSON = DailyMileUtil.getGson();

    @Test
    public void testWorkoutToJson() throws Exception {
        String expected =
            "{\"felt\":\"good\",\"type\":\"running\",\"distance\":{\"units\":\"miles\",\"value\":\"2\"},"
                +
                // TODO - verify this change
                // "\"completed_at\":\"2010-01-01T00:00:01-05:00\"}";
                "\"completed_at\":\"2010-01-01 00:00:01\"}";
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
        wo.setCompletedDate(cal.getTime());
        
        String actual = GSON.toJson(wo, Workout.class);
        assertEquals(expected, actual);
    }

    @Test
    public void testJsonToWorkout() throws Exception {
        String json =
            "{\"felt\":\"good\",\"type\":\"running\",\"distance\":{\"units\":\"miles\",\"value\":\"2\"},"
                + "\"completed_at\":\"2010-01-01T00:00:01-05:00\"}";

        Workout wo = GSON.fromJson(json, Workout.class);

        assertEquals(Units.miles, wo.getDistanceUnits());
        assertEquals("2", wo.getDistanceValue());
        assertEquals(Feeling.good, wo.getFelt());
        assertEquals(Type.running, wo.getType());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.YEAR, 2010);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        cal.set(Calendar.MILLISECOND, 0);

        assertEquals(cal.getTime(), wo.getCompletedDate());
    }

    @Test
    public void testJsonToWorkoutWhenTypeIsUnrecognized() throws Exception {
        String json =
            "{\"felt\":\"good\",\"type\":\"ccskiing\",\"distance\":{\"units\":\"miles\",\"value\":\"2\"},"
                + "\"completed_at\":\"2010-01-01T00:00:01-05:00\"}";

        Workout wo = GSON.fromJson(json, Workout.class);

        assertEquals(Units.miles, wo.getDistanceUnits());
        assertEquals("2", wo.getDistanceValue());
        assertEquals(Feeling.good, wo.getFelt());
        assertEquals(Type.unknown, wo.getType());
    }
    
    @Test
    public void testJsonToWorkoutWhenFeltIsMissing() throws Exception {
        String json = 
                "{\r\n" + 
        		"  \"type\": \"cycling\",\r\n" + 
        		"  \"duration\": 7800,\r\n" + 
        		"  \"distance\": {\r\n" + 
        		"    \"units\": \"miles\",\r\n" + 
        		"    \"value\": 39.2\r\n" + 
        		"   }\r\n" + 
        		"}";
        
        Workout w = GSON.fromJson(json, Workout.class);
        
        Assert.assertNull(w.getFelt());
    }
}
