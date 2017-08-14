package com.nash.teacher.backend.impl;

import java.sql.Connection;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;

public class DatabaseDataService extends DataService {

	private final DSLContext context;

	private static final String BOOKS = "books";

	public DatabaseDataService() {
		context = DSL.using((Connection) null, SQLDialect.POSTGRES_9_5);
	}

	@Override
	public List<Book> getBooks(boolean top) {
		return context.select().from(BOOKS).orderBy(1).fetch().map(new BookMapper());
	}

	@Override
	public void addBook(Book book) {
		// Table<Record> table = new Table
		// context.insertInto((Table) null).;
		// context.newRecord();
	}

	@Override
	public void updateBook(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBook(int id) {
		// context.fetchOne(BOOKS).
	}

	@Override
	public Book getBook(int id) {
		context.select().asField();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkout(int bookId, String studentName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkin(int bookId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addStudent(Student student) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteStudent(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Student getStudent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStudents(boolean topFirst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> lookup(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Checkout> getCheckouts(boolean active) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Checkout> getCheckouts(String name, boolean all) {
		// TODO Auto-generated method stub
		return null;
	}
}
