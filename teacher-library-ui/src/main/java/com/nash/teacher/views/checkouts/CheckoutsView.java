package com.nash.teacher.views.checkouts;

import java.util.Map;
import java.util.stream.Collectors;

import com.nash.teacher.backend.DataService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.VerticalLayout;

public class CheckoutsView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Checkouts";

	private final CheckoutGrid grid;

	private final DataService service;

	public CheckoutsView() {
		setSizeFull();
		setSpacing(false);
		addStyleName("books-view");
		service = DataService.get();
		
		grid = new CheckoutGrid(service);
		grid.setDataProvider((sortOrders, offset, limit) -> {
			Map<String, Boolean> sortOrder = sortOrders.stream().collect(Collectors.toMap(sort -> sort.getSorted(),
					sort -> SortDirection.ASCENDING.equals(sort.getDirection())));

			return service.getCheckouts(true).stream();
		}, () -> service.getCheckouts(true).size());

		addComponent(grid);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
