package com.backend.controller;

import com.backend.model.Student;
import com.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://127.0.0.1:5174")
public class ClientController {
    @Autowired
    private StudentService studentService;
    @RequestMapping("/")
    public String hello_world(){
        return "hello";
    }
    //add student
    @PostMapping("/add")
    public String addStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return "student added successfully";
    }
    //getStudentById
    @RequestMapping("/students/{id}")
        public Student getStudentById(@PathVariable("id") long id){
        return studentService.getStudentById(id);
    }
    //getStudents
    @RequestMapping("/students")
    public List<Student> getStudents(){
        return studentService.getStudents();

    }
    @PutMapping("/students")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
    //deleteStudentById
    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable("id") long id){
        studentService.deleteStudent(id);
        return "Student Deleted";
    }
}
