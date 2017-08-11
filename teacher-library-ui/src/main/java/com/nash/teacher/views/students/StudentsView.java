package com.nash.teacher.views.students;

import java.util.Map;
import java.util.stream.Collectors;

import com.nash.teacher.backend.DataService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.VerticalLayout;

public class StudentsView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Students";

	private final StudentGrid grid;

	private final DataService service;

	public StudentsView() {
		service = DataService.get();
		setSizeFull();
		addStyleName("students-view");
		grid = new StudentGrid(service);
		grid.setDataProvider(new StudentDataProvider(service));
		grid.setDataProvider((sortOrders, offset, limit) -> {
			        Map<String, Boolean> sortOrder = sortOrders.stream()
			                .collect(Collectors.toMap(
			                        sort -> sort.getSorted(),
			                        sort -> SortDirection.ASCENDING.equals(
			                                sort.getDirection())));

			        return service.getStudents().stream();
			    },
			    () -> service.getStudents().size()
			);
		grid.setRowHeight(40);
		addComponent(grid);
    }

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
