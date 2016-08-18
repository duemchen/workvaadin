package com.example.myapplication;

import java.io.Serializable;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

import org.apache.log4j.Logger;
import org.json.JSONObject;

@ApplicationScoped
@Startup

public class ReportBean implements Serializable {
	private static Logger logMess = Logger.getLogger("MessLogger");

	public ReportBean() {

	}

	public void logMesspunkt(JSONObject json) {
		String s = json.toString();
		logMess.info(s);
	}

}