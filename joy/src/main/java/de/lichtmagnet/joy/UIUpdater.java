package de.lichtmagnet.joy;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class UIUpdater {

	public UIUpdater(final Runnable uiRunnable) {
		UI.getCurrent().access(new Runnable() {
			@Override
			public void run() {
				try {
					VaadinSession.getCurrent().getLockInstance().lock();
					uiRunnable.run();
				} catch (Throwable e) {
					System.out.println(e.getClass().getSimpleName() + ":" + e.getMessage());
				} finally {
					VaadinSession.getCurrent().getLockInstance().unlock();
				}

			}
		});

	}

}
