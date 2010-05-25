package com.pc.dailymile.cli.nikeplus;

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

public class NikeConverterTest {
		
	@Test
	public void testCreateWorkout() throws Exception {
		String inputFilePath = this.getClass().getResource("/com/pc/dailymile/cli/nikeplus/nike.xml").getFile();
		NikeConverter nc = new NikeConverter(inputFilePath);
		
		InputStream is = null;
		try {
			is = new FileInputStream(inputFilePath);
			Document doc = ParseUtil.parse(new InputSource(is));
			NodeList runs = doc.getElementsByTagName("run");
			//there should only be 2 runs in the test data
			assertEquals(2, runs.getLength());
			//test the parsing of the first one
			Workout wo = nc.createWorkout(runs.item(0));
			
			assertEquals(Type.running, wo.getType());
			assertEquals(1163L, wo.getDuration().longValue());
			assertEquals(Units.kilometers, wo.getDistanceUnits());
			assertEquals("3.4144", wo.getDistanceValue());
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, Calendar.AUGUST);
			cal.set(Calendar.DAY_OF_MONTH, 21);
			cal.set(Calendar.YEAR, 2008);
			cal.set(Calendar.HOUR_OF_DAY, 18);
			cal.set(Calendar.MINUTE, 50);
			cal.set(Calendar.SECOND, 18);
			cal.set(Calendar.MILLISECOND, 0);
			
			assertEquals(cal.getTime(), wo.getCompletedDate());
			
			//test the parsing of the second one
			wo = nc.createWorkout(runs.item(1));
			assertEquals(Type.running, wo.getType());
			assertEquals(1732L, wo.getDuration().longValue());
			assertEquals(Units.kilometers, wo.getDistanceUnits());
			assertEquals("5.1584", wo.getDistanceValue());
			
			cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, Calendar.AUGUST);
			cal.set(Calendar.DAY_OF_MONTH, 22);
			cal.set(Calendar.YEAR, 2008);
			cal.set(Calendar.HOUR_OF_DAY, 18);
			cal.set(Calendar.MINUTE, 02);
			cal.set(Calendar.SECOND, 01);
			cal.set(Calendar.MILLISECOND, 0);
			
			assertEquals(cal.getTime(), wo.getCompletedDate());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					//ignore
				}
			}
		}
	}
}
