package com.nash.teacher.views.books;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Student;
import com.nash.teacher.views.students.StudentDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AssignStudentWindow extends Window {
	private static final long serialVersionUID = 1L;

	private final Grid<Book> grid;

	private final ComboBox<Student> students;

	public AssignStudentWindow(final Book book, final Grid<Book> grid) {
		super("Checkout for...");
		this.grid = grid;
		setClosable(false);
		setModal(true);
		setDraggable(false);
		setResizable(false);

		center();

		students = new ComboBox<Student>();
		students.setDataProvider(new StudentDataProvider());

		final VerticalLayout layout = new VerticalLayout();

		Label label = new Label(book.getTitle());
		layout.addComponent(label);
		layout.addComponent(students);

		final HorizontalLayout buttonLayout = new HorizontalLayout();

		final Button checkoutButton = new Button("Checkout", event -> addCheckout(book));
		checkoutButton.setClickShortcut(KeyCode.ENTER);
		checkoutButton.addStyleName("primary");
		buttonLayout.addComponent(checkoutButton);
		buttonLayout.addComponent(new Button("Close", event -> close()));

		layout.addComponent(students);
		layout.addComponent(buttonLayout);
		setContent(layout);
	}

	private void addCheckout(final Book book) {
		if (students.getSelectedItem().isPresent()) {
			DataService service = DataService.get();
			service.checkout(book.getId(), students.getSelectedItem().get().getName());
			service.updateBook(book);
			grid.getDataProvider().refreshAll();
			close();
		} else {
			Notification.show("Please select a student...", com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
		}
	}
}
