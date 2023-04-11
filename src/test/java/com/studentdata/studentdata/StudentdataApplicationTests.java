package com.studentdata.studentdata;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.studentdata.studentdata.constant.ApplicationConstants;
import com.studentdata.studentdata.entity.StudentDto;
import com.studentdata.studentdata.entity.StudentEntity;
import com.studentdata.studentdata.entity.StudentResponse;
import com.studentdata.studentdata.entity.SubjectData;
import com.studentdata.studentdata.entity.SubjectDataDto;
import com.studentdata.studentdata.service.StudentService;

@SpringBootTest
class StudentdataApplicationTests {

	@Autowired
	StudentService studentService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void completableFutureSaveStudentDataTest() throws Exception
	{   
		CompletableFuture<Map<String, Object>> studentResponse = new CompletableFuture<>();
		StudentDto studentDataDto = new StudentDto();
		List<Map<String, Double>> subjectsData = new ArrayList<>();
		Map<String, Double> subjectList = new HashMap<String, Double>();
		
		StudentEntity studentEntity = new StudentEntity();
		
		studentEntity.setName("Hardik");
		studentEntity.setAge(23);
		studentEntity.setAddress("Ahmedabad, Gujarat");
		
		subjectList.put("Maths", 80d);
		subjectList.put("Maths", 90d);
		
		subjectsData.add(subjectList);
		studentEntity.setSubjects(subjectsData);
		studentDataDto.setStudentEntity(studentEntity);
		studentResponse = studentService.saveStudentData(studentDataDto);
		
		assertNotEquals(null, studentResponse);
	}
	
	
	@Test
	public void completableFutureGetStudentDataTest() throws Exception
	{   
		StudentResponse testStudentData = studentService.getStudentData("Hardik");
		System.out.println("testStudentData : "+testStudentData);
		assertNotEquals(null, testStudentData);
	}
	
	@Test
	public void completableFutureGetNullStudentDataTest() throws Exception
	{   
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_OK);
		map.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE, ApplicationConstants.RESPONSE_STATUS_CODE_NO_DATA);
		map.put(ApplicationConstants.RESPONSE_STATUS_DATA, new ArrayList<>());
		
		StudentResponse testStudentDataCheck = new StudentResponse();
		
		assertNotEquals(testStudentDataCheck, studentService.getStudentData("123123"));
	}
	
	@Test
	public void completableFutureAddSubjectDataTest() throws Exception
	{   
		SubjectDataDto subjects = new SubjectDataDto();
		SubjectData subjectData = new SubjectData();
		
		subjectData.setSubjectName("Maths");
		subjects.setSubjectData(subjectData);
		CompletableFuture<Map<String, Object>> studentData = studentService.addSubject(subjects);
		
		assertNotEquals(null, studentData);
	}

}