package com.backend.repository;

import com.backend.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);

    List<Book> findByCopiesAvailable(long copies);

    List<Book> findByAuthor(String author);

    Book findByTitleAndAuthor(String Title, String Author);

    List<Book> findByBorrowByIsNotNull();


    @Query(value = "select * from book_table b where student_id = ?1",nativeQuery = true)
    List<Book> findByBorrowBy(long studentId);
}
