package com.example.myapplication;

import java.io.Serializable;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Startup

public class ReportBean implements Serializable {
	Thread thread;

	public ReportBean() {
		runReports();
	}

	public void runReports() {
		System.out.println("runReports");
		ReportTask reportTask = new ReportTask();
		thread = new Thread(reportTask);
		thread.start();

	}

}