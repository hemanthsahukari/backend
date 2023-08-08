package com.backend.service;

import com.backend.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    public Student addStudent(Student student);
    public Student getStudentById(long id);
    public List<Student> getStudents();
    public void deleteStudent(long id);
    public Student updateStudent(Student student);

}
