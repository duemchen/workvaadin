package de.lichtmagnet.joystick;

public class JoyForm extends JoyDesign {
	public JoyForm() {
		top.setCaption("Hoch");
		left.setCaption("links");
		right.setCaption("rechts");
		down.setCaption("runter");
		top.addClickListener(e -> {
			System.out.println("x");
			top.setCaption("XXX");
			System.out.println("xxx");
			;
		});
	}
}
