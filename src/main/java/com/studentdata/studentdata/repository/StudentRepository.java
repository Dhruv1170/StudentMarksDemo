package com.studentdata.studentdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentdata.studentdata.entity.StudentEntity;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<StudentEntity, Long>{

	@Query("SELECT s FROM StudentEntity s WHERE s.name = :name")
	List<StudentEntity> findByName(String name);

}
