package com.pc.dailymile.cli;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.pc.dailymile.DailyMileClient;
import com.pc.dailymile.auth.DailyMileAuthenticator;
import com.pc.dailymile.cli.nikeplus.NikeConverter;

public class DailyMileCli {

	public static void main(String[] args) throws Exception {
		System.out.println("");
		
		DailyMileClient dmc  = null;
		
		if (args.length > 0) {
			dmc = configureDMVFromPropsFile(args[0]);
		} else {
			dmc = configureDMCFromPrompts();
		}
		
		String converter = getUserInput("Please enter the converison type, allowed types: [nikeplus]");
		
		String inputFilePath = getUserInput("Please enter full path to input file:");
		
		if (converter.equalsIgnoreCase("nikeplus")) {
			NikeConverter nike = new NikeConverter(inputFilePath);
			long total = nike.doConversion(dmc);
			System.out.println("Converted " + total + " runs");
		} else {
			System.out.println("unknown conversion requested");
			System.exit(1);
		}
		

	}
	
	private static DailyMileClient configureDMVFromPropsFile(String propsFile) throws Exception {
		Properties props = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(propsFile);
			props.load(is);
		} catch (Exception e) {
			System.out.println("Unable to load props file");
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					//ignore
				}
			}
		}
				
		return new DailyMileClient(props.getProperty("oauthToken"));
	}
	
	private static DailyMileClient configureDMCFromPrompts() throws Exception {
		String clientId = getUserInput("Please enter your client id:");
		String redirect = getUserInput("Please enter your redirect url:");
		
		System.out.println("Please open a brower and go to:" + DailyMileAuthenticator.buildAuthorizeUrl(clientId, redirect));
		
		String oauthToken = getUserInput("Please enter your oauth token:");
		
		return new DailyMileClient(oauthToken);
	}
	
	private static String getUserInput(String message) {
		BufferedReader br = null;
		System.out.println(message);
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			return br.readLine();
		} catch (IOException ioe) {
			System.out.println("Unable to understand your response");
			System.exit(1);
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					//ignore
//				}
//			}
		}
		
		return "";
	}
	
}
