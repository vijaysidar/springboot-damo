package com.vijay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vijay.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}
