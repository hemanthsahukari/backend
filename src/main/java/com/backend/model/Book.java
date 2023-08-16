package com.backend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book_table")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String author;
    private Boolean available;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student borrowBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    public Student getBorrowBy() {
        return borrowBy;
    }

    public void setBorrowBy(Student borrowBy) {
        this.borrowBy = borrowBy;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    //until here...


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}