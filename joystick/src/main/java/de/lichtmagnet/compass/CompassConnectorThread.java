package de.lichtmagnet.compass;

public class CompassConnectorThread extends Thread implements CompassCallback {
	CompassCallback callback = null;
	private CompassReader tr;

	public CompassConnectorThread() {

	}

	public void register(CompassCallback x) {
		callback = x;
		// x.setPosition("waiting for Compass...");

	}

	@Override
	public void run() {

		while (!isInterrupted()) {
			tr = new CompassReader();

			tr.register(this);
			try {
				tr.connectToMQTT();

			} catch (InterruptedException e1) {
				interrupt();
				System.out.println("Unterbrechung in sleep()0");
				break;
			}
			while (tr.isConnected()) {
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
				Thread.sleep(5000);
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

}
