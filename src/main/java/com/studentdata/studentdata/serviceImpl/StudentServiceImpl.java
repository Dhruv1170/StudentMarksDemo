package com.studentdata.studentdata.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.studentdata.studentdata.constant.ApplicationConstants;
import com.studentdata.studentdata.entity.Marks;
import com.studentdata.studentdata.entity.StudentDto;
import com.studentdata.studentdata.entity.StudentEntity;
import com.studentdata.studentdata.entity.StudentResponse;
import com.studentdata.studentdata.entity.SubjectData;
import com.studentdata.studentdata.entity.SubjectDataDto;
import com.studentdata.studentdata.repository.MarksRepository;
import com.studentdata.studentdata.repository.StudentRepository;
import com.studentdata.studentdata.repository.SubjectDataRepository;
import com.studentdata.studentdata.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	MarksRepository marksRepository;
	
	@Autowired
	SubjectDataRepository subjectdataRepository;

	/**
	 * This method is used to save student and his/her subject details
	 * 
	 * @param studentDto
	 * 
	 * @return status with Success/Failure
	 */
	
	@Override
	@Async
	public CompletableFuture<Map<String, Object>> saveStudentData(StudentDto studentDto) {
		Map<String, Object> responseMap = new HashMap<>();
		StudentEntity studentEntity = new StudentEntity();

		CompletableFuture<Marks> saveSubjectData = new CompletableFuture<>();

		try {
			studentEntity.setAddress(studentDto.getStudentEntity().getAddress());
			studentEntity.setAge(studentDto.getStudentEntity().getAge());
			studentEntity.setName(studentDto.getStudentEntity().getName());

			// Save Student's data into the table
			CompletableFuture<StudentEntity> saveStudentData = CompletableFuture.supplyAsync(() -> {
				return studentRepository.save(studentEntity);
			});

			StudentEntity student = saveStudentData.get();

			if (!studentDto.getStudentEntity().getSubjects().isEmpty()) {
				for (Map<String, Double> subject : studentDto.getStudentEntity().getSubjects()) {

					Marks marks = new Marks();
					Set<String> a = subject.keySet();
					for (String s : a) {
						
						//Check if subject present in table
						SubjectData subjectdata =  subjectdataRepository.findbySubjectName(s);
						
						if(subjectdata == null) {
							SubjectData sub = new SubjectData();
							sub.setSubjectName(s);
							
							subjectdata = subjectdataRepository.save(sub);
						}
						
						//subjects.setSubjectName(s);
						marks.setMarks(subject.get(s));
						marks.setSubjectId(subjectdata.getId());
						marks.setStudentId(student.getId());
					}
					
					//subjects.setStudentId(student.getId());
					saveSubjectData = CompletableFuture.supplyAsync(() -> marksRepository.save(marks));
				}
			}

			// Save Subject's data into the table
			if (saveSubjectData.get() != null) {
				responseMap.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_OK);
				responseMap.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE,
						ApplicationConstants.RESPONSE_STATUS_CODE_SUCCESS_MESSAGE);
				responseMap.put(ApplicationConstants.RESPONSE_STATUS_DATA, new ArrayList<>());

			} else {
				responseMap.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_BAD);
				responseMap.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE,
						ApplicationConstants.RESPONSE_STATUS_CODE_FAILUR_MESSAGE);
				responseMap.put(ApplicationConstants.RESPONSE_STATUS_DATA, new ArrayList<>());
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_BAD);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE,
					ApplicationConstants.RESPONSE_STATUS_CODE_FAILUR_MESSAGE);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_DATA, new ArrayList<>());
		}

		return CompletableFuture.completedFuture(responseMap);
	}

	/**
	 * This method is used to get student and subject list by Student Name
	 * 
	 * @param name
	 * 
	 *             return list of student and subject data
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Override
	public StudentResponse getStudentData(String name) throws Exception {
		
		List<Map<String, Double>> subjectMap = new ArrayList<>();
		
		List<Marks> marksList = new ArrayList<>();
		SubjectData subjectList = null;
		
		StudentResponse studentResponseDto = new StudentResponse();

		try {
			List<StudentEntity> studentEntityList = studentRepository.findByName(name);

			if (!CollectionUtils.isEmpty(studentEntityList)) {
				for (StudentEntity studentEntity : studentEntityList) {
			
					studentResponseDto.setName(studentEntity.getName());
					studentResponseDto.setAge(studentEntity.getAge());
					studentResponseDto.setAddress(studentEntity.getAddress());

					//Get all marks of student
					marksList = marksRepository.getByStudentId(studentEntity.getId());
				}
				
				//Get subject name with marks
				for (Marks marks : marksList) {
					Map<String, Double> subjectMapInner = new HashMap<String, Double>();
					if (marks != null) {
						subjectList = subjectdataRepository.getById(marks.getSubjectId());
						
						subjectMapInner.put(subjectList.getSubjectName(), marks.getMarks());
						subjectMap.add(subjectMapInner);
						
						studentResponseDto.setSubjects(subjectMap);
					}
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CompletableFuture.completedFuture(studentResponseDto).get();

	}
	
	
	/**
	 * This method is used to save subject data
	 * 
	 * @para subjects
	 * 
	 * return response
	 */
	@Override
	@Async
	public CompletableFuture<Map<String, Object>> addSubject(SubjectDataDto subjects) {
		//CompletableFuture<Subjects> savedSubjectData = new CompletableFuture<>();
		Map<String, Object> responseMap = new HashMap<>();
		Boolean isSubjectPresent = Boolean.FALSE;
		
		try {
				
			for (String subs : subjects.getSubjectData().getSubjects()) {
				Long subjectdata =  subjectdataRepository.findbyName(subs);
				
				if(subjectdata != null && subjectdata > 0) {
					
					responseMap.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_OK);
					responseMap.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE,
							ApplicationConstants.RESPONSE_STATUS_SUBJECT_ALREADY_PRESENT);
					responseMap.put(ApplicationConstants.RESPONSE_STATUS_DATA, new ArrayList<>());
					isSubjectPresent = Boolean.TRUE;
					
				} else {
					SubjectData sub = new SubjectData();
					sub.setSubjectName(subs);
					
					subjectdataRepository.save(sub);
				}
			}
			
			if(isSubjectPresent) {
				return CompletableFuture.completedFuture(responseMap);
			}
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_OK);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE,
					ApplicationConstants.RESPONSE_STATUS_CODE_SUCCESS_MESSAGE);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_DATA, new ArrayList<>());
			
		} catch (Exception e) {
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_STATUS, ApplicationConstants.RESPONSE_STATUS_CODE_BAD);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_MESSAGE,
					ApplicationConstants.RESPONSE_STATUS_CODE_FAILUR_MESSAGE);
			responseMap.put(ApplicationConstants.RESPONSE_STATUS_DATA, new ArrayList<>());
			e.printStackTrace();
		}
		
		return CompletableFuture.completedFuture(responseMap);
	}

}
