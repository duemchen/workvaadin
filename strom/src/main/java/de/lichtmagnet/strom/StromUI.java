package de.lichtmagnet.strom;

import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
//@Widgetset("de.lichtmagnet.strom.AppWidgetset")
@Theme("mytheme")
@Title("Stromzähler")
public class StromUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		//		final VerticalLayout layout = new VerticalLayout();
		//
		//		final TextField name = new TextField();
		//		name.setCaption("Type your name here:");
		//
		//		Button button = new Button("Click Me");
		//		button.addClickListener(e -> {
		//			layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
		//		});
		//
		//		layout.addComponents(name, button);
		//		layout.setMargin(true);
		//		layout.setSpacing(true);
		//		setContent(layout);
		final StromForm sf = new StromForm();
		sf.setSizeFull();
		setContent(sf);
		setSizeFull();

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = StromUI.class, productionMode = false)
	public static class MyUIServlet extends TouchKitServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3038611375194502376L;
	}
}
