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
    //books.
    @PostMapping("/books/add")
    public String addBook(@RequestBody Book book) {
        System.out.println("copies : " + book.getCopiesAvailable());
        book.setFirstCopy(book.getCopiesAvailable());
        System.out.println("fist copy : " + book.getFirstCopy());
        Book existingBook = bookService.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if(existingBook != null) {
            System.out.println("inside existing book");
            existingBook.setCopiesAvailable(book.getCopiesAvailable() + existingBook.getCopiesAvailable());
            existingBook.setFirstCopy(book.getFirstCopy() + existingBook.getFirstCopy());
            bookService.updateBook(existingBook);
        }
        else {
            bookService.addBook(book);
        }
        return "Book added successfully";
    }

    @PutMapping("/books/update")
    public String updateBook(@RequestBody Book book) {
        book.setFirstCopy(book.getCopiesAvailable());
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
        Student student1 = studentService.getStudentByName(student.getName());
        if(student1 == null) {
            studentService.addStudent(student);
            return "Student added successfully";
        }
        return "Student with Same name already exist";
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