package com.nash.teacher.backend.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	}

	public synchronized static DataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MockDataService();
		}
		return INSTANCE;
	}

	@Override
	public List<Book> getBooks(boolean top) {
		if (top) {
			System.out.println("doing compare by top");
			System.out.println(books);
			Collections.sort(books, new Comparator<Book>() {
				@Override
				public int compare(Book o1, Book o2) {
					return Integer.compare(o2.getTotalCheckouts(), o1.getTotalCheckouts());
				}
			});
			for (final Book b : books) {
				System.out.println(b.getTitle() + " : " + b.getTotalCheckouts());
			}
		} else {
			System.out.println("doing compare by regular");
			Collections.sort(books, new Comparator<Book>() {
				@Override
				public int compare(Book o1, Book o2) {
					return Integer.compare(o1.getId(), o2.getId());
				}
			});
		}
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
				b.setAvailable(book.isAvailable());
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
				book.setTotalCheckouts(book.getTotalCheckouts() + 1);
				book.setAvailable(false);
				break;
			}
		}

		for (final Student student : students) {
			if (student.getName().equals(studentName)) {
				student.setTotalCheckouts(student.getTotalCheckouts() + 1);
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
			if (checkout.getBook().getId() == bookId) {
				checkout.setEnd(new Date());
				checkout.setActive(false);
				getBook(bookId).setAvailable(true);
			}
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
	public List<Student> getStudents(boolean topFirst) {
		if (topFirst) {
			Collections.sort(students, new Comparator<Student>() {
				@Override
				public int compare(Student arg0, Student arg1) {
					return Integer.compare(arg0.getTotalCheckouts(), arg1.getTotalCheckouts());
				}
			});
		} else {
			Collections.sort(students, new Comparator<Student>() {
				@Override
				public int compare(Student arg0, Student arg1) {
					return arg1.getName().compareTo(arg0.getName());
				}
			});
		}
		return students;
	}

	@Override
	public List<Book> lookup(String isbn) {
		return books;
	}

	@Override
	public List<Checkout> getCheckouts(boolean allCheckouts) {
		if (allCheckouts) {
			return checkouts;
		}
		final List<Checkout> all = new ArrayList<>();
		for (final Checkout checkout : checkouts) {
			if (checkout.isActive()) {
				all.add(checkout);
			}
		}
		return all;
	}

	@Override
	public List<Checkout> getCheckouts(String name, boolean all) {
		final List<Checkout> list = new ArrayList<>();
		System.out.println("ALL : " + all);
		for (final Checkout checkout : checkouts) {
			if (name.equals(checkout.getStudent().getName())) {
				System.out.println(checkout.getEnd());
				if (all) {
					list.add(checkout);
				} else {
					if (checkout.getEnd() == null) {
						list.add(checkout);
					}
				}
			}
		}
		return list;
	}
}
