package com.nash.teacher.views.books;

import java.util.stream.Stream;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;

public class BookDataProvider extends AbstractBackEndDataProvider<Book, String> {

	private static final long serialVersionUID = 1L;

	private final DataService service;

	private final boolean all;

	public BookDataProvider(final DataService service, boolean all) {
		super();
		this.service = service;
		this.all = all;
	}

	@Override
	public boolean isInMemory() {
		return false;
	}

	@Override
	protected Stream<Book> fetchFromBackEnd(Query<Book, String> query) {
		System.out.println("query limit : " + query.getLimit());
		System.out.println("query offset : " + query.getOffset());
		return service.getBooks(all).subList(query.getOffset(), query.getOffset() + query.getLimit()).stream()
				.unordered();
	}

	@Override
	protected int sizeInBackEnd(Query<Book, String> query) {
		return service.getBooks(all).size();
	}
}
