package com.vijay.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vijay.model.StudentFile;

@Repository
public interface StudentFileRepository extends JpaRepository<StudentFile, Long> {
	@Transactional
	@Modifying
	@Query("delete  from StudentFile  where student_id=?1 or modifiedFileName in(?2)")
	void deleteByStudentIdAndImageName(long id, List<String> removeImages);
	
	@Query("select f from StudentFile as f where f.student.id=?1")
	List<StudentFile> getFilesByStudentId(long sutdentId);
	
	@Transactional
	@Modifying
	@Query("delete  from StudentFile as f where f.student.id=?1")
	void deleteByStudentId(long studentId);

}
