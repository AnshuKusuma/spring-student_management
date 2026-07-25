package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService service;
	
	@PostMapping("/insert")
	public ResponseEntity create(@RequestBody Student s) {
		return new ResponseEntity(service.create(s),HttpStatus.CREATED);
	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity getbyid(@PathVariable int id) {
		return new ResponseEntity(service.getbyid(id),HttpStatus.OK);
	}
	
	@GetMapping("/getall")
	public ResponseEntity getall() {
		return new ResponseEntity(service.getall(),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity deletebyid(@PathVariable int id) {
		service.deletebyid(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
