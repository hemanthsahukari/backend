package com.backend.service;

import com.backend.model.Admin;

import java.util.List;

public interface AdminService {
    void addAdmin(Admin admin);
    List<Admin> getAllAdmins();
    boolean adminExists(String username, String password);
}