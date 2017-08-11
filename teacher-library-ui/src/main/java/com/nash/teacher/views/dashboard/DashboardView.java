package com.nash.teacher.views.dashboard;

import java.util.Map;
import java.util.stream.Collectors;

import com.nash.teacher.backend.DataService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.VerticalLayout;

public class DashboardView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Dashboard";

	private final DashboardGrid grid;

	private final DataService service;

	public DashboardView() {
		setSizeFull();
		addStyleName("dashboard-view");
		grid = new DashboardGrid();
		service = DataService.get();
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
