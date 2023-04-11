package com.studentdata.studentdata.entity;

import java.util.List;
import java.util.Map;

public class SubjectDto {
	
	private List<Map<String, Double>> subjects;

	public List<Map<String, Double>> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Map<String, Double>> subjects) {
		this.subjects = subjects;
	}

}
