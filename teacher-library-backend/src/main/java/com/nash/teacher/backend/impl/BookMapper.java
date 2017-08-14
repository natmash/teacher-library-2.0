package com.nash.teacher.backend.impl;

import org.jooq.Record;
import org.jooq.RecordMapper;

import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Owner;

public class BookMapper implements RecordMapper<Record, Book> {

	@Override
	public Book map(Record record) {
		final Book book = new Book();
		book.setTitle(record.get("title", String.class));
		book.setAuthor(record.get("author", String.class));
		book.setDescription(record.get("description", String.class));
		book.setCover(record.get("cover", String.class));
		book.setId(record.get("id", Integer.class));
		book.setIsbn(record.get("isbn", String.class));
		book.setPages(record.get("pages", Integer.class));
		// TODO how do we get available?  Maybe not from this class???
		book.setAvailable(true);
		book.setOwner(owner(record.get("owner", String.class)));
		return book;
	}

	private Owner owner(final String name) {
		final Owner owner = new Owner();
		owner.setName(name);
		return owner;
	}

}
