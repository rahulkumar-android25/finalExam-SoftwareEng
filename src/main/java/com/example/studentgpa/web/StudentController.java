package com.example.studentgpa.web;


import com.example.studentgpa.entity.Student;
import com.example.studentgpa.entity.StudentGpa;
import com.example.studentgpa.repositories.StudentGpaRepository;
import com.example.studentgpa.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class StudentController {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentGpaRepository studentGpaRepository;

    @GetMapping("/index")
    public String getStudents(Model model) {
        List<Student> studentList = studentRepository.findAll();
        List<StudentGpa> studentGpaList = studentGpaRepository.findAll();
        model.addAttribute("students", studentList);
        model.addAttribute("student", new Student());
        model.addAttribute("studentGpaList", studentGpaList);
        return "home-page";
    }


    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("student") Student student, Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "home-page";
        }
        student.setGPoints(student.getCourseUnits() * getGradeNumberByGrade(student.getGrade()));
        studentRepository.save(student);

        redirectAttributes.addFlashAttribute("message", "Student added successfully");
        calculateAndAddGPA(student);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteHotelById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Record deleted successfully");
        return "redirect:/index";
    }


    @GetMapping("edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        model.addAttribute("student", student);
        return "edit-student";
    }


    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable long id, Student student, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "home-page";
        }
        student.setId(id);
        studentRepository.save(student);
        redirectAttributes.addFlashAttribute("message", "Student updated successfully");
        return "redirect:/index";
    }


    public void calculateAndAddGPA(Student student) {
        List<Student> allByStudentCourses = studentRepository.findAllByStudentId(student.getId());
        System.out.println("allByStudentCourses"+allByStudentCourses.size());
        double gpa = 0;
        double totalCreditHours = 0;
        double totalGPoints = 0;
        for (Student stud : allByStudentCourses) {
            System.out.println("totalGPoints: " + stud.getGPoints()+" "+stud.getCourseUnits());

            totalGPoints = totalGPoints + stud.getGPoints();
            totalCreditHours = totalCreditHours + stud.getCourseUnits();
        }

        gpa = totalGPoints / totalCreditHours;
        System.out.println("Total gpa: " + gpa);
        StudentGpa studentGpa = new StudentGpa();
//        studentGpa.setStudent(student);
        studentGpa.setGpa(gpa);
        studentGpaRepository.save(studentGpa);

    }

    public double getGradeNumberByGrade(String grade) {
        return switch (grade) {
            case "A" -> 4;
            case "B" -> 3;
            case "C" -> 2;
            case "D" -> 1;
            default -> 0;
        };
    }


}
