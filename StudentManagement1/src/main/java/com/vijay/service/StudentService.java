package com.vijay.service;

import java.util.List;

import com.vijay.model.Student;
import com.vijay.model.StudentFile;

public interface StudentService {

	 List<Student>  findAllStudent();

	Student saveStudent(Student student);

	Student updateStudent(Student student);

	Student getByStudentId(long sutdentId);

	List<StudentFile> getFilesByStudentId(long sutdentId);

	void deleteById(long studentId);

	void deleteFileByStudentId(long studentId);

}
