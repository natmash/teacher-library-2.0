package com.nash.teacher.backend.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Book implements Serializable, Comparable<Book> {

	private static final long serialVersionUID = 1L;

	private int id = -1;
	@NotNull
	private String title = "";
	@NotNull
	private String author = "";

	private String cover;

	private int pages;

	private String isbn;

	private String description;

	private int totalCount;

	private int availableCount;

	private Owner owner;

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

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getAvailableCount() {
		return availableCount;
	}

	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
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

	@Override
	public int compareTo(Book arg0) {
		return title.compareTo(arg0.getTitle());
	}
}
