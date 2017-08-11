package com.nash.teacher.backend.impl;

import org.jooq.Record;
import org.jooq.RecordMapper;

import com.nash.teacher.backend.data.Book;

public class BookMapper implements RecordMapper<Record, Book> {

	@Override
	public Book map(Record record) {
		final Book book = new Book();
		book.setTitle(record.get("title", String.class));
		book.setAuthor(record.get("author", String.class));
		// book.setAvailableCount(record.get("", String.class));
		book.setDescription(record.get("description", String.class));
		book.setCover(record.get("cover", String.class));
		book.setId(record.get("id", Integer.class));
		book.setIsbn(record.get("isbn", String.class));
		book.setPages(record.get("pages", Integer.class));
		book.setTotalCount(record.get("total", Integer.class));
//		book.setOwner(owner);

		return book;
	}

}
