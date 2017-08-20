package com.nash.teacher.views.books;

import org.vaadin.dialogs.ConfirmDialog;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.DetailsGenerator;
import com.vaadin.ui.components.grid.ItemClickListener;

public class BookGrid extends Grid<Book> {

	private static final long serialVersionUID = 1L;

	public BookGrid(final DataService service) {
		setSizeFull();

		addColumn(Book::getTitle).setCaption("Book Title").setId("bookTitle");
		addColumn(Book::getAuthor).setCaption("Author");
		addColumn(Book::getIsbn).setCaption("ISBN").setSortable(false);
		addColumn(Book::getPages).setCaption("Pages").setSortable(false);
		addColumn(Book::isAvailable).setCaption("Available").setSortable(false);

		setDetailsGenerator(new DetailsGenerator<Book>() {
			@Override
			public Component apply(Book t) {
				HorizontalLayout layout = new HorizontalLayout();
				Image image = new Image();
				image.setSource(new ExternalResource(t.getCover()));
				image.setWidth("128px");
				image.setHeight("189px");

				Label label = new Label();
				label.setCaption(t.getDescription());
				label.setSizeFull();
				layout.addComponent(image);
				layout.addComponent(label);
				return layout;
			}
		});

		addItemClickListener(new ItemClickListener<Book>() {
			@Override
			public void itemClick(com.vaadin.ui.Grid.ItemClick<Book> event) {
				if (!isDetailsVisible(event.getItem())) {
					setDetailsVisible(event.getItem(), true);
				} else {
					setDetailsVisible(event.getItem(), false);
				}
			}
		});

		addComponentColumn(book -> {
			HorizontalLayout layout = new HorizontalLayout();
			Button checkoutButton = new Button("Checkout");
			checkoutButton.addClickListener(click -> openWindow(book));

			Button checkinButton = new Button("Check-In");
			checkinButton.addClickListener(click -> ConfirmDialog.show(UI.getCurrent(),
					"You are checking in " + book.getTitle() + "...", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								service.checkin(book.getId());
								checkinButton.setEnabled(false);
								checkoutButton.setEnabled(true);
								getDataProvider().refreshAll();
							}
						}
					}));

			if (!book.isAvailable()) {
				checkoutButton.setEnabled(false);
			} else {
				checkinButton.setEnabled(false);
			}

			Button deleteButton = new Button();
			deleteButton.setIcon(VaadinIcons.TRASH);
			deleteButton.addClickListener(click -> ConfirmDialog.show(UI.getCurrent(),
					"You are deleting " + book.getTitle() + "...", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;

						public void onClose(ConfirmDialog dialog) {
							service.deleteBook(book.getId());
							getDataProvider().refreshAll();
						}
					}));
			layout.addComponent(checkoutButton);
			layout.addComponent(checkinButton);
			layout.addComponent(deleteButton);

			return layout;
		}).setSortable(false);
		setRowHeight(40);
	}

	private void openWindow(final Book book) {
		Window window = new AssignStudentWindow(book, this);
		UI.getCurrent().addWindow(window);
	}

	public Book getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Book book) {
		getDataCommunicator().refresh(book);
	}
}
