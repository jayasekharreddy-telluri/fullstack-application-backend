package com.jai.springboot.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jai.springboot.exceptions.ResourceNotFoundException;
import com.jai.springboot.models.Student;
import com.jai.springboot.repos.StudentRepositiory;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class StudentController {
	
	@Autowired
	private StudentRepositiory studentRepositiory;
	
	private static final Logger logger=LoggerFactory.getLogger(StudentController.class);
	
	
	//get all students
	
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		return studentRepositiory.findAll();
	}
	
	//create a student
	
	@PostMapping("/students")
	public Student createStudent(@RequestBody Student student) {
		logger.info("student saved to database :"+ student);
		return studentRepositiory.save(student);
	}
	
	//get student by id

	@GetMapping("/students/{id}")
	public ResponseEntity<Student>  getStudentById(@PathVariable Long id) {
		Student student = studentRepositiory.findById(id).
				orElseThrow(()->new ResourceNotFoundException("Student not found with following: "+id));
		
		logger.info("student details of id "+id+" is :"+student);
		return ResponseEntity.ok(student);
	}
	
	//update student details
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Student>  updateStudentById(@PathVariable Long id,@RequestBody Student updateStudent) {
		
		Student student = studentRepositiory.findById(id).
				orElseThrow(()->new ResourceNotFoundException("Student not found with following: "+id));
		student.setFirstName(updateStudent.getFirstName());
		student.setLastName(updateStudent.getLastName());
		student.setEmail(updateStudent.getEmail());
		
		logger.info("updated student info is "+student);
		return ResponseEntity.ok(studentRepositiory.save(student));
	}
	
	//delete student by id
	

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Map<String, Boolean>>  deleteStudentById(@PathVariable Long id) {
		
		Student student = studentRepositiory.findById(id).
				orElseThrow(()->new ResourceNotFoundException("Student not found with following: "+id));
		studentRepositiory.delete(student);
		Map<String,Boolean> response=new  HashMap<>();
		response.put("deleted the student", Boolean.TRUE);
		
		logger.info("student deleted of id :"+id);
		return ResponseEntity.ok(response);
	}
	
	
	

}
