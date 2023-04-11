package com.studentdata.studentdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentdata.studentdata.entity.Marks;

@Repository
@Transactional
public interface MarksRepository extends JpaRepository<Marks, Long>{

	@Query("SELECT s FROM Marks s WHERE s.studentId = :id")
	List<Marks> getByStudentId(Long id);

	@Query("SELECT distinct s FROM StudentEntity se inner join Marks m on se.id = m.studentId where se.id = :id")
	List<Marks> getAllDetails(Long id);
}
