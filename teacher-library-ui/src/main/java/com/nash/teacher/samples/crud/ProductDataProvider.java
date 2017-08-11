//package com.nash.teacher.samples.crud;
//
//import java.util.Locale;
//import java.util.Objects;
//import java.util.stream.Stream;
//
//import com.nash.teacher.backend.DataService;
//import com.nash.teacher.backend.data.Book;
//import com.vaadin.data.provider.AbstractDataProvider;
//import com.vaadin.data.provider.Query;
//
//public class ProductDataProvider extends AbstractDataProvider<Book, String> {
//
//	/** Text filter that can be changed separately. */
//	private String filterText = "";
//
//	/**
//	 * Store given product to the backing data service.
//	 * 
//	 * @param product
//	 *            the updated or new product
//	 */
//	public void save(Book product) {
//		boolean newProduct = product.getId() == -1;
//
//		DataService.get().updateBook(product);
//		if (newProduct) {
//			refreshAll();
//		} else {
//			refreshItem(product);
//		}
//	}
//
//	/**
//	 * Delete given product from the backing data service.
//	 * 
//	 * @param product
//	 *            the product to be deleted
//	 */
//	public void delete(Book product) {
//		DataService.get().deleteBook(product.getId());
//		refreshAll();
//	}
//
//	/**
//	 * Sets the filter to use for the this data provider and refreshes data.
//	 * <p>
//	 * Filter is compared for product name, availability and category.
//	 * 
//	 * @param filterText
//	 *            the text to filter by, never null
//	 */
//	public void setFilter(String filterText) {
//		Objects.requireNonNull(filterText, "Filter text cannot be null");
//		if (Objects.equals(this.filterText, filterText.trim())) {
//			return;
//		}
//		this.filterText = filterText.trim();
//
//		refreshAll();
//	}
//
//	@Override
//	public Integer getId(Book product) {
//		Objects.requireNonNull(product, "Cannot provide an id for a null product.");
//
//		return product.getId();
//	}
//
//	@Override
//	public boolean isInMemory() {
//		return true;
//	}
//
//	@Override
//	public int size(Query<Book, String> t) {
//		return (int) fetch(t).count();
//	}
//
//	@Override
//	public Stream<Book> fetch(Query<Book, String> query) {
//		if (filterText.isEmpty()) {
//			return DataService.get().getAllBooks().stream();
//		}
//		return DataService.get().getAllBooks().stream().filter(product -> passesFilter(product.getTitle(), filterText)
//				|| passesFilter(product.getAvailability(), filterText));
//	}
//
//	private boolean passesFilter(Object object, String filterText) {
//		return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
//	}
//}
