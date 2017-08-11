package com.nash.teacher.views.books;

import org.vaadin.dialogs.ConfirmDialog;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class BookGrid extends Grid<Book> {

	private static final long serialVersionUID = 1L;

	public BookGrid(final DataService service) {
		setSizeFull();

		addColumn(Book::getTitle).setCaption("Book Title");
		addColumn(Book::getAuthor).setCaption("Author");
		addColumn(Book::getIsbn).setCaption("ISBN");
		addColumn(Book::getTotalCount).setCaption("Count");
		addColumn(Book::getAvailableCount).setCaption("Available");

		addComponentColumn(book -> {
			HorizontalLayout layout = new HorizontalLayout();
			Button checkoutButton = new Button("Checkout");
			// TODO add listener here so we can add checkouts and set other values
			checkoutButton.addClickListener(click -> openWindow(book.getTitle()));
			if (book.getAvailableCount() <= 0) {
				// button.setEnabled(false);
			}
			Button checkinButton = new Button("Check-In");
			checkinButton.addClickListener(click -> ConfirmDialog.show(UI.getCurrent(),
					"You are checking in " + book.getTitle() + "...", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
							// doesn't make sense if we have available and total
							// to not have a drop down of who we are checking in
							// to
							book.setAvailableCount(book.getAvailableCount() + 1);
							if (book.getAvailableCount() == book.getTotalCount()) {
								checkinButton.setEnabled(false);
							}

							if (book.getAvailableCount() > 0) {
								checkoutButton.setEnabled(true);
							}
						}
					}));
			if (book.getAvailableCount() <= 0) {
				// button.setEnabled(false);
			}
			layout.addComponent(checkoutButton);
			layout.addComponent(checkinButton);
			return layout;
		});
		setRowHeight(40);
	}

	private void openWindow(final String title) {
		Window window = new AssignStudentWindow(title);
		UI.getCurrent().addWindow(window);
	}

	public Book getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Book book) {
		getDataCommunicator().refresh(book);
	}
}
