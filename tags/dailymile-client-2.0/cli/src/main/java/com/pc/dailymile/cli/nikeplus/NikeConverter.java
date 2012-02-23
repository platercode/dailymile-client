package com.pc.dailymile.cli.nikeplus;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.practicalxml.DomUtil;
import net.sf.practicalxml.ParseUtil;

import org.apache.commons.lang.StringUtils;
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

public class NikeConverter extends Converter {

	public NikeConverter(String inputFilePath) {
		super(inputFilePath);
	}
	
	@Override
	public long doConversion(DailyMileClient client) {
		long migrated = 0;
		InputStream is = null;
		try {
			is = new FileInputStream(getInputFilePath());
			Document doc = ParseUtil.parse(new InputSource(is));
			NodeList runs = doc.getElementsByTagName("run");
			for (int i = 0; i < runs.getLength(); i++) {
				Workout wo = createWorkout(runs.item(i));
				client.addWorkout(wo, "Migrated from Nike+");
				migrated++;
			}
			
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
		
		return migrated;
	}
	
	//scoped for testing
	protected Workout createWorkout(Node run) {
		Workout wo = new Workout();
		//only support running
		wo.setType(Type.Running);
		wo.setDuration(parseDuration(run));
		//nike plus keeps dist in kilometers
		wo.setDistanceUnits(Units.kilometers);
		wo.setDistanceValue(parseDistance(run));
		wo.setCompletedDate(parseCompletionDate(run));

		return wo;
	}
	
	private long parseDuration(Node run) {
		long dur = 0;
		Element durEl = DomUtil.getChild(run, "duration");
		if (durEl != null && StringUtils.isNotBlank(durEl.getTextContent())) {
			dur = Long.parseLong(durEl.getTextContent());
			//time is in ms, convert to seconds
			dur = dur / 1000;
		}
		return dur;
	}
	
	private String parseDistance(Node run) {
		String dist = "";
		Element distEl = DomUtil.getChild(run, "distance");
		if (distEl != null && StringUtils.isNotBlank(distEl.getTextContent())) {
			dist = distEl.getTextContent();
		}
		return dist;
	}
	
	private Date parseCompletionDate(Node run) {
		//2008-08-21T18:50:18
		Date rtn = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Element dateEl = DomUtil.getChild(run, "startTime");
		if (dateEl != null && StringUtils.isNotBlank(dateEl.getTextContent())) {
			try {
				rtn = sdf.parse(dateEl.getTextContent());
			} catch (ParseException e) {
				//unable to parse date
			}
		}
		
		return rtn;
	}
	
}
