package com.nash.teacher.views.students;

import com.nash.teacher.backend.DataService;
import com.nash.teacher.backend.data.Student;
import com.vaadin.data.provider.ListDataProvider;

public class StudentDataProvider extends ListDataProvider<Student> {

	private static final long serialVersionUID = 1L;

	private final DataService service;

	public StudentDataProvider(final DataService service) {
		super(service.getStudents());
		this.service = service;
	}
}
