package com.backend.repository;

import com.backend.model.Admin;
import com.backend.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findByNameAndPassword(String name, String password);

    Student findByName(String name);
}
