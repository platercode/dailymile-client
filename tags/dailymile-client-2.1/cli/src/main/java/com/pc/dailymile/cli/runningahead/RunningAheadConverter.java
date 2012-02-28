package com.pc.dailymile.cli.runningahead;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sf.practicalxml.DomUtil;
import net.sf.practicalxml.ParseUtil;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.ISODateTimeFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.pc.dailymile.DailyMileClient;
import com.pc.dailymile.cli.Converter;
import com.pc.dailymile.domain.Workout;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

/**
 * @author jplater
 * 
 */
public class RunningAheadConverter extends Converter {

    private enum RunningAheadType {
        Run(Type.Running),
        Yoga(Type.Yoga),
        Bike(Type.Cycling),
        Gymworkout(Type.Fitness),
        Walking(Type.Walking),
        Weights(Type.Weights),
        Cardioworkout(Type.CrossTraining),
        Hiking(Type.Hiking);

        private Type _type;

        private RunningAheadType(Type type) {
            _type = type;
        }

        public Type getType() {
            return _type;
        }
    }

    public RunningAheadConverter(String inputFilePath) {
        super(inputFilePath);
    }

    @Override
    public long doConversion(DailyMileClient client) {
        long migrated = 0;
        InputStream is = null;
        Set<String> tmp = new HashSet<String>();
        try {
            is = new FileInputStream(getInputFilePath());
            Document doc = ParseUtil.parse(new InputSource(is));

            NodeList runs = doc.getElementsByTagName("Event");
            for (int i = 0; i < runs.getLength(); i++) {
                Node event = runs.item(i);
                Workout wo = createWorkout(runs.item(i));
                client.addWorkout(wo, parseNotes(event));

                migrated++;
            }
            for (String foo : tmp) {
                System.out.println(foo);
            }

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

        return migrated;
    }

    // scoped for testing
    protected Workout createWorkout(Node event) {
        Workout wo = new Workout();
        wo.setType(parseWorkoutType((Element)event));

        wo.setDuration(parseDuration(event));
        // only supporting miles currently
        wo.setDistanceUnits(Units.miles);
        wo.setDistanceValue(parseDistance(event));
        wo.setCompletedDate(parseCompletionDate((Element) event));

        return wo;
    }

    private Type parseWorkoutType(Element event) {
        String type = event.getAttribute("typeName");
        try {
            return RunningAheadType.valueOf(type.replaceAll("\\s", "")).getType();
        } catch (Exception e) {
            // ignore
        }
        return Type.Unknown;
    }

    private long parseDuration(Node run) {
        long dur = 0;
        Element durEl = DomUtil.getChild(run, "Duration");
        if (durEl != null) {
            dur = (long) Double.parseDouble(durEl.getAttribute("seconds"));
        }
        return dur;
    }

    private String parseDistance(Node event) {
        String dist = "";
        Element distEl = DomUtil.getChild(event, "Distance");
        if (distEl != null && StringUtils.isNotBlank(distEl.getTextContent())) {
            dist = distEl.getTextContent();
        }
        return dist;
    }
    
    private String parseNotes(Node event) {
        String note = "";
        Element noteEl = DomUtil.getChild(event, "Notes");
        if (noteEl != null && StringUtils.isNotBlank(noteEl.getTextContent())) {
            note = noteEl.getTextContent();
        }
        return note;
    }

    private Date parseCompletionDate(Element event) {
        // 2008-04-29T20:29:03Z
        Date rtn = null;
        try {
            rtn = ISODateTimeFormat.dateTimeNoMillis().parseDateTime(event.getAttribute("time")).toDate();
        } catch (Exception e) {
            // unable to parse date
        }

        return rtn;
    }

}
