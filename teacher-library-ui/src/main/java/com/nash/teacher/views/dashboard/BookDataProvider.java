package com.nash.teacher.views.dashboard;

import java.util.stream.Stream;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

public class BookDataProvider extends AbstractDataProvider<Book, String> {

	private static final long serialVersionUID = 1L;

	private final DataService service;

	public BookDataProvider(final DataService service) {
		super();
		this.service = service;
	}

	@Override
	public boolean isInMemory() {
		return false;
	}

	@Override
	public int size(Query<Book, String> query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Stream<Book> fetch(Query<Book, String> query) {
		System.out.println(query.getFilter());
		System.out.println(service.getBooks());
		System.out.println("service : " + service.getBooks().stream().sorted().count());
		return service.getBooks().stream().sorted();
	}
}
