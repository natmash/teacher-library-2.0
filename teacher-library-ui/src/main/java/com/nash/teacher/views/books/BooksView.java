package com.nash.teacher.views.books;

import java.util.Map;
import java.util.stream.Collectors;

import com.nash.teacher.backend.DataService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class BooksView extends VerticalLayout implements View {

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
		
		grid = new BookGrid(service);
		grid.setDataProvider(new BookDataProvider(service));
		grid.setDataProvider((sortOrders, offset, limit) -> {
			Map<String, Boolean> sortOrder = sortOrders.stream().collect(Collectors.toMap(sort -> sort.getSorted(),
					sort -> SortDirection.ASCENDING.equals(sort.getDirection())));

			return service.getBooks().stream();
		}, () -> service.getBooks().size());

		addComponent(grid);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
