package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.Student;
import com.example.demo.exception.ExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.StudentRepo;

@Service
public class StudentService {
	
	@Autowired StudentRepo repo;
	
	public Student create(Student s) {
		int id=s.getId();
		Optional<Student> stu=repo.findById(id);
		if(stu.isPresent())
			throw new ExistsException("Student already exists  with given id");
		return  repo.save(s);
	}
	
	public Student getbyid(int id) {
		Optional<Student> s=repo.findById(id);
		if(s.isEmpty())
			throw new NotFoundException("Student not found with the given id");
		return s.get();
	}
	
	public List<Student> getall(){
		return repo.findAll();
	}
	
	public String deletebyid(int id) {
		Optional<Student> s=repo.findById(id);
		if(s.isEmpty())
			throw new NotFoundException("Student not found with the given id");
		repo.deleteById(id);
		return "Deleted";
	}

}
