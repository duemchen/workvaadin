package de.lichtmagnet.volkszaehler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;

import de.horatio.common.HoraFile;

public class Zaehler {
	ArrayList<String> liste = null;

	public Zaehler() {
		String datei = "D:/Programme/MQTTSammler/log/elektro";
		if (HoraFile.fileExists(datei)) {
			liste = new ArrayList<String>();
			HoraFile.fillDateiToArrayList(datei, liste);
		}
	}

	public String getVerbrauch() {
		if (liste != null) {
			String s = liste.get(liste.size() - 1);
			JSONObject o = new JSONObject(s);
			// {"time":"11.08.2016
			// 15:25:56","delta":99483,"watt":482,"countTrue":"241.00","countFalse":"44.00"}

			return "" + o.getInt("watt") + " W";
		}
		return "keine Daten";

	}

	public String getZeitstempel() {
		if (liste != null) {
			String s = liste.get(liste.size() - 1);
			JSONObject o = new JSONObject(s);
			return "" + o.getString("time");

		}
		return "";
	}

	public DataSeries getTagesverlauf(Object object) {
		// {"time":"11.08.2016
		// 15:25:56","delta":99483,"watt":482,"countTrue":"241.00","countFalse":"44.00"}
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		DataSeries result = new DataSeries("Leistung (W)");
		for (String s : liste) {
			try {
				JSONObject o = new JSONObject(s);
				int watt = o.getInt("watt");
				String stime = o.getString("time");
				Date time = sdf.parse(stime);
				result.add(new DataSeriesItem(time, watt));
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}
		return result;
	}

}
