package edu.icet.crm.repository;

import edu.icet.crm.entity.AdminEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<AdminEntity,Integer> {
    Optional<AdminEntity> findByEmail(String email);
}
