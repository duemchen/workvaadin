package de.lichtmagnet.volkszaehler;

import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("de.lichtmagnet.volkszaehler.MyAppWidgetset")
@Title("Volkszähler")
public class VolksUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final BlankForm z = new BlankForm();
		setContent(z);

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = VolksUI.class, productionMode = false)
	public static class MyUIServlet extends TouchKitServlet {
	}
}
