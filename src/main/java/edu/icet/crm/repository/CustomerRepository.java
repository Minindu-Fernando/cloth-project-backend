package edu.icet.crm.repository;

import edu.icet.crm.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity,Integer> {

    Optional<CustomerEntity> findByEmail(String email);
}
