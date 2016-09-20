package de.lichtmagnet.volkszaehler;

import com.vaadin.ui.CustomComponent;

public class ZForm extends CustomComponent {
	protected BlankDesign design = new BlankDesign();

	public ZForm() {
		Zaehler zaehler = new Zaehler();

		setWidth(design.getWidth(), design.getWidthUnits());
		setCompositionRoot(design);
		design.verbrauch.setValue("hello");

	}

}
