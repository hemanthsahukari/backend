package com.backend.service;

import com.backend.model.Book;
import com.backend.model.Student;
import com.backend.repository.BookRepository;
import com.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElse(null);
    }
    @Override
    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> getBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }


    @Override
    public void borrowBook(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null && book.getCopiesAvailable()>0) {
            bookRepository.save(book);
        }
    }

    @Override
    public void returnBook(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null && book.getCopiesAvailable() <= 0) {
            bookRepository.save(book);
        }
    }
    @Override
    public List<Book> getBorrowedBooks(){
        return bookRepository.findByBorrowByIsNotNull();

    }
    @Override
    public void addMultipleBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }


    @Override
    public void renewBook(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null && book.getCopiesAvailable() <= 0) {
            Date currentDate = new Date();
            if (currentDate.before(book.getReturnDate())) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.DAY_OF_MONTH, 14);
                book.setReturnDate(cal.getTime());
                bookRepository.save(book);
            }
        }
    }

    @Override
    public Book findByTitleAndAuthor(String title, String author){
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    @Override
    public List<Book> getStudentBorrowedBooks(String name){
        Student student = studentRepository.findByName(name);
        return bookRepository.findByBorrowBy(student.getId());
    }

}