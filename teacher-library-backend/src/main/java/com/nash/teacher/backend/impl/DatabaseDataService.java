package com.nash.teacher.backend.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerColumnMapper;

import com.google.common.collect.Lists;
import com.nash.teacher.backend.BookLookup;
import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;

public class DatabaseDataService extends DataService {

	private static final long serialVersionUID = 1L;

	private static final String URL = "p2d0untihotgr5f6.cbetxkdyhwsb.us-east-1.rds.amazonaws.com/iu1h9ilg02zzvr2v";
	private static final String USERNAME = "toewq5qpyt5b1ade";
	private static final String PASSWORD = "hemk00e76s9w8ivf";

	private final Handle handle;

	private static DatabaseDataService INSTANCE;

	public synchronized static DataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DatabaseDataService();
		}
		return INSTANCE;
	}

	private DatabaseDataService() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mariadb://" + URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		handle = DBI.open(connection);
	}

	public static void main(String[] args) {

		Student s = new DatabaseDataService().getStudent("Matt");

		System.out.println(s);
		// System.out.println(new DatabaseDataService().getBooks(false));
	}

	@Override
	// XXX
	public List<Book> getBooks(boolean top) {
		List<Book> books = handle.createQuery("select * from books").map(new BookMapper()).list();
		List<Checkout> checkouts = handle.createQuery("select * from checkouts").map(new CheckoutMapper()).list();

		for (final Book book : books) {
			boolean available = true;
			for (final Checkout checkout : checkouts) {
				if (checkout.getBook().getId() == book.getId()) {
					if (checkout.isActive()) {
						available = false;
						if (book.getTotalCheckouts() == null) {
							book.setTotalCheckouts(1);
						}
						book.setTotalCheckouts(book.getTotalCheckouts() + 1);
					}
				}
			}
			book.setAvailable(available);
		}

		if (top) {
			Collections.sort(books, new Comparator<Book>() {
				@Override
				public int compare(Book o1, Book o2) {
					return o1.getTitle().compareTo(o2.getTitle());
				}
			});
			Collections.sort(books, new Comparator<Book>() {
				@Override
				public int compare(Book o1, Book o2) {
					return o2.getTotalCheckouts().compareTo(o1.getTotalCheckouts());
				}
			});
		} else {
			Collections.sort(books, new Comparator<Book>() {
				@Override
				public int compare(Book o1, Book o2) {
					return o1.getTitle().compareTo(o2.getTitle());
				}
			});
		}

		return books;
	}

	@Override
	public void addBook(Book book) {
		handle.createStatement("insert into books (title, author, pages, isbn, description, owner, cover)"
				+ " values (:title, :author, :pages, :isbn, :description, :owner, :cover)")
				.bind("title", book.getTitle()).bind("author", book.getAuthor())
				.bind("description", book.getDescription()).bind("cover", book.getCover()).bind("isbn", book.getIsbn())
				.bind("pages", book.getPages()).bind("owner", "Stacy").execute();
	}

	@Override
	// TODO
	public void updateBook(Book book) {
	}

	@Override
	public void deleteBook(int id) {
		handle.createStatement("delete from books where id=:id").bind("id", id).execute();
	}

	@Override
	public Book getBook(int id) {
		final Book book = handle.createQuery("select * from books where id=:id").bind("id", id).map(new BookMapper())
				.first();
		final List<Checkout> checkouts = handle.createQuery("select * from checkouts where book=:book").bind("book", id)
				.map(new CheckoutMapper()).list();

		boolean available = true;
		for (final Checkout checkout : checkouts) {
			if (checkout.isActive()) {
				available = false;
				break;
			}
		}

		book.setAvailable(available);
		book.setTotalCheckouts(checkouts.size());
		return book;
	}

	@Override
	public void checkout(int bookId, String studentName) {
		handle.createStatement("insert into checkouts (book, student, start) values (:book, :student, :start)")
				.bind("book", bookId).bind("student", studentName)
				.bind("start", new Date(new java.util.Date().getTime())).execute();
	}

	@Override
	public void checkin(int bookId) {
		handle.createStatement("update checkouts set end=:end where book=:book").bind("book", bookId)
				.bind("end", new Date(new java.util.Date().getTime())).execute();
	}

	@Override
	public void addStudent(Student student) {
		handle.createStatement("insert into students (name) values (:name)").bind("name", student.getName()).execute();
	}

	@Override
	public void deleteStudent(String name) {
		handle.createStatement("delete from students where name=:name").bind("name", name).execute();
	}

	@Override
	public Student getStudent(String name) {
		Student student = handle.createQuery("select * from students where name=:name").bind("name", name)
				.map(new StudentMapper()).first();
		int count = handle.createQuery("select count(*) from checkouts where student=:name").bind("name", name)
				.map(IntegerColumnMapper.PRIMITIVE).first();
		student.setTotalCheckouts(count);
		return student;
	}

	@Override
	// XXX
	public List<Student> getStudents(boolean topFirst) {
		final List<Student> students = handle.createQuery("select * from students").map(new StudentMapper()).list();
		final List<Checkout> checkouts = handle.createQuery("select * from checkouts").map(new CheckoutMapper()).list();
		for (final Student student : students) {
			for (final Checkout checkout : checkouts) {
				if (checkout.getStudent().getName().equals(student.getName())) {
					student.setTotalCheckouts(student.getTotalCheckouts() + 1);
				}
			}
		}

		if (topFirst) {
			Collections.sort(students, new Comparator<Student>() {

				@Override
				public int compare(Student o1, Student o2) {
					return Integer.compare(o2.getTotalCheckouts(), o1.getTotalCheckouts());
				}
			});
		} else {
			Collections.sort(students, new Comparator<Student>() {

				@Override
				public int compare(Student o1, Student o2) {
					return o1.getName().compareTo(o2.getName());
				}

			});
		}

		return students;
	}

	@Override
	public List<Book> lookup(String isbn) {
		return Lists.newArrayList(BookLookup.lookup(isbn));
	}

	@Override
	public List<Checkout> getCheckouts(boolean active) {
		final List<Checkout> checkouts = new ArrayList<>();
		if (!active) {
			checkouts.addAll(
					handle.createQuery("select * from checkouts where end is null").map(new CheckoutMapper()).list());
		} else {
			checkouts.addAll(handle.createQuery("select * from checkouts").map(new CheckoutMapper()).list());
		}

		final List<Book> books = handle.createQuery("select * from books").map(new BookMapper()).list();
		final List<Student> students = handle.createQuery("select * from students").map(new StudentMapper()).list();
		for (final Checkout checkout : checkouts) {
			for (final Book book : books) {
				if (checkout.getBook().getId() == book.getId()) {
					checkout.setBook(book);
					break;
				}
			}
			for (final Student student : students) {
				if (checkout.getStudent().getName().equals(student.getName())) {
					checkout.setStudent(student);
					break;
				}
			}
		}

		return checkouts;
	}

	@Override
	// TODO
	public List<Checkout> getCheckouts(String name, boolean all) {
		// TODO Auto-generated method stub
		return null;
	}
}
