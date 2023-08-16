package com.backend.controller;

import com.backend.model.Book;

import com.backend.model.Student;
import com.backend.service.BookService;
import com.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return "Book added successfully";
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        return bookService.getBookById(id);
    }
    @GetMapping("/search/{title}")
    public List<Book> getBookByTitle(@PathVariable("title") String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/all")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return "Book Deleted";
    }

    @PutMapping("/borrow/{id}/{name}")
    public String borrowBook(@PathVariable("id") long id,@PathVariable("name") String name) {
        Book book = bookService.getBookById(id);
        if (book != null && book.getAvailable()) {
            book.setAvailable(false);
            Student student= studentService.getCurrentLoggedInStudent(name);
            book.setBorrowBy(student);
            book.setBorrowDate(new Date());
            Calendar cal = Calendar.getInstance();
            cal.setTime(book.getBorrowDate());
            cal.add(Calendar.DAY_OF_MONTH, 14);
            book.setReturnDate(cal.getTime());
            bookService.updateBook(book);
            return "Book borrowed successfully";
        } else if (book != null && !book.getAvailable()) {
            return "Book is not available (already borrowed)";
        } else {
            return "Book not found";
        }
    }

    @PutMapping("/return/{id}")
    public String returnBook(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id);
        if (book != null && !book.getAvailable()) {
            book.setAvailable(true);
            Date currentDate = new Date();
            if (currentDate.after(book.getReturnDate())) {
                 double fineAmount = studentService.calculateFine(book.getBorrowDate(), book.getReturnDate());
                Student student = book.getBorrowBy();
                student.setFineAmount(student.getFineAmount() + fineAmount);
                studentService.updateStudent(student);
            }
            bookService.updateBook(book);
            return "Book returned successfully";
        } else if (book != null && book.getAvailable()) {
            return "Book is already available (not borrowed)";
        } else {
            return "Book not found";
        }
    }

}
