package com.nash.teacher.views.students;

import org.vaadin.dialogs.ConfirmDialog;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Student;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

public class StudentGrid extends Grid<Student> {

	private static final long serialVersionUID = 1L;

	private final DataService service;

	public StudentGrid(final DataService service) {
		this.service = service;
		setSizeFull();

		addColumn(Student::getName).setCaption("Name");
		addColumn(Student::getTotalCheckouts).setCaption("Checkouts");

		addComponentColumn(student -> {
			Button button = new Button("Delete");
			button.setIcon(VaadinIcons.TRASH);
			button.addClickListener(click -> openWindow(student));
			return button;
		});

		addSelectionListener(new SelectionListener<Student>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void selectionChange(SelectionEvent<Student> event) {
				DisplayCheckoutsWindow window = new DisplayCheckoutsWindow();
				UI.getCurrent().addWindow(window);
			}
		});
	}

	private void openWindow(final Student student) {
		final String name = student.getName();
		ConfirmDialog.show(UI.getCurrent(), "Are you sure you want to delete " + name + "?",
				new ConfirmDialog.Listener() {

					@Override
					public void onClose(ConfirmDialog arg0) {
						if (arg0.isConfirmed()) {
							service.deleteStudent(name);
							refresh(student);
						}
					}
				});
	}

	public Student getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Student student) {
		getDataCommunicator().markAsDirty();
		getDataCommunicator().refresh(student);
	}
}
