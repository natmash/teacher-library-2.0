package com.nash.teacher.backend.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Book implements Serializable, Comparable<Book> {

	private static final long serialVersionUID = 1L;

	private int id;
	
	@NotNull
	private String title = "";
	
	@NotNull
	private String author = "";

	private String cover;

	private Integer pages;

	private String isbn;

	private String description;

	private boolean available;

	private Owner owner;

	private Integer totalCheckouts;

	public Book() {
	}

	public Book(final String title, final String author, final String cover, final Integer pages, final String isbn,
			final String description) {
		this.title = title;
		this.author = author;
		this.cover = cover;
		this.pages = pages;
		this.isbn = isbn;
		this.description = description;
		this.available = true;
		// TODO, get current user
		this.owner = new Owner();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return getTitle();
	}

	public Integer getTotalCheckouts() {
		return totalCheckouts;
	}

	public void setTotalCheckouts(Integer totalCheckouts) {
		this.totalCheckouts = totalCheckouts;
	}

	@Override
	public int compareTo(Book arg0) {
		return title.compareTo(arg0.getTitle());
	}
}
