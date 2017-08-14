package com.nash.teacher.views.dashboard;

import com.nash.teacher.backend.data.Book;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class DashboardGrid extends Grid<Book> {

	private static final long serialVersionUID = 1L;

	public DashboardGrid() {
		setSizeFull();

		addColumn(Book::getTitle).setCaption("Book Title");
		addColumn(Book::getAuthor).setCaption("Author");
		addColumn(Book::getIsbn).setCaption("ISBN");

		setRowHeight(40);
	}

	public Book getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Book book) {
		getDataCommunicator().refresh(book);
	}
}
