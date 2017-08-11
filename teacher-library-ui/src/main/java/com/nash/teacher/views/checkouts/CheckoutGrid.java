package com.nash.teacher.views.checkouts;

import org.vaadin.dialogs.ConfirmDialog;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Checkout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class CheckoutGrid extends Grid<Checkout> {

	private static final long serialVersionUID = 1L;

	public CheckoutGrid(final DataService service) {
		setSizeFull();

		addColumn(Checkout::getStudent).setCaption("Student");
		addColumn(Checkout::getBook).setCaption("Book");
		addColumn(Checkout::getStart).setCaption("Start");
		addColumn(Checkout::getEnd).setCaption("End");
	}

	public Checkout getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Checkout checkout) {
		getDataCommunicator().refresh(checkout);
	}
}
