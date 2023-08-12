package com.backend.service;

import com.backend.model.Student;
import com.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;
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

}