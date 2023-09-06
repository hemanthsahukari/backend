package com.backend.service;

import com.backend.model.Book;
import java.util.List;

public interface BookService {
    Book addBook(Book book);
    Book getBookById(long id);
    List<Book> getBooks();
    void deleteBook(long id);
    Book updateBook(Book book);
    void borrowBook(long id);
    void returnBook(long id);

    List<Book> getBookByTitle(String title);

    List<Book> getBookByAuthor(String author);
    List<Book> getBorrowedBooks();

    void addMultipleBooks(List<Book> books);

    void renewBook(long id);

//    void reserveBook(long id);
}