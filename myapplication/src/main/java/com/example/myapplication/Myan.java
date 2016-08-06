package com.example.myapplication;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class Myan implements Serializable {

	private int i = 0;

	public void say() {
		System.out.println(i++ + " Myoomy " + this.getClass().getName());
	}

}
