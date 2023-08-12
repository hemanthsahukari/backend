package com.backend.controller;

import com.backend.model.Book;

import com.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return "Book added successfully";
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
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

    @PutMapping("/borrow/{id}")
    public String borrowBook(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id);
        if (book != null && book.getAvailable()) {
            book.setAvailable(false);
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
            bookService.updateBook(book);
            return "Book returned successfully";
        } else if (book != null && book.getAvailable()) {
            return "Book is already available (not borrowed)";
        } else {
            return "Book not found";
        }
    }
}
