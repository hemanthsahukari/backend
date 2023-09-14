package com.backend.model;

import javax.persistence.*;
@Entity
@Table(name = "book_student_map")
public class BookStudentMap {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        private long bookId;

        private long studentId;

    public BookStudentMap() {

    }
    public BookStudentMap(long bookId, long studentId) {
        this.bookId = bookId;
        this.studentId = studentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

}
