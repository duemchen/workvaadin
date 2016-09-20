package de.lichtmagnet.compass;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class CompassReader implements MqttCallback {
	CompassCallback callback;
	private MqttClient client;
	private boolean connectionOK;

	public CompassReader() {
	}

	void register(CompassCallback x) {
		callback = x;
		callback.setPosition("info", "registriert.");

	}

	@Override
	public void connectionLost(Throwable thrwbl) {
		connectionOK = false;
		System.out.println("clost:" + thrwbl);
	}

	@Override
	public void messageArrived(String path, MqttMessage mm) throws Exception {
		if (mm == null) {
			System.out.println("cMessage NULL");
		} else {
			byte[] b = mm.getPayload();
			if (path.contains("cam")) {
				callback.setPicure(mm.getPayload());
			} else
				callback.setPosition(path, new String(b));
		}
		// System.out.println(string + " " + mm);

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken imdt) {
		System.out.println("c delivery");
	}

	public void connectToMQTT() throws InterruptedException {
		Thread.sleep(1000);
		try {
			MemoryPersistence persistence = new MemoryPersistence();
			// jeder client muss eine zufallsid generieren, um stress zu
			// vermeiden
			SecureRandom random = new SecureRandom();
			String id = new BigInteger(60, random).toString(32);
			System.out.println("id=" + id);
			client = new MqttClient("tcp://duemchen.ddns.net:1883", id, persistence);
			client.connect();
			client.setCallback(this);
			// client.subscribe("simago/compass/#");
			// client.subscribe("simago/compass");			   
			client.subscribe("simago/cam");
			client.subscribe("simago/compass/74-DA-38-3E-E8-3C");

			connectionOK = true;
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	boolean isConnected() {
		return connectionOK;
	}

	public void close() {
		try {
			client.disconnect(1000);
		} catch (MqttException e) {

			System.out.println(e);
		}
		try {
			client.close();
		} catch (MqttException e) {
			System.out.println(e);

		}

	}
}
