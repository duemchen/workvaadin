package de.lichtmagnet.joy;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import de.horatio.common.HoraFile;

public class JoyForm extends JoyDesign {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	private MqttClient client;

	public JoyForm() {
		compass.setCaption("wartet auf Kompassdaten");
		img.setCaption("Kamerabild wird geladen...");

		top.setCaption("hoch");
		left.setCaption("links");
		right.setCaption("rechts");
		down.setCaption("runter");
		save.setCaption("Speichern");

		top.addClickListener(e -> {
			sendCommand(0);
		});
		left.addClickListener(e -> {
			sendCommand(1);
		});
		right.addClickListener(e -> {
			sendCommand(2);
		});
		down.addClickListener(e -> {
			sendCommand(3);
		});
		save.addClickListener(e -> {
			doSpeichern();
		});
	}

	/**
	 * compass daten mit target versehen und speichern
	 * {"position":{"roll":2,"dir":0,"pitch":-26},"target:":1,"time":"02.04.2016 12:11:00"}
	 */
	private void doSpeichern() {
		JSONObject position = new JSONObject(compass.getValue());
		JSONObject mess = new JSONObject();
		mess.put("position", position);
		mess.put("target", 2);
		mess.put("time", sdf.format(new Date()));
		System.out.println("speichere: " + mess);

		String s = mess.toString();
		// "simago/compass/abc"
		String p = compass.getCaption();
		int i = p.lastIndexOf("/");
		p = p.substring(i + 1);
		System.out.println(p);
		if ("compass".equals(p)) {
			p = "logfile";
		}
		p = "D:/Programme/MQTTRegler/" + p + ".txt";
		HoraFile.fileAppend(p, s);
		// report.logMesspunkt(mess);
	}

	void sendCommand(int cmd) {
		try {
			// TODO Format json
			JSONObject jo = new JSONObject();
			jo.put("cmd", cmd);
			MqttMessage message = new MqttMessage();
			message.setPayload(jo.toString().getBytes());
			// message.setPayload("JoyIt!".getBytes());
			try {
				if (client == null) {
					MemoryPersistence persistence = new MemoryPersistence();
					SecureRandom random = new SecureRandom();
					String id = "joy-" + new BigInteger(60, random).toString(32);
					System.out.println("id=" + id);
					client = new MqttClient("tcp://duemchen.ddns.net:1883", id, persistence);
				}
				if (!client.isConnected()) {
					client.connect();
				}
				//
				String p = compass.getCaption();
				int i = p.lastIndexOf("/");
				p = p.substring(i + 1);
				System.out.println(p);
				if ("compass".equals(p)) {
					p = "";

				} else {
					p = "/" + p;

				}
				client.publish("simago/joy" + p, message);
				//client.publish("simago/joy", message);
				//client.publish("simago/joy/74-DA-38-3E-E8-3C", message);

			} catch (MqttException ex) {
				System.out.println(ex);

			}

		} catch (JSONException ex) {
			System.out.println(ex);
		}

	}

	public void setCompass(String cap, String val) {
		compass.setCaption(cap);
		compass.setValue(val);

	}

}
