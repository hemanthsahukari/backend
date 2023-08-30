package com.backend.controller;

import com.backend.model.Admin;
import com.backend.model.Book;
import com.backend.model.Student;
import com.backend.service.AdminService;
import com.backend.service.BookService;
import com.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private BookService bookService;

    @PostConstruct
    public void createDefaultAdmin() {
        if (adminService.getAllAdmins().isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("@dm!n");
            adminService.addAdmin(admin);
        }
    }

    @PostMapping("/login")
    public String adminLogin(@RequestBody Admin admin) {
        if (adminService.adminExists(admin.getUsername(), admin.getPassword())) {
            return "Admin logged in successfully";
        } else {
            return "Invalid credentials";
        }
    }
    //books..
    @PostMapping("/books/add")
    public String addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return "Book added successfully";
    }

    @PutMapping("/books/update")
    public String updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return "Book updated successfully";
    }

    @DeleteMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return "Book deleted successfully";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return "Student added successfully";
    }

    @PutMapping("/students/update")
    public String updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
        return "Student updated successfully";
    }

    @DeleteMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }

}