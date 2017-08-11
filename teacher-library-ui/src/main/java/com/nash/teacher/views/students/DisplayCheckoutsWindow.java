package com.nash.teacher.views.students;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Checkout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DisplayCheckoutsWindow extends Window {
	private static final long serialVersionUID = 1L;

	public DisplayCheckoutsWindow() {
		super("Books Checked Out"); // Set window caption

		setClosable(true);
		setModal(true);
		setDraggable(false);
		setResizable(false);

		center();

		Grid<Checkout> checkouts = new Grid<>();
		checkouts.addColumn(Checkout::getBook).setCaption("Book");
		checkouts.addColumn(Checkout::getStart).setCaption("Started");
		checkouts.addColumn(Checkout::getEnd).setCaption("Ended");
		checkouts.setDataProvider(new StudentCheckoutsDataProvider(DataService.get()));
		
		

		final VerticalLayout layout = new VerticalLayout();
		layout.addComponent(checkouts);
		setContent(layout);
	}
}
