package com.nash.teacher.views.students;

import java.util.Comparator;
import java.util.stream.Stream;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Checkout;
import com.nash.teacher.backend.data.Student;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

public class StudentCheckoutsDataProvider extends AbstractDataProvider<Checkout, String> {

	private static final long serialVersionUID = 1L;

	private final Student student;
	
	private final boolean all;
	
	private final DataService service;

	public StudentCheckoutsDataProvider(final Student student, boolean all) {
		super();
		this.all = all;
		this.service = DataService.get();
		this.student = student;
	}

	@Override
	public boolean isInMemory() {
		return false;
	}

	@Override
	public int size(Query<Checkout, String> query) {
		return service.getCheckouts(student.getName(), all).size();
	}

	@Override
	public Stream<Checkout> fetch(Query<Checkout, String> query) {
		return service.getCheckouts(student.getName(), all).stream().sorted(new Comparator<Checkout>() {
			@Override
			public int compare(Checkout o1, Checkout o2) {
				return o1.getStart().compareTo(o2.getStart());
			}
		});
	}
}
