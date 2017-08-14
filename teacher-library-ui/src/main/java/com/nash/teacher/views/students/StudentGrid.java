package com.nash.teacher.views.students;

import org.vaadin.dialogs.ConfirmDialog;

import com.nash.teacher.WindowUtils;
import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class StudentGrid extends Grid<Student> {

	private static final long serialVersionUID = 1L;

	public StudentGrid() {
		setSizeFull();

		setDataProvider(new StudentDataProvider());

		addColumn(Student::getName).setCaption("Name");
		addColumn(Student::getTotalCheckouts).setCaption("Checkouts");

		addComponentColumn(student -> {
			HorizontalLayout layout = new HorizontalLayout();

			Button checkedOut = new Button("Checked Out");
			checkedOut.addClickListener(click -> openCheckedOut(student));
			checkedOut.setEnabled(false);

			Button checkIn = new Button("Check-In");
			checkIn.addClickListener(click -> openCheckIn(student));
			checkIn.setEnabled(false);

			Button deleteButton = new Button("Delete");
			deleteButton.setIcon(VaadinIcons.TRASH);
			deleteButton.addClickListener(click -> openDelete(student));

			layout.addComponent(checkedOut);
			layout.addComponent(checkIn);
			layout.addComponent(deleteButton);
			return layout;
		});

		setRowHeight(40);
	}

	private void openCheckedOut(final Student student) {
		Window window = WindowUtils.createWindow("Book List");
		Grid<Checkout> grid = new Grid<>();
		grid.addColumn(Checkout::getBook).setCaption("Book");
		grid.addColumn(Checkout::getStart).setCaption("Start");
		grid.addColumn(Checkout::getEnd).setCaption("End");

		grid.setDataProvider(new StudentCheckoutsDataProvider(student, true));
		window.setContent(grid);
		getUI().addWindow(window);
	}

	private void openCheckIn(final Student student) {
		Window window = WindowUtils.createWindow("Check-In Book");

		GridLayout layout = new GridLayout();
		Grid<Checkout> grid = new Grid<>();
		grid.addColumn(Checkout::getBook).setCaption("Book");
		grid.addColumn(Checkout::getStart).setCaption("Start");
		grid.addComponentColumn(checkout -> {
			Button checkIn = new Button("Check-In");
			// checkIn.addClickListener(click -> openDelete(student));
			return checkIn;
		});
		grid.setDataProvider(new StudentCheckoutsDataProvider(student, false));
		layout.setSizeFull();
		layout.addComponent(grid);

		window.setContent(layout);
		getUI().addWindow(window);
	}

	private void openDelete(final Student student) {
		final String name = student.getName();
		ConfirmDialog.show(UI.getCurrent(), "Are you sure you want to delete " + name + "?",
				new ConfirmDialog.Listener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClose(ConfirmDialog arg0) {
						if (arg0.isConfirmed()) {
							DataService.get().deleteStudent(name);
							refresh(student);
						}
					}
				});
	}

	public Student getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Student student) {
		getDataProvider().refreshAll();
	}
}
