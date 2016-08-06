package com.example.myapplication;

public class ReportTask implements Runnable {

	// Logger logger = Logger.getLogger(getClass().getSimpleName());

	public void run() {
		// logger.log(Level.INFO, "New thread running...");
		try {
			// do your background task
			while (true) {
				Thread.sleep(1000);
				System.out.println("ReportTask");
				break;
			}

		} catch (InterruptedException e) {
			// logger.log(Level.SEVERE, "Thread interrupted", e);
		}
	}
}