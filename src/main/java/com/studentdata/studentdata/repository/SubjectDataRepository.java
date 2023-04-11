package com.studentdata.studentdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentdata.studentdata.entity.SubjectData;

@Repository
public interface SubjectDataRepository extends JpaRepository<SubjectData, Long>{

	@Query("SELECT COUNT(*) FROM SubjectData s WHERE s.subjectName = :subjectName")
	Long findbyName(String subjectName);
	
	@Query("SELECT s FROM SubjectData s WHERE s.subjectName = :subjectName")
	SubjectData findbySubjectName(String subjectName);
}
