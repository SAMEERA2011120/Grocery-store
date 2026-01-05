package com.grocery.repository;

import com.grocery.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByAdminIdAndPassword(String adminId, String password);

    Optional<Admin> findByAdminId(String adminId);
}
