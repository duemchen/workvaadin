package com.example.myapplication;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LD_Zustand")
public class LDZustand {

	
	@Id
	@Column(name="U_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long U_id;
	@Column(name = "Parameter")
	private String parameter;
	@Column(name = "Value")
	private String value;
	@Column(name = "LastAccessed")
	private Timestamp lastAccessed;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Timestamp getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Timestamp lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	public String toString(){
		return this.U_id+"."+this.getValue();
	}

}