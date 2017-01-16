package de.lichtmagnet.joy;

import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Audio;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class JoyDesign extends VerticalLayout {
	protected Image img = new Image();
	protected TextField compass = new TextField();
	protected NativeButton top = new NativeButton();
	protected NativeButton left = new NativeButton();
	protected NativeButton right = new NativeButton();
	protected NativeButton down = new NativeButton();
	protected NativeButton save = new NativeButton();
	protected Switch sw = new Switch("Mirror");
	protected Panel p = new Panel("Joystick");
	protected Audio audio = new Audio("audio");
	//protected CssCheckBox check = new CssCheckBox();

	public JoyDesign() {
		//Design.read(this);
		//img.setHeight("220px");

		audio.setSource(new ClassResource("/CL.mp3"));
		audio.setAutoplay(true);
		audio.setShowControls(false);
		audio.setSizeUndefined();
		this.addComponent(audio);
		this.setExpandRatio(audio, 0);
		audio.play();

		img.setSource(new ClassResource("/stonehenge3.jpg"));
		img.setSizeFull();

		p.setSizeUndefined();
		p.setContent(img);
		//p.setHeight("300px");
		this.addComponent(p);
		//this.addComponent(p, "top: 5%; bottom: 50%;");
		//this.addComponent(img, "top: 5%; bottom: 70%;");
		compass.setSizeFull();
		this.addComponent(compass);
		VerticalLayout vl = new VerticalLayout();
		vl.setHeight("200px");
		vl.setWidth("200px");
		vl.addComponent(new TextField("ser"));
		//this.addComponent(vl, "top: 45%; bottom: 67%;");

		//img.setHeight("300px");
		//setComponentAlignment(img, Alignment.TOP_CENTER);
		//		this.addComponent(compass);
		//		compass.setSizeFull();
		//		//setComponentAlignment(compass, Alignment.MIDDLE_CENTER);
		GridLayout joy = new GridLayout(3, 3);
		joy.setSizeFull();
		joy.addComponent(top, 1, 0);
		joy.addComponent(left, 0, 1);
		joy.addComponent(right, 2, 1);
		joy.addComponent(down, 1, 2);
		top.setSizeFull();
		left.setSizeFull();
		right.setSizeFull();
		down.setSizeFull();
		this.addComponent(joy);
		setComponentAlignment(joy, Alignment.MIDDLE_CENTER);
		joy.setSpacing(true);
		HorizontalLayout ho = new HorizontalLayout();

		addComponent(ho);
		ho.setSizeFull();
		save.setHeight("50px");
		save.setWidth("100px");
		ho.addComponents(sw, save);
		ho.setHeight("60px");

		ho.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
		//save.setSizeFull();
		//setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
		//		HorizontalLayout hl = new HorizontalLayout();
		//		this.addComponent(hl);
		//		hl.setSizeFull();

		//		hl.addComponent(save);
		//		
		//		hl.setComponentAlignment(save, Alignment.BOTTOM_CENTER);
		//		//hb.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
		//
		//		//		this.addComponent(img, "left: 1%; right: 99%; top: 1%; bottom: 50%;");
		//		//		this.addComponent(compass, "left: 30%; right: 10%; top: 20%; bottom: 20%;");
		//		this.setSizeFull();
		this.setSpacing(true);
		this.setMargin(true);
		//		//setExpandRatio(img, 1.0f);

	}
}
