package com.backend.repository;

import com.backend.model.Book;
import com.backend.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);

    List<Book> findByAvailableFalse();

    List<Book> findByAuthor(String author);

}
