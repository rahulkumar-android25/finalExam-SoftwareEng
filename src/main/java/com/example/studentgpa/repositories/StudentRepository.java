package com.example.studentgpa.repositories;

import com.example.studentgpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByStudentId(Long id);

}
