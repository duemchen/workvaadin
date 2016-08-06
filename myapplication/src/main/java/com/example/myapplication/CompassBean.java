package com.example.myapplication;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Startup
public class CompassBean implements Serializable {
	private CompassConnectorThread cct;

	public CompassBean() {
	}

	@PostConstruct
	public void init() {
		System.out.println("CompassBean init");
		cct = new CompassConnectorThread();
		cct.start();
	}

	@PreDestroy
	public void close() {
		System.out.println("CompassBean interrupt");
		cct.interrupt();

	}

	public void register(CompassCallback compassCallback) {
		System.out.println("CompassBean register");
		cct.register(compassCallback);

	}

	public void unregister() {
		System.out.println("CompassBean un-register");
		cct.register(null);

	}

}
