package com.backend.controller;

import com.backend.DTO.BorrowedBooks;
import com.backend.model.Book;

import com.backend.model.Student;
import com.backend.service.BookService;
import com.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.*;

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

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title) {
        List<Book> books = new ArrayList<>();

        if (id != null) {
            Book book = bookService.getBookById(id);
            books.add(book);
        } else if (author != null) {
            books = bookService.getBookByAuthor(author);
        } else if (title != null) {
            books = bookService.getBookByTitle(title);
        }

        return ResponseEntity.ok(books);
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
    @ResponseBody
    public ResponseEntity<String> borrowBook(@PathVariable("id") long id, @PathVariable("name") String name) {
        Book book = bookService.getBookById(id);
        if (book != null && book.getAvailable()) {
            book.setAvailable(false);
            Student student= studentService.getCurrentLoggedInStudent(name);
            if(student.getFineAmount() > 0) {
                return new ResponseEntity<>("Pay the fine amount to borrow another book", new HttpHeaders(), HttpStatus.ACCEPTED);
            }
            book.setBorrowBy(student);
            book.setBorrowDate(new Date());
            Calendar cal = Calendar.getInstance();
            cal.setTime(book.getBorrowDate());
            cal.add(Calendar.DAY_OF_MONTH, 7);
            book.setReturnDate(cal.getTime());
            bookService.updateBook(book);
            return new ResponseEntity<>("Book borrowed successfully", new HttpHeaders(), HttpStatus.OK);
        } else if (book != null && !book.getAvailable()) {
            return new ResponseEntity<>("Book is not available (already borrowed)", new HttpHeaders(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Book not found", new HttpHeaders(), HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id);
        if (book != null && !book.getAvailable()) {
            book.setAvailable(true);
            Date currentDate = new Date();
            System.out.println("currentDate : " + currentDate);
            book.setSubmitDate(currentDate);
            if (currentDate.after(book.getReturnDate())) {
                 double fineAmount = studentService.calculateFine(currentDate, book.getReturnDate());
                System.out.println("fineAmount:" + fineAmount);
                Student student = book.getBorrowBy();
                student.setFineAmount(student.getFineAmount() + fineAmount);
                studentService.updateStudent(student);
            }
            bookService.updateBook(book);
            return new ResponseEntity<>("Book returned successfully",new HttpHeaders(),HttpStatus.OK) ;
        } else if (book != null && book.getAvailable()) {
            return new ResponseEntity<>("Book is already available (not borrowed)",new HttpHeaders(),HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Book not found",new HttpHeaders(),HttpStatus.ACCEPTED);
        }
    }
    @GetMapping("/borrowed")
    public  List<BorrowedBooks> getBorrowedBooks(){
        List<Book> bookList = bookService.getBorrowedBooks();
        List<BorrowedBooks> borrowedBooksList = new ArrayList<>();
        for(Book book : bookList) {
            BorrowedBooks borrowedBooks = new BorrowedBooks();
            borrowedBooks.setId(book.getId());
            borrowedBooks.setTitle(book.getTitle());
            borrowedBooks.setAuthor(book.getAuthor());
            borrowedBooks.setAvailable(book.getAvailable());
            //null pointer exception need to fix: when a user borrrow it should
            // define the name of user but showing undefined
            borrowedBooks.setBorrowBy(book.getBorrowBy().getName());
            borrowedBooks.setBorrowDate(book.getBorrowDate());
            borrowedBooks.setReturnDate(book.getReturnDate());
            double fineAmount = 0.0;
            if(book.getSubmitDate()!=null) {
                fineAmount = studentService.calculateFine(book.getSubmitDate(), book.getReturnDate());
            }
            System.out.println("fineAmount:" + fineAmount);
            borrowedBooks.setFineAmount(fineAmount);
            borrowedBooksList.add(borrowedBooks);
        }
        return borrowedBooksList;
    }
    @PostMapping("/addMultiple")
    public String addMultipleBooks(@RequestBody List<Book> books) {
        bookService.addMultipleBooks(books);
        return "books added successfully";
    }
    @PutMapping("/renew/{id}")
    public String renewBook(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id);
        if (book != null && !book.getAvailable()) {
            Date currentDate = new Date();
            if (currentDate.before(book.getReturnDate())) {
                // Calculates the  new return date
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.DAY_OF_MONTH, 14);
                book.setReturnDate(cal.getTime());
                bookService.updateBook(book);
                return "Book renewed successfully";
            } else {
                return "Book cannot be renewed as the return date has passed";
            }
        } else {
            return "Book not found or not borrowed";
        }
    }

}
