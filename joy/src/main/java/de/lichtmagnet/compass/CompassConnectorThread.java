package de.lichtmagnet.compass;

import com.vaadin.addon.touchkit.ui.Switch;

public class CompassConnectorThread extends Thread implements CompassCallback {
	CompassCallback callback = null;
	private CompassReader tr;
	private Switch sw;

	public CompassConnectorThread() {

	}

	public void register(CompassCallback x) {
		callback = x;
		// x.setPosition("waiting for Compass...");

	}

	@Override
	public void run() {

		while (!isInterrupted()) {
			boolean b = true;
			if (sw != null)
				b = sw.getValue();
			tr = new CompassReader(b);

			tr.register(this);
			try {
				tr.connectToMQTT();

			} catch (InterruptedException e1) {
				interrupt();
				System.out.println("Unterbrechung in sleep()0");
				break;
			}
			boolean bb = true;
			if (sw != null)
				bb = sw.getValue();
			while (tr.isConnected()) {
				if (sw != null) {
					if (bb != sw.getValue()) {
						System.out.println("Wechsel");
						tr.close();
						break;
					}
				}
				if (isInterrupted())
					break;
				try {
					Thread.sleep(2000);

				} catch (InterruptedException e) {
					interrupt();
					System.out.println("Unterbrechung in sleep()1");
					break;

				}
			}
			if (isInterrupted())
				break;
			System.out.println("restart");

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				{
					interrupt();
					System.out.println("Unterbrechung in sleep()2");
					break;

				}

			}
		}
		tr.close();

	}

	@Override
	public void setPosition(String p, String s) {
		if (callback != null)
			callback.setPosition(p, s);
		else
			System.out.println("setPosition: Keiner registriert.");
	}

	@Override
	public void setPicure(byte[] payload) {
		callback.setPicure(payload);

	}

	public void setSwitch(Switch sw) {
		this.sw = sw;

	}

}
