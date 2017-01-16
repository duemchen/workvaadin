package de.lichtmagnet.strom;

import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.Title;

public class StromForm extends StromDesign {

	public StromForm() {
		Zaehler zaehler = new Zaehler();
		verbrauch.setValue(zaehler.getVerbrauch());
		zeit.setValue(zaehler.getZeitstempel());
		chartTag.setHeight("240px");
		chartTag.setResponsive(true);
		Configuration conf = chartTag.getConfiguration();
		conf.setTitle(new Title("Tagesverlauf"));
		conf.getChart().setType(ChartType.AREA);
		conf.getyAxis().setTitle("Leistung");
		conf.getxAxis().setTitle("Uhrzeit");
		conf.getxAxis().setType(AxisType.DATETIME);
		DataSeries leistung = zaehler.getTagesverlauf(null);
		conf.addSeries(leistung);
		setSizeFull();

	}

}
