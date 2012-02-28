package com.pc.dailymile.cli.runningahead;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;

import net.sf.practicalxml.ParseUtil;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.pc.dailymile.domain.Workout;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

public class RunningAheadConverterTest {

    @Test
    public void testCreateWorkout() throws Exception {
        String inputFilePath =
            this.getClass().getResource("/com/pc/dailymile/cli/runningahead/runningahead.xml")
                    .getFile();
        RunningAheadConverter nc = new RunningAheadConverter(inputFilePath);

        InputStream is = null;
        try {
            is = new FileInputStream(inputFilePath);
            Document doc = ParseUtil.parse(new InputSource(is));
            NodeList events = doc.getElementsByTagName("Event");
            // there should only be 8 events in the test data
            assertEquals(8, events.getLength());
            // test the parsing of the first one
            Workout wo = nc.createWorkout(events.item(0));

            assertEquals(Type.Running, wo.getType());
            assertEquals(1901L, wo.getDuration().longValue());
            assertEquals(Units.miles, wo.getDistanceUnits());
            assertEquals("3.46", wo.getDistanceValue());

            // 2008-04-28T10:27:36Z
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Calendar.APRIL);
            cal.set(Calendar.DAY_OF_MONTH, 28);
            cal.set(Calendar.YEAR, 2008);
            cal.set(Calendar.HOUR_OF_DAY, 6);
            cal.set(Calendar.MINUTE, 27);
            cal.set(Calendar.SECOND, 36);
            cal.set(Calendar.MILLISECOND, 0);

            assertEquals(cal.getTime(), wo.getCompletedDate());

            // test the parsing of the second one
            wo = nc.createWorkout(events.item(1));
            assertEquals(Type.Yoga, wo.getType());
            assertEquals(5400L, wo.getDuration().longValue());

            wo = nc.createWorkout(events.item(2));
            assertEquals(Type.Cycling, wo.getType());
            assertEquals(3600L, wo.getDuration().longValue());
            assertEquals(Units.miles, wo.getDistanceUnits());
            assertEquals("20.80", wo.getDistanceValue());
            
            wo = nc.createWorkout(events.item(3));
            assertEquals(Type.Fitness, wo.getType());
            assertEquals(4200L, wo.getDuration().longValue());
            
            wo = nc.createWorkout(events.item(4));
            assertEquals(Type.Walking, wo.getType());
            assertEquals(6351L, wo.getDuration().longValue());
            assertEquals(Units.miles, wo.getDistanceUnits());
            assertEquals("3.75", wo.getDistanceValue());
            
            wo = nc.createWorkout(events.item(5));
            assertEquals(Type.Weights, wo.getType());
            assertEquals(3600L, wo.getDuration().longValue());
            
            wo = nc.createWorkout(events.item(6));
            assertEquals(Type.CrossTraining, wo.getType());
            assertEquals(7200L, wo.getDuration().longValue());
            
            wo = nc.createWorkout(events.item(7));
            assertEquals(Type.Hiking, wo.getType());
            assertEquals(11400L, wo.getDuration().longValue());
            assertEquals(Units.miles, wo.getDistanceUnits());
            assertEquals("5.97", wo.getDistanceValue());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }
}
