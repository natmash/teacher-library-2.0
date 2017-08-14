package com.nash.teacher.views.students;

import com.nash.teacher.WindowUtils;
import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Student;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class StudentsView extends GridLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Students";

	private final Grid<Student> grid;
	
	public StudentsView() {
		setSizeFull();
		addStyleName("students-view");

		Button addStudentsButton = new Button();
		addStudentsButton.setCaption("Add...");
		addStudentsButton.addClickListener(click -> addStudent());
		addComponent(addStudentsButton);
		setComponentAlignment(addStudentsButton, Alignment.TOP_RIGHT);

		grid = new StudentGrid();
		
		addComponent(grid);

		setRowExpandRatio(0, 1.0f);
		setRowExpandRatio(1, 1000.0f);
	}

	private void addStudent() {
		final Window window = WindowUtils.createWindow("Create Student");
		final FormLayout layout = new FormLayout();

		final TextField field = new TextField();
		field.setCaption("Name");

		GridLayout btnLayout = new GridLayout();
		Button add = new Button();
		add.setCaption("Add");
		add.addClickListener(click -> add(field.getValue(), window));

		Button close = new Button();
		close.setCaption("Close");
		close.addClickListener(click -> window.close());

		btnLayout.addComponent(add);
		btnLayout.addComponent(close);

		layout.addComponent(field);
		layout.addComponent(btnLayout);
		layout.setSizeUndefined();
		layout.setMargin(true);
		window.setContent(layout);
		getUI().addWindow(window);
	}

	private void add(final String name, final Window window) {
		final Student student = new Student();
		student.setName(name);
		DataService.get().addStudent(student);
		grid.getDataProvider().refreshAll();
		window.close();
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
