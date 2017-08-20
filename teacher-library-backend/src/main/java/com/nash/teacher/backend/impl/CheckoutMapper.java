package com.nash.teacher.backend.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;

public class CheckoutMapper implements ResultSetMapper<Checkout> {

	@Override
	public Checkout map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		final Checkout checkout = new Checkout();
		checkout.setId(r.getInt("id"));
		checkout.setStart(r.getDate("start"));
		checkout.setEnd(r.getDate("end"));
		checkout.setActive(r.getDate("end") == null);

		final Student student = new Student();
		student.setName(r.getString("student"));
		checkout.setStudent(student);

		final Book book = new Book();
		book.setId(r.getInt("book"));
		checkout.setBook(book);
		return checkout;
	}
}
