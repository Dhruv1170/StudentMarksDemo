package com.studentdata.studentdata.entity;

import java.util.List;
import java.util.Map;

public class StudentResponse {
	
	private String name;
	private String address;
	private Integer age;
	
	private List<Map<String, Double>> subjects;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public List<Map<String, Double>> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Map<String, Double>> subjects) {
		this.subjects = subjects;
	}
	
	@Override
	public String toString() {
		return "StudentResponse [name=" + name + ", address=" + address + ", age=" + age + ", subjects=" + subjects
				+ "]";
	}

	

}
