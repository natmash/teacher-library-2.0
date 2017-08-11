package com.nash.teacher.views.books;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Student;
import com.nash.teacher.views.students.StudentDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AssignStudentWindow extends Window {
	private static final long serialVersionUID = 1L;

	public AssignStudentWindow(final String title) {
		super("Checkout " + title + " to..."); // Set window caption

		setClosable(false);
		setModal(true);
		setDraggable(false);
		setResizable(false);

		center();

		ComboBox<Student> students = new ComboBox<Student>();
		students.setDataProvider(new StudentDataProvider(DataService.get()));

		final VerticalLayout layout = new VerticalLayout();
		layout.addComponent(students);
		final GridLayout buttonLayout = new GridLayout();

		buttonLayout.addComponent(new Button("Checkout", event -> addCheckout()));
		buttonLayout.addComponent(new Button("Close", event -> close()));

		layout.addComponent(buttonLayout);
		setContent(layout);
	}

	private void addCheckout() {
		DataService.get().checkout(1, "Matt");
		close();
	}
}
