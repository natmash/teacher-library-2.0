package com.nash.teacher.backend.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.nash.teacher.backend.data.Student;

public class StudentMapper implements ResultSetMapper<Student> {

	@Override
	public Student map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		final Student student= new Student();
		student.setName(r.getString("name"));
		return student;
	}
}
