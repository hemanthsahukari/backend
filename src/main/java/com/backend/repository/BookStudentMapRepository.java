package com.backend.repository;

import com.backend.model.BookStudentMap;
import com.backend.model.Momo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookStudentMapRepository extends CrudRepository<BookStudentMap, Long> {

    @Query(value = "select * from book_student_map where student_id = ?1",nativeQuery = true)
    List<BookStudentMap> findByStudentId(long studentId);

    @Query(value = "select * from book_student_map where book_id = ?1 and student_id = ?2 ORDER BY id DESC LIMIT 1",nativeQuery = true)
    BookStudentMap findByBookIdAndStudentId(long bookId, long studentId);
}
