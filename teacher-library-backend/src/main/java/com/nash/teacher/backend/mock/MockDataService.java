package com.nash.teacher.backend.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService extends DataService {

	private static MockDataService INSTANCE;

	private List<Book> books;
	private List<Checkout> checkouts;
	private List<Student> students;
	private static int nextBookId = 0;

	private MockDataService() {
		books = MockDataGenerator.createBooks();
		students = MockDataGenerator.createStudents();
		checkouts = new ArrayList<>();
		nextBookId = books.size() + 1;
	}

	public synchronized static DataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MockDataService();
		}
		return INSTANCE;
	}

	@Override
	public List<Book> getBooks() {
		return books;
	}

	@Override
	public void addBook(Book book) {
		books.add(book);
	}

	@Override
	public void updateBook(Book book) {
		Iterator<Book> iterator = books.iterator();
		while (iterator.hasNext()) {
			Book b = iterator.next();
			if (b.getId() == book.getId()) {
				b.setTitle(book.getTitle());
				b.setAuthor(book.getAuthor());
				b.setCover(book.getCover());
				b.setDescription(book.getDescription());
				b.setIsbn(book.getIsbn());
				b.setOwner(book.getOwner());
				b.setPages(book.getPages());
				b.setTotalCount(book.getTotalCount());
				b.setAvailableCount(book.getAvailableCount());
			}
		}
	}

	@Override
	public void deleteBook(int id) {
		Iterator<Book> iterator = books.iterator();
		while (iterator.hasNext()) {
			Book b = iterator.next();
			if (b.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public Book getBook(int id) {
		for (final Book book : books) {
			if (book.getId() == id) {
				return book;
			}
		}
		return null;
	}

	@Override
	public void checkout(int bookId, String studentName) {
		final Checkout checkout = new Checkout();
		checkout.setStart(new Date());

		for (final Book book : books) {
			if (book.getId() == bookId) {
				checkout.setBook(book);
				book.setAvailableCount(book.getAvailableCount() - 1);
				break;
			}
		}

		for (final Student student : students) {
			if (student.getName().equals(studentName)) {
				checkout.setStudent(student);
				break;
			}
		}

		checkout.setActive(true);
		checkouts.add(checkout);
	}

	@Override
	public void checkin(int bookId) {
		for (final Checkout checkout : checkouts) {
			checkout.setEnd(new Date());
			checkout.setActive(false);
		}
	}

	@Override
	public void addStudent(Student student) {
		students.add(student);
	}

	@Override
	public void deleteStudent(String name) {
		final Iterator<Student> iterator = students.iterator();
		while (iterator.hasNext()) {
			final Student student = iterator.next();
			if (student.getName().equals(name)) {
				iterator.remove();
			}
		}
	}

	@Override
	public Student getStudent(String name) {
		for (final Student student : students) {
			if (student.getName().equals(name)) {
				return student;
			}
		}
		return null;
	}

	@Override
	public List<Student> getStudents() {
		return students;
	}

	@Override
	public List<Book> lookup(String isbn) {
		// need to call the isbn service
		

		return null;
	}

	@Override
	public List<Checkout> getCheckouts() {
		return checkouts;
	}

	@Override
	public List<Checkout> getCheckouts(String name) {
		return null;
	}
}
