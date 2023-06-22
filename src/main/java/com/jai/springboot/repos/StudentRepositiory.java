package com.jai.springboot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jai.springboot.models.Student;

@Repository
public interface StudentRepositiory extends JpaRepository<Student, Long> {
	

}
