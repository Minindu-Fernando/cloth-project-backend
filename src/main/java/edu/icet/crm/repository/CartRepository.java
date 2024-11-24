package edu.icet.crm.repository;

import edu.icet.crm.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findByEmail(String email);
    void deleteById(Long id); // Corrected to use ID for deletion
    void deleteByEmail(String email);
}
