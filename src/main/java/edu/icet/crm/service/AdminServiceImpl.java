package edu.icet.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.entity.AdminEntity;
import edu.icet.crm.model.Admin;
import edu.icet.crm.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final ObjectMapper mapper;

    @Override
    public Admin persist(Admin admin) {
        Optional<AdminEntity> existingAdmin = adminRepository.findByEmail(admin.getEmail());
        if (existingAdmin.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        AdminEntity savedEntity = adminRepository.save(mapper.convertValue(admin, AdminEntity.class));
        return mapper.convertValue(savedEntity, Admin.class);
    }

    @Override
    public Admin login(String email, String password) {
        Optional<AdminEntity> adminEntity = adminRepository.findByEmail(email);
        if (adminEntity.isPresent() && adminEntity.get().getPassword().equals(password)) {
            return mapper.convertValue(adminEntity.get(), Admin.class);
        }
        return null;
    }
}
