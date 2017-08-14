package com.nash.teacher.views.books;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class AddBookWindow extends Window {

	private static final long serialVersionUID = 1L;

	public AddBookWindow() {
		setCaption("Add Book");

		center();
		setResizable(true);
		setClosable(true);
		setDraggable(false);
		setModal(true);

		setWidth("30%");

		FormLayout layout = new FormLayout();
		layout.setMargin(true);
		layout.setSizeFull();

		final TextField title = new TextField();
		title.setCaption("Title");
		title.setSizeFull();

		final TextField author = new TextField();
		author.setCaption("Author");
		author.setSizeFull();

		final TextArea description = new TextArea();
		description.setCaption("Description");
		description.setSizeFull();

		final HorizontalLayout gl = new HorizontalLayout();
		gl.setCaption("ISBN");
		final TextField isbn = new TextField();
		isbn.setSizeFull();

		final Button isbnButton = new Button();
		isbnButton.setCaption("Lookup");
		gl.addComponent(isbn);
		gl.addComponent(isbnButton);
		gl.setSizeFull();

		final Image cover = new Image();
		cover.setAlternateText("No Image");

		final TextField pages = new TextField();
		pages.setCaption("Pages");

		layout.addComponent(title);
		layout.addComponent(author);
		layout.addComponent(description);
		layout.addComponent(gl);
		layout.addComponent(cover);
		layout.addComponent(pages);
		// layout.addComponent(cover);

		isbnButton.addClickListener(click -> lookup(isbn.getValue(), title, author, description, pages));
		final HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSizeFull();

		final Button addButton = new Button("Add", event -> close());
		addButton.setClickShortcut(KeyCode.ENTER);
		addButton.addStyleName("primary");
		buttonLayout.addComponent(addButton);
		buttonLayout.addComponent(new Button("Close", event -> close()));

		layout.addComponent(buttonLayout);
		setContent(layout);
	}

	private void lookup(final String isbn, final TextField title, final TextField author, final TextArea description,
			final TextField pages) {
		if (isbn.isEmpty()) {
			Notification.show("Must fill in the ISBN.", Type.WARNING_MESSAGE);
		} else {
			title.setValue("Title");
			author.setValue("author");
			description.setValue("description");
			pages.setValue("123");
			DataService.get().addBook(new Book(title.getValue(), author.getValue(), null,
					Integer.parseInt(pages.getValue()), isbn, description.getValue()));
		}
	}
}
