package com.nash.teacher.backend;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volumes;
import com.nash.teacher.backend.data.Book;

public class BookLookup {

	private static final String API_KEY = "AIzaSyBSLkCv1ZQFKHnIvMHMO12_d-UPMQGZ0IA";

	public static Book lookup(final String isbn) throws GeneralSecurityException, IOException {
		Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), null).setApplicationName("TeacherClassRoom")
						.setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY)).build();
		List list = books.volumes().list("isbn:" + isbn);
		Volumes volumes = list.execute();

		if (volumes.getTotalItems() > 0) {
			if (volumes.getTotalItems() == 1) {
				return createBook(isbn, volumes.getItems().get(0));
			}
		} else {
			System.out.println("Unable to find " + isbn);
		}

		return null;
	}

	private static Book createBook(final String isbn, final Volume volume) {
		Book book = new Book();
		VolumeInfo info = volume.getVolumeInfo();
		book.setTitle(info.getTitle());
		book.setDescription(info.getDescription());
		if (info.getImageLinks() != null) {
			book.setCover(info.getImageLinks().getThumbnail());
		}
		book.setAuthor(Arrays.toString(info.getAuthors().toArray()));
		book.setPages(info.getPageCount());
		book.setIsbn(isbn);
		return book;
	}

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		lookup("9780763648534");
	}
}
