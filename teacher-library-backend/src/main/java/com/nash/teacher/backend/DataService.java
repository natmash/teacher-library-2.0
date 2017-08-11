package com.nash.teacher.backend;

import java.io.Serializable;
import java.util.List;

import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;
import com.nash.teacher.backend.mock.MockDataService;

/**
 * Back-end service interface for retrieving and updating books.
 */
public abstract class DataService implements Serializable {

	public abstract List<Book> getBooks();

	public abstract void addBook(Book book);

	public abstract void updateBook(Book book);

	public abstract void deleteBook(int id);

	public abstract Book getBook(int id);

	public abstract void checkout(int bookId, String studentName);

	public abstract void checkin(int bookId);

	public abstract void addStudent(Student student);

	public abstract void deleteStudent(String name);

	public abstract Student getStudent(String name);

	public abstract List<Student> getStudents();

	public abstract List<Checkout> getCheckouts();
	
	public abstract List<Checkout> getCheckouts(String name);
	
	public abstract List<Book> lookup(String isbn);

	public static DataService get() {
		return MockDataService.getInstance();
	}

}
