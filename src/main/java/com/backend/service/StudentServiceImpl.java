package com.backend.service;

import com.backend.model.Admin;
import com.backend.model.Book;
import com.backend.model.History;
import com.backend.model.Student;
import com.backend.repository.HistoryRepository;
import com.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HistoryRepository historyRepository;
    @Override
    public Student addStudent(Student student){
        return studentRepository.save(student);
    }
    @Override
    public Student getStudentById(long id){
        return studentRepository.findById(id).get();
    }

    @Override
    public List<Student> getStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);

    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public double calculateFine(Date currentDate, Date returnDate) {
        long diffInMillies = currentDate.getTime() - returnDate.getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        double finePerDay = 0.50;
        double fineAmount = finePerDay * diffInDays;
        System.out.println("diffInDays:" + diffInDays);
        return fineAmount;
    }

    @Override
    public boolean userExists(String name, String password) {
        Student student = studentRepository.findByNameAndPassword(name, password);
        return student != null;
    }

    @Override
    public Student getCurrentLoggedInStudent(String name){
        return studentRepository.findByName(name);
    }

    @Override
    public List<History> getHistory(Student student){
        return historyRepository.findByStudent(student);
    }
}