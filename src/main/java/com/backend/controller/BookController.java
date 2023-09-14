package com.backend.controller;

import com.backend.DTO.BorrowedBooks;
import com.backend.model.Book;

import com.backend.model.BookStudentMap;
import com.backend.model.History;
import com.backend.model.Student;
import com.backend.service.BookService;
import com.backend.service.BookStudentMapService;
import com.backend.service.HistoryService;
import com.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HistoryService historyService;


    @Autowired
    private BookStudentMapService bookStudentMapService;

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
    public String borrowBook(@PathVariable("id") long id, @PathVariable("name") String name) {
        Book book = bookService.getBookById(id);
        Student student= studentService.getCurrentLoggedInStudent(name);
        if(student.getBorrowCount()<=2) {
            if (book != null && book.getCopiesAvailable() > 0) {

                if (student.getFineAmount() > 0) {
                    return "Pay the fine amount to borrow another book";
                }
//                book.setBorrowBy(student);//optional

                BookStudentMap bookStudentMap = new BookStudentMap(book.getId(), student.getId());
                bookStudentMapService.saveBookStudentMap(bookStudentMap);



                book.setBorrowDate(new Date());
                Calendar cal = Calendar.getInstance();
                cal.setTime(book.getBorrowDate());
                cal.add(Calendar.DAY_OF_MONTH, 7);
                book.setReturnDate(cal.getTime());
                book.setCopiesAvailable(book.getCopiesAvailable() - 1);

                bookService.updateBook(book);
                student.setBorrowCount(student.getBorrowCount() + 1);

                History history = new History(new Date(),book.getTitle() + " Book borrowed successfully", student);
                historyService.saveHistory(history);

                return "Book borrowed successfully";
            } else if (book != null && book.getCopiesAvailable() == 0) {
                return "no copies of book is available (already borrowed)";
            } else {

                return "Book not found";
            }
        }
        else {
            return "Borrow Count Exceeded";
        }
    }


    @PutMapping("/return/{id}/{name}")
    public String returnBook(@PathVariable("id") long id, @PathVariable("name")String name) {
        Book book = bookService.getBookById(id);

        Student currentLoggedInStudent = studentService.getCurrentLoggedInStudent(name);

        BookStudentMap bookStudentMap = bookStudentMapService.getBookStudentMapByBookAndStudent(id,currentLoggedInStudent.getId());
        if (book != null) {
            if (bookStudentMap != null) {
                //condition for firstCopy
                book.setCopiesAvailable(book.getCopiesAvailable() + 1);
                Date currentDate = new Date();
                System.out.println("currentDate : " + currentDate);
                book.setSubmitDate(currentDate);
                if (currentDate.after(book.getReturnDate())) {
                    double fineAmount = studentService.calculateFine(currentDate, book.getReturnDate());
                    System.out.println("fineAmount:" + fineAmount);
                   //Student student = book.getBorrowBy();
                    Student student = studentService.getStudentById(bookStudentMap.getStudentId());
                    student.setFineAmount(student.getFineAmount() + fineAmount);
                    studentService.updateStudent(student);
                }
                bookStudentMapService.deleteBookStudentMap(bookStudentMap);
                bookService.updateBook(book);
                return "Book returned successfully";
            }
            else {
                return "you are not the borrower of the book";
            }
        }
         else {
            return "Book not found";
        }
    }
    @GetMapping("/borrowed")
    public  List<BorrowedBooks> getBorrowedBooks(){
        List<BookStudentMap> bookStudentMapList = bookStudentMapService.getBookStudentMap();
        List<BorrowedBooks> borrowedBooksList = new ArrayList<>();
        for(BookStudentMap bookStudentMap : bookStudentMapList) {
            Student student = studentService.getStudentById(bookStudentMap.getStudentId());
            Book book = bookService.getBookById(bookStudentMap.getBookId());

            BorrowedBooks borrowedBooks = new BorrowedBooks();

            borrowedBooks.setId(book.getId());
            borrowedBooks.setTitle(book.getTitle());
            borrowedBooks.setAuthor(book.getAuthor());
            borrowedBooks.setBorrowBy(student.getName());
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


    @GetMapping("/borrowed/{name}")
    public  List<BorrowedBooks> getBorrowedBooks(@PathVariable("name") String name){
        Student student = studentService.getStudentByName(name);
        List<BookStudentMap> bookStudentMapList = bookStudentMapService.getBookStudentMapByName(student.getId());
        List<BorrowedBooks> borrowedBooksList = new ArrayList<>();
        for(BookStudentMap bookStudentMap : bookStudentMapList) {
            Book book = bookService.getBookById(bookStudentMap.getBookId());

            BorrowedBooks borrowedBooks = new BorrowedBooks();

            borrowedBooks.setId(book.getId());
            borrowedBooks.setTitle(book.getTitle());
            borrowedBooks.setAuthor(book.getAuthor());
            borrowedBooks.setBorrowBy(student.getName());
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
    @GetMapping("/availability/{id}")
    public ResponseEntity<String> getBookAvailabilityStatus(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            if (book.getCopiesAvailable()>0) {
                return new ResponseEntity<>("Book is available", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Book is not available  or reserved", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/renew/{id}")
    public String renewBook(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id);
        if (book != null && book.getCopiesAvailable() <= 0) {
            Date currentDate = new Date();
            if (currentDate.before(book.getReturnDate())) {
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
