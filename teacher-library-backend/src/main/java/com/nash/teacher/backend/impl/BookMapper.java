package com.nash.teacher.backend.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Owner;

public class BookMapper implements ResultSetMapper<Book> {

	@Override
	public Book map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		final Book book = new Book();
		book.setTitle(r.getString("title"));
		book.setAuthor(r.getString("author"));
		book.setCover(r.getString("cover"));
		book.setDescription(r.getString("description"));
		book.setId(r.getInt("id"));
		book.setIsbn(r.getString("isbn"));
		book.setPages(r.getInt("pages"));
		book.setOwner(owner(r.getString("owner")));
		return book;
	}

	private Owner owner(final String name) {
		final Owner owner = new Owner();
		owner.setName(name);
		return owner;
	}
}
