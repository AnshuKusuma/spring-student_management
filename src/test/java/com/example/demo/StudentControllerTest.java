package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.Student;
import com.example.demo.exception.ExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @MockitoBean
    private StudentService service;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    static Optional<Student> opt1, opt2;

    @BeforeEach
    void beforeEach() {
        Student s = new Student(1, "aaa", "cse", "987867568", 85);
        opt1 = Optional.ofNullable(s);
        opt2 = Optional.empty();
    }

    @Test
    void testAddStudent() throws Exception {
        Student s = new Student(2, "bbb", "ece", "6765786876", 75);

        when(service.create(s)).thenReturn(s);

        mockMvc.perform(post("/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(s)))
                .andExpect(status().isCreated());
    }

    @Test
    void testExistingStudent() throws Exception {
        when(service.create(Mockito.any(Student.class)))
                .thenThrow(new ExistsException("Student already exists with given id"));

        mockMvc.perform(post("/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(opt1.get())))
                .andExpect(status().isAlreadyReported());
    }

    @Test
    void testGetId() throws Exception {
        when(service.getbyid(1)).thenReturn(opt1.get());

        mockMvc.perform(get("/getbyid/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(opt1.get())))
                .andExpect(status().isOk());
    }

    @Test
    void testGetIdNot() throws Exception {
        when(service.getbyid(2))
                .thenThrow(new NotFoundException("Student not found with given id"));

        mockMvc.perform(get("/getbyid/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAll() throws Exception {
        List<Student> lst = new ArrayList<>();
        lst.add(opt1.get());

        when(service.getall()).thenReturn(lst);

        mockMvc.perform(get("/getall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lst)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteId() throws Exception {
        when(service.deletebyid(1)).thenReturn("Deleted");

        mockMvc.perform(delete("/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(opt1.get())))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteIdNot() throws Exception {
        when(service.deletebyid(2))
                .thenThrow(new NotFoundException("Student not found with given id"));

        mockMvc.perform(delete("/delete/2"))
                .andExpect(status().isNotFound());
    }
}