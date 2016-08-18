package de.lichtmagnet.joystick;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
@Theme("mytheme")
@Widgetset("de.lichtmagnet.joystick.MyAppWidgetset")
@Title("Joystick-4-Mirror")
@Viewport("user-scalable=yes,initial-scale=1.0")
public class JoyUI extends UI implements CompassCallback {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	@Inject
	private CompassBean cb;
	private MqttClient client;
	private TextField compass;
	private Button links;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		compass = new TextField();
		compass.setCaption("Lage");
		compass.setWidth(500, Unit.PIXELS);
		layout.addComponents(compass);

		links = new Button("sss");
		links.setVisible(true);
		links.setCaption("ddd");

		Button rechts = new Button("rechts");
		Button hoch = new Button("hoch");
		Button runter = new Button("runter");
		Button speichern = new Button("Speichern");
		layout.addComponent(hoch);
		final HorizontalLayout hori = new HorizontalLayout();
		hori.setMargin(true);
		hori.setSpacing(true);
		hori.addComponent((Component) links);
		hori.addComponent(rechts);
		layout.addComponents(hori, runter);
		hori.addComponent(speichern);
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
		cb.register((CompassCallback) this);

		links.addClickListener(e -> {
			sendCommand(1);
		});
		rechts.addClickListener(e -> {
			sendCommand(2);
		});
		hoch.addClickListener(e -> {
			sendCommand(0);
		});
		runter.addClickListener(e -> {
			sendCommand(3);
		});
		speichern.addClickListener(e -> {
			doSpeichern();
		});

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
		// report.logMesspunkt(mess);
	}

	@Override
	public void setPosition(String s) {
		// System.out.println(s);

		access(new Runnable() {
			@Override
			public void run() {

				compass.setValue(s);
				// ));
			}
		});

	}

	// @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported =
	// true)
	@VaadinServletConfiguration(ui = JoyUI.class, productionMode = false)
	public static class MyUIServlet extends TouchKitServlet {
	}
}
