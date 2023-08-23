package com.backend.controller;

import com.backend.DTO.UserDetails;
import com.backend.model.Admin;
import com.backend.model.Book;
import com.backend.model.Student;
import com.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/home")
    public String home(){
        return "Welcome to Library Management System";
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserDetails userDetails) {
        if (studentService.userExists(userDetails.getUsername(), userDetails.getPassword())) {
            return new ResponseEntity<>("User logged in successfully", new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid Credentials", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
    }
    //add student
    @PostMapping("/add")
    public String addStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return "student added successfully";
    }
    //getStudentById
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") long id){
        return studentService.getStudentById(id);
    }

    //getStudents

    @GetMapping("/")
    public List<Student> getStudents(){
        return studentService.getStudents();

    }
    @PutMapping("/")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
    //deleteStudentById
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable("id") long id){
        studentService.deleteStudent(id);
        return "Student Deleted";
    }

}
