package com.nash.teacher.views.dashboard;

import java.util.stream.Stream;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.components.grid.DescriptionGenerator;

public class DashboardView extends HorizontalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "Dashboard";

	public DashboardView() {
		setSizeFull();
		addStyleName("dashboard-view");
		addComponent(createTopBooksPanel());
		addComponent(createTopStudentsPanel());
		addComponent(createCurrentCheckoutsPanel());
	}

	private Panel createTopBooksPanel() {
		Panel panel = new Panel();
		panel.setCaption("Top Books");

		GridLayout content = new GridLayout();
		content.setSizeFull();
		Grid<Book> grid = new Grid<>();
		grid.setSizeFull();
		grid.addColumn(Book::getTitle).setCaption("Book").setDescriptionGenerator(new DescriptionGenerator<Book>() {

			@Override
			public String apply(Book arg0) {
				return arg0.getTitle();
			}
		});
		;
		grid.addColumn(Book::getTotalCheckouts).setCaption("Checkouts");
		content.addStyleName("topbookspanel");
		grid.setDataProvider(new AbstractDataProvider<Book, String>() {
			@Override
			public Stream<Book> fetch(Query<Book, String> query) {
				return DataService.get().getBooks(true).stream().unordered();
			}

			@Override
			public boolean isInMemory() {
				return false;
			}

			@Override
			public int size(Query<Book, String> query) {
				return DataService.get().getBooks(true).size();
			}
		});
		content.addComponent(grid);
		panel.setContent(content);
		panel.setSizeFull();
		return panel;
	}

	private Panel createTopStudentsPanel() {
		Panel panel = new Panel();
		panel.setCaption("Top Students");

		GridLayout content = new GridLayout();
		content.setSizeFull();
		Grid<Student> grid = new Grid<>();
		grid.setSizeFull();
		grid.addColumn(Student::getName).setCaption("Student");
		grid.addColumn(Student::getTotalCheckouts).setCaption("Books");

		content.addStyleName("topstudentspanel");
		grid.setDataProvider(new AbstractDataProvider<Student, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Stream<Student> fetch(Query<Student, String> query) {
				return DataService.get().getStudents(true).stream().unordered();
			}

			@Override
			public boolean isInMemory() {
				return false;
			}

			@Override
			public int size(Query<Student, String> query) {
				return DataService.get().getStudents(true).size();
			}
		});
		content.addComponent(grid);
		panel.setContent(content);
		panel.setSizeFull();
		return panel;
	}

	private Panel createCurrentCheckoutsPanel() {
		Panel panel = new Panel();
		panel.setCaption("Currently Out");

		GridLayout content = new GridLayout();
		content.setSizeFull();
		Grid<Checkout> grid = new Grid<>();
		grid.addColumn(Checkout::getBook).setCaption("Book")
				.setDescriptionGenerator(new DescriptionGenerator<Checkout>() {

					@Override
					public String apply(Checkout arg0) {
						return arg0.getBook().getTitle();
					}
				});
		grid.addColumn(Checkout::getStudent).setCaption("Student");
		grid.setDataProvider(new AbstractDataProvider<Checkout, String>() {
			@Override
			public Stream<Checkout> fetch(Query<Checkout, String> query) {
				return DataService.get().getCheckouts(false).stream().sorted();
			}

			@Override
			public boolean isInMemory() {
				return false;
			}

			@Override
			public int size(Query<Checkout, String> query) {
				return DataService.get().getCheckouts(false).size();
			}
		});
		grid.setSizeFull();
		content.addStyleName("currentcheckoutspanel");
		content.addComponent(grid);
		panel.setContent(content);
		panel.setSizeFull();
		return panel;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
