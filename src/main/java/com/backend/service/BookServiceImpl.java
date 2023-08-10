package com.backend.service;

import com.backend.model.Book;
import com.backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElse(null);
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
        if (book != null && book.getAvailable()) {
            book.setAvailable(false);
            bookRepository.save(book);
        }
    }

    @Override
    public void returnBook(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null && !book.getAvailable()) {
            book.setAvailable(true);
            bookRepository.save(book);
        }
    }
}