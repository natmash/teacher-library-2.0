package com.nash.teacher.views.books;

import java.util.Map;
import java.util.stream.Collectors;

import com.nash.teacher.backend.DataService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;

public class BooksView extends GridLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Books";

	private final BookGrid grid;

	private final DataService service;

	public BooksView() {
		setSizeFull();
		setSpacing(false);
		addStyleName("books-view");
		service = DataService.get();

		Button addBookButton = new Button();
		addBookButton.setCaption("Add...");
		addComponent(addBookButton);
		setComponentAlignment(addBookButton, Alignment.TOP_RIGHT);

		grid = new BookGrid(service);
		grid.setDataProvider(new BookDataProvider(service));

		addComponent(grid);

		setRowExpandRatio(0, 1.0f);
		setRowExpandRatio(1, 10000.0f);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
