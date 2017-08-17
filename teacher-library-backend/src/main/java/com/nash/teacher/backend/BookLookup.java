package com.nash.teacher.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

	private static ExecutorService service = Executors.newFixedThreadPool(30);

	public static Book lookup(final String isbn) {
		Books books;
		try {
			books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
					null).setApplicationName("TeacherClassRoom")
							.setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY)).build();
			List list = books.volumes().list("isbn:" + isbn);
			Volumes volumes = list.execute();
			if (volumes != null && volumes.getTotalItems() > 0) {
				if (volumes.getTotalItems() == 1) {
					return createBook(isbn, volumes.getItems().get(0));
				} else {
					return createBook(isbn, volumes.getItems().get(0));
				}
			}
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		if (info.getSeriesInfo() != null) {
			System.out.println(info.getSeriesInfo().getShortSeriesBookTitle());
		}
		if (info.getAuthors() != null) {
			book.setAuthor(String.join(", ", info.getAuthors()));
		} else {
			book.setAuthor("None");
		}

		if (info.getPageCount() != null) {
			book.setPages(info.getPageCount());
		}
		book.setIsbn(isbn);
		book.setAvailable(true);
		return book;
	}

	public static void main(String[] args) throws GeneralSecurityException, IOException, InterruptedException {
		try (BufferedReader br = new BufferedReader(new FileReader("/home/matt/books.txt"))) {
			String l;
			final AtomicInteger total = new AtomicInteger(0);
			final AtomicInteger done = new AtomicInteger(0);
			final java.util.List<String> notDone = new ArrayList<>();
			while ((l = br.readLine()) != null) {
				final String ln = l;
				service.submit(new Runnable() {
					@Override
					public void run() {
						total.getAndAdd(1);
						String line = ln.replaceAll("\\s+", "");
						if (!line.startsWith("//")) {
							Book book = lookup(line);
							if (book != null) {
								done.getAndAdd(1);
								System.out.println(book.getTitle());
							} else {
								notDone.add(line);
								System.out.println(line + " not found.");
							}
						}
					}
				});
			}
			service.shutdown();
			while (!service.awaitTermination(10, TimeUnit.MINUTES)) {
			}
			System.out.println(Arrays.toString(notDone.toArray()));
			System.out.println("Found " + done + " out of " + total + ".");
		}
	}
}
