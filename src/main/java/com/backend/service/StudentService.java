package com.backend.service;

import com.backend.model.Book;
import com.backend.model.History;
import com.backend.model.Student;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface StudentService {
    public Student addStudent(Student student);
    public Student getStudentById(long id);
    public List<Student> getStudents();
    public void deleteStudent(long id);
    public Student updateStudent(Student student);
    public double calculateFine(Date borrowDate, Date returnDate);

    public boolean userExists(String username, String password);
    public Student getCurrentLoggedInStudent(String name);

    List<History> getHistory(Student student);

    Student getStudentByName(String name);

//    Student getStudentByName(Student name);
}