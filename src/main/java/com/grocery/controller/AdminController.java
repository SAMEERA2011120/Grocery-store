package com.grocery.controller;

import com.grocery.model.Admin;
import com.grocery.repository.AdminRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // ADMIN REGISTER
    @PostMapping("/register")
    public String registerAdmin(@RequestBody Admin admin) {

        Optional<Admin> existing =
                adminRepository.findByAdminId(admin.getAdminId());

        if (existing.isPresent()) {
            return "ADMIN_EXISTS";
        }

        adminRepository.save(admin);
        return "ADMIN_REGISTER_SUCCESS";
    }

    // ADMIN LOGIN
    @PostMapping("/login")
    public String loginAdmin(@RequestBody Admin admin) {

        Optional<Admin> found =
                adminRepository.findByAdminIdAndPassword(
                        admin.getAdminId(),
                        admin.getPassword()
                );

        return found.isPresent()
                ? "ADMIN_LOGIN_SUCCESS"
                : "ADMIN_LOGIN_FAILED";
    }
}
