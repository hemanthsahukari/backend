package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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


    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student borrowBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submitDate;

    @Column(name="copies_available")
    private long copiesAvailable;

    @Column(name="firstCopy")
    public long getFirstCopy() {
        return firstCopy;
    }

    public void setFirstCopy(long firstCopy) {
        this.firstCopy = firstCopy;
    }

    private long firstCopy;
    public Book(){

    }
    public Book(long copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public long getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(long copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

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


    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
}
