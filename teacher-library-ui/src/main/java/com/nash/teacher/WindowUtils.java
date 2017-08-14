package com.nash.teacher;

import com.vaadin.ui.Window;

public class WindowUtils {
	public static Window createWindow(final String caption) {
		Window window = new Window();
		window.setModal(true);
		window.setClosable(true);
		window.setDraggable(false);
		window.setResizable(false);
		window.setCaption(caption);
		window.center();
		return window;
	}
}
