package com.studentdata.studentdata.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentdata.studentdata.constant.ApplicationConstants;
import com.studentdata.studentdata.entity.StudentDto;
import com.studentdata.studentdata.entity.StudentResponse;
import com.studentdata.studentdata.entity.SubjectDataDto;
import com.studentdata.studentdata.service.StudentService;

@RestController
@RequestMapping("/StudentDetails")
public class StudentController {

	@Autowired
	StudentService studentService;

	/**
	 * This API will save student details and their marks according to their subjects.
	 * 
	 * @param studentDto
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@PostMapping("/saveStudentData")
	public ResponseEntity<Map<String, Object>> saveStudentData(@RequestBody StudentDto studentDto) throws InterruptedException, ExecutionException {
		CompletableFuture<Map<String, Object>> studentData = studentService.saveStudentData(studentDto);
		return new ResponseEntity<Map<String,Object>>(studentData.get(), HttpStatus.OK);
	}

	/**
	 * This API will get all Students and their subjects marks
	 * 
	 * @param studentName
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/getData")
	public ResponseEntity<Map<String, Object>> getStudentData(@RequestParam("studentName") String studentName) throws Exception {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		StudentResponse studentResponse =  studentService.getStudentData(studentName);
		
		if (studentResponse != null && studentResponse.getName() != null) {
			
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_OK);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE,
					ApplicationConstants.RESPONSE_STATUS_CODE_SUCCESS_MESSAGE);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_DATA, studentResponse);
			
		} else {
			
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_BAD);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE,
					ApplicationConstants.RESPONSE_STATUS_CODE_NO_DATA);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_DATA, new ArrayList<>());
		}
		
		return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
	}
	
	
	/**
	 *
	 * @param subjects
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@PostMapping("/addSubject")
	public ResponseEntity<Map<String, Object>> addSubject(@RequestBody SubjectDataDto subjects) throws InterruptedException, ExecutionException {
		CompletableFuture<Map<String, Object>> studentData = studentService.addSubject(subjects);
		return new ResponseEntity<Map<String,Object>>(studentData.get(), HttpStatus.OK);
	}

}
