package de.lichtmagnet.joy;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.vaadin.server.StreamResource.StreamSource;

public class MyImageSource implements StreamSource {
	ByteArrayOutputStream imagebuffer = null;
	int reloads = 0;

	private BufferedImage bImageFromConvert;

	public MyImageSource(BufferedImage bImageFromConvert) {
		// TODO Auto-generated constructor stub
		this.bImageFromConvert = bImageFromConvert;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	/* We need to implement this method that returns
	* the resource as a stream. */
	public InputStream getStream() {
		/* Create an image and draw something on it. */

		try {
			bImageFromConvert = resize(bImageFromConvert, 340, 200);
			imagebuffer = new ByteArrayOutputStream();
			ImageIO.write(bImageFromConvert, "jpg", imagebuffer);
			/* Return a stream from the buffer. */
			return new ByteArrayInputStream(imagebuffer.toByteArray());

		} catch (Exception e) {
			return null;
		}
	}
}
