package com.backend.DTO;

import com.backend.model.Student;

import java.util.Date;

public class BorrowedBooks {
    private long id;
    private String title;
    private String author;
    private String borrowBy;
    private Date borrowDate;
    private Date returnDate;

    private double fineAmount;

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
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


    public String getBorrowBy() {
        return borrowBy;
    }

    public void setBorrowBy(String borrowBy) {
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

}
