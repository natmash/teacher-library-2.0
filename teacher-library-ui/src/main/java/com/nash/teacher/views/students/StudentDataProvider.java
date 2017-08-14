package com.nash.teacher.views.students;

import java.util.stream.Stream;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Student;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

public class StudentDataProvider extends AbstractDataProvider<Student, String> {

	private static final long serialVersionUID = 1L;

	private final DataService service;

	public StudentDataProvider() {
		super();
		this.service = DataService.get();
	}

	@Override
	public boolean isInMemory() {
		return false;
	}

	@Override
	public int size(Query<Student, String> query) {
		return service.getStudents(false).size();
	}

	@Override
	public Stream<Student> fetch(Query<Student, String> query) {
		return service.getStudents(false).stream().sorted();
	}		
}
