package de.lichtmagnet.compass;

/**
 *
 * @author duemchen
 */
public interface CompassCallback {
	void setPosition(String path, String s);

	void setPicure(byte[] payload);

}
