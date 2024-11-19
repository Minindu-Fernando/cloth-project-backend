package edu.icet.crm.repository;

import edu.icet.crm.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, String> {
    List<CartEntity> findByEmail(String email);
}
