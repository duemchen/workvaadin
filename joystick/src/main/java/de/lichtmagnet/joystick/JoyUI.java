package de.lichtmagnet.joystick;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import de.horatio.common.HoraFile;
import de.lichtmagnet.compass.CompassBean;
import de.lichtmagnet.compass.CompassCallback;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Push
@CDIUI("")
@Theme("touchkit")
@Widgetset("de.lichtmagnet.joystick.MyAppWidgetset")
@Title("Spiegel einstellen")
// nichtinvolksz@Viewport("user-scalable=yes,initial-scale=1.0")
public class JoyUI extends UI implements CompassCallback {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	@Inject
	private CompassBean cb;
	private MqttClient client;
	private TextField compass;

	private Button links;
	private Image img;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final JoyForm z = new JoyForm();
		setContent(z);
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
				client.publish("simago/joy/74-DA-38-3E-E8-3C", message);
				// client.publish("simago/joy", message);

			} catch (MqttException ex) {
				System.out.println(ex);

			}

		} catch (JSONException ex) {
			System.out.println(ex);
		}

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
		p = "D:/Programme/MQTTRegler/" + p + ".txt";
		HoraFile.fileAppend(p, s);
		// report.logMesspunkt(mess);
	}

	@Override
	public void setPosition(String path, String s) {
		// System.out.println(s);

		access(new Runnable() {
			@Override
			public void run() {

				compass.setValue(s);
				compass.setCaption(path);
				// ));
			}
		});

	}

	// @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported =
	// true)
	@VaadinServletConfiguration(ui = JoyUI.class, productionMode = true)
	public static class MyUIServlet extends TouchKitServlet {
	}

	@Override
	public void setPicure(byte[] payload) {
		System.out.println("Bild: " + payload.length);
		try {

			InputStream in = new ByteArrayInputStream(payload);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			//	bImageFromConvert = (BufferedImage) bImageFromConvert.getScaledInstance(200, -1, 0);

			//ImageIO.write(bImageFromConvert, "jpg", new File("d:/new-darksouls.jpg"));

			access(new Runnable() {
				@Override
				public void run() {

					StreamResource.StreamSource imagesource = new MyImageSource(bImageFromConvert);

					// Create a resource that uses the stream source and give it a name.
					// The constructor will automatically register the resource in
					// the application.
					StreamResource resource = new StreamResource(imagesource, System.currentTimeMillis() + ".jpg");

					// Create an image component that gets its contents
					// from the resource.
					img.setSource(resource);
					img.setAlternateText("????");
					img.setCaption(sdf.format(new Date()));

					// ));
				}
			});

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}
}
