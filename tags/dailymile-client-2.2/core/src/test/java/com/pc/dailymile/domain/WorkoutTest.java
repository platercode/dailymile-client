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
            "{\"felt\":\"good\",\"activity_type\":\"Running\",\"distance\":{\"units\":\"miles\",\"value\":\"2\"}}";
        Workout wo = new Workout();
        wo.setDistanceUnits(Units.miles);
        wo.setDistanceValue("2");
        wo.setFelt(Feeling.good);
        wo.setType(Type.Running);
        
        String actual = GSON.toJson(wo, Workout.class);
        assertEquals(expected, actual);
    }

    @Test
    public void testJsonToWorkout() throws Exception {
        String json =
            "{\"felt\":\"good\",\"activity_type\":\"Running\",\"distance\":{\"units\":\"miles\",\"value\":\"2\"}}";

        Workout wo = GSON.fromJson(json, Workout.class);

        assertEquals(Units.miles, wo.getDistanceUnits());
        assertEquals("2", wo.getDistanceValue());
        assertEquals(Feeling.good, wo.getFelt());
        assertEquals(Type.Running, wo.getType());
    }

    @Test
    public void testJsonToWorkoutWhenTypeIsUnrecognized() throws Exception {
        String json =
            "{\"felt\":\"good\",\"activity_type\":\"Ccskiing\",\"distance\":{\"units\":\"miles\",\"value\":\"2\"}}";

        Workout wo = GSON.fromJson(json, Workout.class);

        assertEquals(Units.miles, wo.getDistanceUnits());
        assertEquals("2", wo.getDistanceValue());
        assertEquals(Feeling.good, wo.getFelt());
        assertEquals(Type.Unknown, wo.getType());
    }
    
    @Test
    public void testJsonToWorkoutWhenFeltIsMissing() throws Exception {
        String json = 
                "{\r\n" + 
        		"  \"activity_type\": \"Cycling\",\r\n" + 
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
