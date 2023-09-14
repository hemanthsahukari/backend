package com.backend.controller;

import com.backend.DTO.UserDetails;

import com.backend.model.History;
import com.backend.model.Momo;
import com.backend.model.Student;

import com.backend.service.MomoService;
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

    @Autowired
    private MomoService momoService;
    @GetMapping("/home")
    public String home(){
        return "Welcome to Library Management System";
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserDetails userDetails) {
        if (studentService.userExists(userDetails.getUsername(), userDetails.getPassword())) {
            Momo momo = new Momo(userDetails.getUsername());
            momoService.saveMomo(momo);
            return new ResponseEntity<>("User logged in successfully", new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid Credentials", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getCurrentMomo")
    public String getCurrentMomo() {
        return momoService.getCurrentMomo();

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

    @GetMapping("/history/{name}")
    public List<History> getHistory(@PathVariable("name") String name) {
        Student student = studentService.getCurrentLoggedInStudent(name);
         return studentService.getHistory(student);
    }

}
