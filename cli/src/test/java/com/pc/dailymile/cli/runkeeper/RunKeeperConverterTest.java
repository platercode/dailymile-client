package com.pc.dailymile.cli.runkeeper;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import net.sf.practicalxml.ParseUtil;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import au.com.bytecode.opencsv.CSVReader;

import com.pc.dailymile.domain.Workout;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

public class RunKeeperConverterTest {

    @Test
    public void testCreateWorkout() throws Exception {
        String inputFilePath =
            this.getClass().getResource("/com/pc/dailymile/cli/runkeeper/runkeeper.csv")
                    .getFile();
        RunKeeperConverter rkc = new RunKeeperConverter(inputFilePath);

        CSVReader reader = null;
        try {
            
            reader = new CSVReader(new FileReader(inputFilePath));
            List<String[]> lines = reader.readAll();

                
            //first row is headers, so start at 1
            String[] nextLine = lines.get(1);
            
            Workout wo = rkc.createWorkout(nextLine);
            assertEquals(Type.Running, wo.getType());
            assertEquals(1740L, wo.getDuration().longValue());
            assertEquals(Units.kilometers, wo.getDistanceUnits());
            assertEquals("1.17", wo.getDistanceValue());
            
            nextLine = lines.get(2);
            wo = rkc.createWorkout(nextLine);
            assertEquals(Type.Walking, wo.getType());
            assertEquals(1241L, wo.getDuration().longValue());
            assertEquals(Units.kilometers, wo.getDistanceUnits());
            assertEquals("0.96", wo.getDistanceValue());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }
}
