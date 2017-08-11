package com.nash.teacher.backend.mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.nash.teacher.backend.data.Book;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;

public class MockDataGenerator {
	private static int nextCheckoutId = 1;
	private static int nextBookId = 1;
	private static final Random random = new Random(1);
	private static final String[] authors = new String[] { "JK Rowling", "Gary Paulsen", "Peter Pettigrew",
			"Shel Silverstein", "Rowling JK", "Roger Nash", "Larry the Cable Guy", "Dan Whitney" };

	private static String[] word1 = new String[] { "The art of", "Mastering", "The secrets of", "Avoiding",
			"For fun and profit: ", "How to fail at", "10 important facts about", "The ultimate guide to", "Book of",
			"Surviving", "Encyclopedia of", "Very much", "Learning the basics of", "The cheap way to",
			"Being awesome at", "The life changer:", "The Vaadin way:", "Becoming one with", "Beginners guide to",
			"The complete visual guide to", "The mother of all references:" };

	private static String[] word2 = new String[] { "gardening", "living a healthy life", "designing tree houses",
			"home security", "intergalaxy travel", "meditation", "ice hockey", "children's education",
			"computer programming", "Vaadin TreeTable", "winter bathing", "playing the cello", "dummies",
			"rubber bands", "feeling down", "debugging", "running barefoot", "speaking to a big audience",
			"creating software", "giant needles", "elephants", "keeping your wife happy" };

	static List<Book> createBooks() {
		List<Book> Books = new ArrayList<Book>();
		for (int i = 0; i < 100; i++) {
			Book p = createBook();
			Books.add(p);
		}

		return Books;
	}

	static List<Student> createStudents() {
		List<Student> students = new ArrayList<>();
		students.add(createStudent("Matt"));
		students.add(createStudent("Stacy"));
		return students;
	}

	private static Student createStudent(final String name) {
		final Student s = new Student();
		s.setName(name);
		return s;
	}

	private static Book createBook() {
		Book p = new Book();
		p.setId(nextBookId++);
		p.setTitle(generateTitle());
		p.setAuthor(generateAuthor());
		p.setCover("random cover");
		p.setDescription("description : " + p.getTitle());
		p.setIsbn("random");
		p.setPages(212);
		return p;
	}

	private static Set<Checkout> getCheckout(List<Checkout> categories, int min, int max) {
		int nr = random.nextInt(max) + min;
		HashSet<Checkout> BookCategories = new HashSet<Checkout>();
		for (int i = 0; i < nr; i++) {
			BookCategories.add(categories.get(random.nextInt(categories.size())));
		}

		return BookCategories;
	}

	private static String generateTitle() {
		return word1[random.nextInt(word1.length)] + " " + word2[random.nextInt(word2.length)];
	}

	private static String generateAuthor() {
		return authors[random.nextInt(authors.length)];
	}

}
