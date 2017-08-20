package com.nash.teacher.backend.data;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class Checkout implements Serializable, Comparable<Checkout> {

	private static final long serialVersionUID = 1L;

	@NotNull
	private int id;

	@NotNull
	private Student student;

	private Book book;

	private Date start;

	private Date end;

	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int compareTo(Checkout arg0) {
		return Integer.compare(this.getId(), arg0.getId());
	}
}