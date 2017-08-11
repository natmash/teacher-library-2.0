package com.nash.teacher.views.checkouts;

import java.util.stream.Stream;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Checkout;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

public class CheckoutDataProvider extends AbstractDataProvider<Checkout, String> {

	private static final long serialVersionUID = 1L;

	private final DataService service;

	public CheckoutDataProvider(final DataService service) {
		super();
		this.service = service;
	}

	@Override
	public boolean isInMemory() {
		return false;
	}

	@Override
	public int size(Query<Checkout, String> query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Stream<Checkout> fetch(Query<Checkout, String> query) {
		System.out.println(query.getFilter());
		System.out.println(service.getCheckouts());
		System.out.println("service : " + service.getCheckouts().stream().sorted().count());
		return service.getCheckouts().stream().sorted();
	}
}
