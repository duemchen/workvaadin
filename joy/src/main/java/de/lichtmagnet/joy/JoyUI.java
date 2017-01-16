package de.lichtmagnet.joy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import de.lichtmagnet.compass.CompassBean;
import de.lichtmagnet.compass.CompassCallback;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Push
@CDIUI("")
@Theme("mytheme")
@Title("Joy")
@Viewport("user-scalable=no,initial-scale=1.0")
public class JoyUI extends UI implements CompassCallback {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	@Inject
	private CompassBean cb;
	JoyForm jf;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		jf = new JoyForm();
		setContent(jf);
		setSizeFull();
		cb.register((CompassCallback) this);
		jf.sw.setValue(false);
		cb.setSwitch(jf.sw);
		jf.sw.addValueChangeListener(e -> {
			System.out.println(jf.sw.getValue());
		});

	}

	@Override
	public void setPosition(String path, String s) {
		//	System.out.println(s);
		new UIUpdater(new Runnable() {

			@Override
			public void run() {
				jf.setCompass(path, s);//);+ " " + sdf.format(new Date()), path);
			}

		});

	}

	@Override
	public void setPicure(byte[] payload) {

		//System.out.println("Bild: " + payload.length);
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
					jf.img.setSource(resource);
					jf.img.setAlternateText("???");
					jf.img.setCaption(sdf.format(new Date()));
					jf.p.setCaption("Kamerabild " + sdf.format(new Date()));

					// ));
				}
			});

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	//	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = JoyUI.class, productionMode = false)
	public static class MyUIServlet extends TouchKitServlet {
	}
}
