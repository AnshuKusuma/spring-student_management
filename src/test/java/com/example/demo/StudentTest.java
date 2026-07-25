package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockitoSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Student;
import com.example.demo.exception.ExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.StudentRepo;
import com.example.demo.service.StudentService;

@ExtendWith(MockitoExtension.class)
public class StudentTest {
	
	@Mock
	private StudentRepo repo;
	
	@InjectMocks
	private StudentService service;
	
	static Map<Integer,Student> map=new HashMap<>();
	
	@BeforeAll
	public static void beforeall() {
	    map.put(1, new Student(1,"aaa","cse","987867568",85));
	    map.put(2, new Student(2,"bbb","ece","6765786876",75));
	}
	
	@Test
	public void createnew() {
		Student s=new Student(3,"ccc","eee","889667564",70);
		Mockito.when(repo.findById(3)).thenReturn(Optional.empty());
		Mockito.when(repo.save(s)).thenReturn(s);
		assertEquals(s, service.create(s));
		Mockito.verify(repo).findById(3);
		Mockito.verify(repo).save(s);
	}
	
	@Test
	public void createexist() {
		Student s=map.get(1);
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(s));
		assertThrows(ExistsException.class,()->service.create(s));
		Mockito.verify(repo).findById(1);
		Mockito.verify(repo,Mockito.never()).save(s);
	}
	
	
	@Test
	public void testgetbyidexist() {
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(map.get(1)));
		assertSame(service.getbyid(1),map.get(1));
		Mockito.verify(repo).findById(1);
	}
	
	@Test
	public void testgetbyidnot() {
		Mockito.when(repo.findById(3)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, ()->service.getbyid(3));
		Mockito.verify(repo).findById(3);
	}
	
	@Test 
	public void getall() {
		List<Student> lst=new ArrayList<>(map.values());
		Mockito.when(repo.findAll()).thenReturn(lst);
		assertEquals(lst, service.getall());
		Mockito.verify(repo).findAll();
	}
	
	@Test
	public void deleteexist() {
		Student s=map.get(1);
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(s));
		assertEquals("Deleted",service.deletebyid(1));
		Mockito.verify(repo).findById(1);
		Mockito.verify(repo).deleteById(1);
	}
	
	@Test
	public void deletenot() {
		Mockito.when(repo.findById(3)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, ()->service.deletebyid(3));
		Mockito.verify(repo).findById(3);
		Mockito.verify(repo,Mockito.never()).deleteById(3);
	}
	

}
