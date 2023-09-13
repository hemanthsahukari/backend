package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    String message;

    public Date getDate() {
        return date;
    }

    public void setDate(Date borrowDate) {
        this.date = borrowDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    Student student;

    public History() {

    }
    public History(Date date, String message, Student student) {
        this.date = date;
        this.message = message;
        this.student = student;
    }
}
