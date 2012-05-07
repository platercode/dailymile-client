package com.pc.dailymile.cli.runkeeper;

import java.io.FileReader;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.format.DateTimeFormat;

import au.com.bytecode.opencsv.CSVReader;

import com.pc.dailymile.DailyMileClient;
import com.pc.dailymile.cli.Converter;
import com.pc.dailymile.domain.Workout;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

/**
 * Date,Type,Route Name,Distance (km),Duration,Average Pace,Average Speed (km/h),Calories Burned,Climb (m),Average Heart Rate (bpm),Notes,GPX File
 * 2011-01-14 08:09:38,Running,,0.00,0:32:42,,,49.0,0.00,,"Rkp update is even more broken than before on iPhone 3G!!! Have a look at my map!",2011-01-14-0809.gpx
 * 
 * @author jplater
 * 
 */
public class RunKeeperConverter extends Converter {

    private enum RunKeeperField {
        DATE(0),
        TYPE(1),
        ROUTE_NAME(2),
        DISTANCE(3),
        DURATION(4),
        NOTE(10);
        
        private int _fieldIndex;
        
        private RunKeeperField(int fieldIndex) {
            _fieldIndex = fieldIndex;
        }
        
        public int getFieldIndex() {
            return _fieldIndex;
        }
    }
    
    
    private enum RunKeeperType {
        Running(Type.Running),
        Walking(Type.Walking);

        private Type _type;

        private RunKeeperType(Type type) {
            _type = type;
        }

        public Type getType() {
            return _type;
        }
    }

    public RunKeeperConverter(String inputFilePath) {
        super(inputFilePath);
    }

    @Override
    public long doConversion(DailyMileClient client) {
        long migrated = 0;
        CSVReader reader = null;
        
        try {
            reader = new CSVReader(new FileReader(getInputFilePath()));
            String [] nextLine = reader.readNext();
           
            //skip the first line, it is a header row
            if (nextLine == null) {
                return 0;
            }
            
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                Workout wo = createWorkout(nextLine);
                client.addWorkout(wo, nextLine[RunKeeperField.NOTE.getFieldIndex()]);
                migrated++;
            }
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

        return migrated;
    }

    // scoped for testing
    protected Workout createWorkout(String[] entry) {
        Workout wo = new Workout();
        wo.setType(parseWorkoutType(entry[RunKeeperField.TYPE.getFieldIndex()]));

        wo.setDuration(parseDuration(entry[RunKeeperField.DURATION.getFieldIndex()]));
        // only supporting km currently
        wo.setDistanceUnits(Units.kilometers);
        wo.setDistanceValue(entry[RunKeeperField.DISTANCE.getFieldIndex()]);
        wo.setCompletedDate(parseCompletionDate(entry[RunKeeperField.DATE.getFieldIndex()]));

        return wo;
    }

    private Type parseWorkoutType(String type) {
        try {
            return RunKeeperType.valueOf(type.replaceAll("\\s", "")).getType();
        } catch (Exception e) {
            // ignore
        }
        return Type.Unknown;
    }


    private Date parseCompletionDate(String date) {
        // 2011-01-14 08:09:38
        Date rtn = null;
        try {
            rtn = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(date).toDate();
        } catch (Exception e) {
            // unable to parse date
        }

        return rtn;
    }
    
    private static long parseDuration(String time) {     
        String[] parts = time.split(":");
        
        if (parts.length == 3) {
            return toSeconds(parts[0], parts[1], parts[2]);
        }
        
        if (parts.length == 2) {
            return toSeconds("", parts[0], parts[1]);
        }
        
        if (parts.length == 1) {
            return toSeconds("", "", parts[0]);
        }
        
        return 0L;
    }
    
    private static long toSeconds(String hours, String minutes, String seconds) {
        long hr = NumberUtils.toLong(hours, 0);
        long min = NumberUtils.toLong(minutes, 0);
        long sec = NumberUtils.toLong(seconds, 0);
        
        return sec + (min * 60) + (hr * 3600);
    }

}
