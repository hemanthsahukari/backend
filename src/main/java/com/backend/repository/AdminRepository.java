package com.backend.repository;

import com.backend.model.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUsernameAndPassword(String username, String password);
}