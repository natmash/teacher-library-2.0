package com.nash.teacher.backend.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Student implements Serializable, Comparable<Student> {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;

	private int totalCheckouts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalCheckouts() {
		return totalCheckouts;
	}

	public void setTotalCheckouts(int totalCheckouts) {
		this.totalCheckouts = totalCheckouts;
	}
	
	@Override
	public int compareTo(Student arg0) {
		return getName().compareTo(arg0.getName());
	}

	@Override
	public String toString() {
		return getName();
	}
}
