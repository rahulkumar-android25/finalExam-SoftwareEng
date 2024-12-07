package com.example.studentgpa.web;

import com.example.studentgpa.entity.Student;
import com.example.studentgpa.repositories.StudentRepository;
import org.hibernate.annotations.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class StudentControllerTest {

    Student student;
    public MockMvc mockMvc;

    @Mock
    StudentRepository studentRepository;

    @Mock
    View mockView;

    @InjectMocks
    StudentController studentController;


    @BeforeEach
    void setUp() throws ParseException {
        student = new Student();
        student.setId(1L);
        student.setStudentId(123);
        student.setStudentName("Rahul Kumar");
        student.setCourseName("SE");
        student.setGrade("A");
        student.setGPoints(4);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void getStudents() throws Exception {

        Student s1 = new Student(1L, 123, "Rahul Kumar", "SE", 4, "A", 4);


        List<Student> students = Arrays.asList(s1);


        when(studentRepository.findAll()).thenReturn(students);


        mockMvc.perform(get("/"))
                .andExpect(view().name("home-page"))
                .andExpect(model().attribute("students", students));


    }

    @Test
    void addStudent() {
    }

    @Test
    void deleteHotelById() {
    }

    @Test
    void showEditForm() {
    }

    @Test
    void updateRoom() {
    }

    @Test
    void calculateAndAddGPA() {
    }

    @Test
    void getGradeNumberByGrade() {
    }
}